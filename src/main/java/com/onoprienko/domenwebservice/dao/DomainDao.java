package com.onoprienko.domenwebservice.dao;

import com.onoprienko.domenwebservice.entity.Domain;

import java.util.List;

public interface DomainDao {
    List<Domain> findAll();

    void addDomain(Domain domain);

    Domain getDomain(String domainName);
}
