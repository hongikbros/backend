package com.hongikbros.jobmanager.notice.infrastructure.scraper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import com.hongikbros.jobmanager.notice.command.domain.notice.ApplyUrl;
import com.hongikbros.jobmanager.notice.command.domain.notice.Company;
import com.hongikbros.jobmanager.notice.command.domain.notice.Duration;
import com.hongikbros.jobmanager.notice.command.domain.notice.Notice;
import com.hongikbros.jobmanager.notice.command.domain.scraper.Scraper;
import com.hongikbros.jobmanager.notice.command.domain.skill.Skill;
import com.hongikbros.jobmanager.notice.infrastructure.exception.NotParseException;
import com.hongikbros.jobmanager.notice.infrastructure.exception.NotScrapingException;
import com.hongikbros.jobmanager.notice.infrastructure.exception.ParseExceptionCode;
import com.hongikbros.jobmanager.notice.infrastructure.exception.ScrapingExceptionCode;

@Component
public class JsoupScraper implements Scraper {

    private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.0.3 Safari/605.1.15";
    private static final int HOLDING_TIME = 7000;
    private static final int NOT_FOUND = 404;
    private static final int TOO_MANY_REQUEST = 429;

    public Notice createNotice(Long memberId, String noticeUrl, List<String> skillTags,
            Duration duration) {
        final Document document = scrapDocument(noticeUrl);
        final List<Skill> skills = skillTags.stream()
                .map(Skill::from)
                .collect(Collectors.toList());
        final Company company = parseCompany(document);
        final String title = parseTitle(document);
        final ApplyUrl applyUrl = ApplyUrl.from(noticeUrl);

        return Notice.of(memberId, title, company, skills, duration, applyUrl);
    }

    private String parseTitle(Document document) {
        try {
            return document.select("title").first().text();
        } catch (NullPointerException e) {
            throw new NotParseException(ParseExceptionCode.NOT_FOUND_MATCH_REGEX);
        }
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
            throw new NotParseException(ParseExceptionCode.NOT_PARSE_COMPANY_LOGO);
        }
    }

    private Document scrapDocument(String noticeUrl) {
        try {
            return Jsoup.connect(noticeUrl)
                    .userAgent(USER_AGENT)
                    .timeout(HOLDING_TIME)
                    .get();
        } catch (HttpStatusException e) {
            if (e.getStatusCode() == NOT_FOUND) {
                throw new NotScrapingException(ScrapingExceptionCode.NOT_FOUND_URL);
            } else if (e.getStatusCode() == TOO_MANY_REQUEST) {
                throw new NotScrapingException(ScrapingExceptionCode.TOO_MANY_REQUEST);
            }
            throw new NotScrapingException(ScrapingExceptionCode.URL_NOT_CONNECT);
        } catch (IOException e) {
            throw new NotScrapingException(ScrapingExceptionCode.URL_NOT_CONNECT);
        }
    }
}
