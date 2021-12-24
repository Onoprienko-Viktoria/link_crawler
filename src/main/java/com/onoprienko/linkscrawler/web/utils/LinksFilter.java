package com.onoprienko.linkscrawler.web.utils;

import java.net.URI;
import java.net.URISyntaxException;

public class LinksFilter {
    public static String getDomainFromLink(String link) {
        try {
            URI uri = new URI(link);
            String domain = uri.getHost();
            String checkedDomain =  domain.startsWith("www.") ? domain.substring(4) : domain;
            return ("https://" + checkedDomain);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
