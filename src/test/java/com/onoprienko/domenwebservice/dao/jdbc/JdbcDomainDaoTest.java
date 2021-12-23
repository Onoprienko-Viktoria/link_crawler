package com.onoprienko.domenwebservice.dao.jdbc;

import com.onoprienko.domenwebservice.entity.Domain;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JdbcDomainDaoTest {
    @Test
    public void testFindAllReturnCorrectData(){
        JdbcDomainDao jdbcDomainDao = new JdbcDomainDao();
        List<Domain> domains = jdbcDomainDao.findAll();
        assertFalse(domains.isEmpty());

        for (Domain domain : domains) {
            assertNotNull(domain.getName());
            assertNotNull(domain.getAllLinks());
            assertNotNull(domain.getExternalLinks());
            assertNotNull(domain.getInternalLinks());
        }
    }
}