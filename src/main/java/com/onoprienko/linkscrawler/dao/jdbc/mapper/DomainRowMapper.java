package com.onoprienko.linkscrawler.dao.jdbc.mapper;

import com.onoprienko.linkscrawler.entity.Domain;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DomainRowMapper {
    public static Domain mapRow(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("name");
        double allLinks = resultSet.getDouble("all_links");
        double internalLinks = resultSet.getDouble("internal_links");
        double externalLinks = resultSet.getDouble("external_links");
        return Domain.builder()
                .name(name)
                .allLinks(allLinks)
                .internalLinks(internalLinks)
                .externalLinks(externalLinks)
                .build();
    }
}
