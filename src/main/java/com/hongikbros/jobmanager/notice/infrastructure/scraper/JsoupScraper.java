package com.hongikbros.jobmanager.notice.infrastructure.scraper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.hongikbros.jobmanager.notice.domain.notice.Notice;
import com.hongikbros.jobmanager.notice.domain.scraper.Scraper;
import com.hongikbros.jobmanager.notice.infrastructure.exception.NotScrapingUrlException;

public class JsoupScraper implements Scraper {

    private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.0.3 Safari/605.1.15";
    private static final int HOLDING_TIME = 7000;
    private static final int NOT_FOUND = 404;
    private static final int TOO_MANY_REQUEST = 429;

    public Notice createNotice(String noticeUrl) {
        final Document document = getDocument(noticeUrl);
        final Elements logo = document.select("img[class=logo],img[id=logo]").eq(0);
        parseCompanyLogoSvg(logo);
        return null;
    }

    private void parseCompanyLogoSvg(Elements logo) {
        String logoUrl = logo.attr("abs:src");
        final String strImageName = logoUrl.substring(logoUrl.lastIndexOf("/") + 1);
        System.out.println("Saving: " + strImageName + ", from: " + logoUrl);

        try (InputStream in = new URL(logoUrl).openStream()) {

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] byteSize = new byte[4096];

            int length = -1;
            while ((length = in.read(byteSize)) != -1) {
                byteArrayOutputStream.write(byteSize, 0, length);
            }

            System.out.println(byteArrayOutputStream.toString());
            System.out.println("Image saved");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Document getDocument(String noticeUrl) {
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
