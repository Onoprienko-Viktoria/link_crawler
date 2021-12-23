package com.onoprienko.domenwebservice.service;

import com.onoprienko.domenwebservice.dao.DomainDao;
import com.onoprienko.domenwebservice.entity.Domain;
import com.onoprienko.domenwebservice.entity.Link;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DomainService {
    private DomainDao domainDao;
    private LinkService linkService;
    private static Logger log = Logger.getLogger(DomainService.class.getName());

    public DomainService(DomainDao domainDao, LinkService linkService) {
        this.domainDao = domainDao;
        this.linkService = linkService;
    }

    public List<Domain> findAll() {
        List<Domain> domainList = domainDao.findAll();
        log.log(Level.INFO, "Get list of domains");
        return domainList;
    }

    public Domain addDomain(String link, List<Link> links) {
        Domain domain = Domain.builder()
                .name(link)
                .allLinks(linkService.getSumOfAllLinks(links))
                .externalLinks(linkService.getSumOfExternalLinks(links))
                .internalLinks(links.size())
                .build();
        domainDao.addDomain(domain);
        log.log(Level.INFO, "Add new domain " + domain.getName() + " to data base");
        return domain;
    }

    public Domain getDomain(String domainName, List<Link> links) {
        Domain domain = domainDao.getDomain(domainName);
        if (domain == null) {
            domain = addDomain(domainName, links);
        }
        log.log(Level.INFO, "Get domain " + domain.getName());
        return domain;
    }

}
