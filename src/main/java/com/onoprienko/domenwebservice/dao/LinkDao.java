package com.onoprienko.domenwebservice.dao;

import com.onoprienko.domenwebservice.entity.Link;

import java.util.List;

public interface LinkDao {
    List<Link> findAllByDomain(String domain);

    void addLink(Link link);

    boolean isLinkWithDomainAlreadyExist(String domainName);

}
