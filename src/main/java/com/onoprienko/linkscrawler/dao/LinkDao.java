package com.onoprienko.linkscrawler.dao;

import com.onoprienko.linkscrawler.entity.Link;

import java.util.List;

public interface LinkDao {
    List<Link> findAllByDomain(String domain);

    void addLink(Link link);

    boolean isLinkWithDomainAlreadyExist(String domainName);

}
