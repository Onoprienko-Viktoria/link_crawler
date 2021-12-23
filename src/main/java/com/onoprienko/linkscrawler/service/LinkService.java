package com.onoprienko.linkscrawler.service;

import com.onoprienko.linkscrawler.dao.LinkDao;
import com.onoprienko.linkscrawler.entity.Link;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class LinkService {
    private LinkDao linkDao;
    private static Logger log = Logger.getLogger(LinkService.class.getName());

    public LinkService(LinkDao linkDao) {
        this.linkDao = linkDao;
    }

    public List<Link> findAllByDomain(String domainName) {
        if (!linkDao.isLinkWithDomainAlreadyExist(domainName)) {
            List<String> visitedLinks = new ArrayList<>();
            crawler(domainName, domainName, visitedLinks, 1);
        }
        List<Link> links = linkDao.findAllByDomain(domainName);
        log.log(Level.INFO, "Get list of Links for domain: " + domainName);
        return links;
    }


    public double getSumOfAllLinks(List<Link> links) {
        double sum = 0;
        for (Link link : links) {
            sum += link.getExternalLinks();
            sum++;
        }
        return sum;
    }

    public double getSumOfExternalLinks(List<Link> links) {
        double sum = 0;
        for (Link link : links) {
            sum += link.getExternalLinks();
        }
        return sum;
    }

    private void crawler(String domainName, String url, List<String> visitedLinks, int level) {
        int externalLinks = 0;
        Document document = getHtml(url, visitedLinks);
        if (document != null) {
            for (Element link : document.select("a[href]")) {
                String next_link = link.absUrl("href");
                if (!next_link.contains(domainName)) {
                    externalLinks++;
                } else if (!visitedLinks.contains(next_link)) {
                    crawler(domainName, next_link, visitedLinks, level++);
                }
            }
        }
        System.out.println(url);
        Link link = Link.builder()
                .domainName(domainName)
                .url(url)
                .nestingLevel(level)
                .externalLinks(externalLinks)
                .build();
        addLink(link);
    }

    private static Document getHtml(String url, List<String> visitedLinks) {
        try {
            org.jsoup.Connection con = Jsoup.connect(url);
            Document doc = con.get();
            if (con.response().statusCode() == 200) {
                log.log(Level.INFO, "Connect page: " + url);
                visitedLinks.add(url);
                return doc;
            }
        } catch (IOException e) {
            throw new RuntimeException("Incorrect or broken url");
        }
        return null;
    }

    private void addLink(Link link) {
        linkDao.addLink(link);
        log.log(Level.INFO, "Add link " + link.getUrl() + " to data base");
    }

}
