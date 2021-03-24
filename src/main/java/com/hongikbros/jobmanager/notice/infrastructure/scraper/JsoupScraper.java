package com.hongikbros.jobmanager.notice.infrastructure.scraper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.hongikbros.jobmanager.notice.domain.company.Company;
import com.hongikbros.jobmanager.notice.domain.notice.ApplyUrl;
import com.hongikbros.jobmanager.notice.domain.notice.Duration;
import com.hongikbros.jobmanager.notice.domain.notice.Notice;
import com.hongikbros.jobmanager.notice.domain.scraper.Scraper;
import com.hongikbros.jobmanager.notice.infrastructure.exception.NotParseCompany;
import com.hongikbros.jobmanager.notice.infrastructure.exception.NotScrapingUrlException;

public class JsoupScraper implements Scraper {

    private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.0.3 Safari/605.1.15";
    private static final int HOLDING_TIME = 7000;
    private static final int NOT_FOUND = 404;
    private static final int TOO_MANY_REQUEST = 429;

    public Notice createNotice(String noticeUrl, Duration duration) {
        return parseNotice(noticeUrl, duration);
    }

    private Notice parseNotice(String noticeUrl, Duration duration) {
        final Document document = scrapDocument(noticeUrl);
        final Company company = parseCompany(document);
        final String title = document.title();
        final ApplyUrl applyUrl = ApplyUrl.from(noticeUrl);

        return Notice.of(company, title, duration, applyUrl);
    }

    private Company parseCompany(Document document) {
        final Element logo = document.select("img[class=logo],img[id=logo]").first();
        final String logoUrl = logo.attr("abs:src");

        try (InputStream in = new URL(logoUrl).openStream()) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] byteSize = new byte[4096];

            int length;
            while ((length = in.read(byteSize)) != -1) {
                byteArrayOutputStream.write(byteSize, 0, length);
            }
            return Company.from(byteArrayOutputStream.toString());
        } catch (IOException e) {
            throw new NotParseCompany("Company를 parse하는 도중 문제가 발생하였습니다." + e.getMessage());
        }
    }

    public Document scrapDocument(String noticeUrl) {
        try {
            return Jsoup.connect(noticeUrl)
                    .userAgent(USER_AGENT)
                    .timeout(HOLDING_TIME)
                    .get();
        } catch (IOException e) {
            throw new NotScrapingUrlException("noticeUrl 을 스크랩하지 못하였습니다.: " + e.getMessage());
        }
    }
}
