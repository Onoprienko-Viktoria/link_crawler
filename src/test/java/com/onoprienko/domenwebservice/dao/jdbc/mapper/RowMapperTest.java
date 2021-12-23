package com.onoprienko.domenwebservice.dao.jdbc.mapper;

import com.onoprienko.domenwebservice.entity.Domain;
import com.onoprienko.domenwebservice.entity.Link;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RowMapperTest {
    @Test
    public void testDomainRowMap() throws SQLException {
        DomainRowMapper domainRowMapper = new DomainRowMapper();
        ResultSet resultSetMock = mock(ResultSet.class);
        when(resultSetMock.getString("name")).thenReturn("google.com");
        when(resultSetMock.getDouble("all_links")).thenReturn(11.0);
        when(resultSetMock.getDouble("internal_links")).thenReturn(9.0);
        when(resultSetMock.getDouble("external_links")).thenReturn(2.0);

        Domain domain = domainRowMapper.mapRow(resultSetMock);
        assertEquals("google.com", domain.getName());
        assertEquals(11, domain.getAllLinks());
        assertEquals(9, domain.getInternalLinks());
        assertEquals(2, domain.getExternalLinks());
    }

    @Test
    public void testLinkRowMap() throws SQLException {
        LinkRowMapper linkRowMapper = new LinkRowMapper();
        ResultSet resultSetMock = mock(ResultSet.class);
        when(resultSetMock.getString("url")).thenReturn("google.com");
        when(resultSetMock.getInt("nesting_level")).thenReturn(1);
        when(resultSetMock.getInt("external_links")).thenReturn(2);

        Link link = linkRowMapper.mapRow(resultSetMock);
        assertEquals("google.com", link.getUrl());
        assertEquals(1, link.getNestingLevel());
        assertEquals(2, link.getExternalLinks());
    }
}