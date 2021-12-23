package com.onoprienko.linkscrawler.dao;

import com.onoprienko.linkscrawler.entity.Domain;

import java.util.List;

public interface DomainDao {
    List<Domain> findAll();

    void addDomain(Domain domain);

    Domain getDomain(String domainName);
}
