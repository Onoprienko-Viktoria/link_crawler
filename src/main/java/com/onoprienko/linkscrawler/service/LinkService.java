package com.onoprienko.linkscrawler.service;

import com.onoprienko.linkscrawler.dao.LinkDao;
import com.onoprienko.linkscrawler.entity.Link;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
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
            crawler(domainName);
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


    private void crawler(String domainName) {
        Queue<Link> remainingLinks = new LinkedList<>();
        List<String> visitedLinks = new LinkedList<>();
        List<Link> links = new LinkedList<>();
        remainingLinks.add(Link.builder()
                .domainName(domainName)
                .url(domainName)
                .nestingLevel(0)
                .build());


        while (!remainingLinks.isEmpty()) {
            Link currentLink = remainingLinks.poll();
            int externalLinks = 0;
            int level = currentLink.getNestingLevel();
            
            Document document = getPage(currentLink.getUrl());
            Elements linksOnPage = document.select("a[href]");
            for (Element element : linksOnPage) {
                String href = element.absUrl("href");
                if (!href.contains(domainName)) {
                    externalLinks++;
                } else if (!visitedLinks.contains(href)) {
                    visitedLinks.add(href);
                    remainingLinks.offer(Link.builder()
                            .domainName(domainName)
                            .url(href)
                            .nestingLevel(level + 1)
                            .build());
                }
            }
            currentLink.setExternalLinks(externalLinks);
            links.add(currentLink);
        }
        addLink(links);
    }

    private void addLink(List<Link> links) {
        for (Link link : links) {
            linkDao.addLink(link);
            log.log(Level.INFO, "Add link " + link.getUrl() + " to data base");
        }

    }

    private Document getPage(String url) {
        try {
            Connection con = Jsoup.connect(url);
            Document doc = con.get();
            if (con.response().statusCode() == 200) {
                log.log(Level.INFO, "Get page: " + url);
                return doc;
            }
        } catch (IOException e) {
            throw new RuntimeException("Incorrect or broken url");
        }
        return null;
    }
}