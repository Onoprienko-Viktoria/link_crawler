package com.onoprienko.domenwebservice.dao.jdbc.mapper;

import com.onoprienko.domenwebservice.entity.Link;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LinkRowMapper {
    public static Link mapRow(ResultSet resultSet) throws SQLException {
        String url = resultSet.getString("url");
        int nestingLevel = resultSet.getInt("nesting_level");
        int externalLinks = resultSet.getInt("external_links");
        return Link.builder()
                .url(url)
                .nestingLevel(nestingLevel)
                .externalLinks(externalLinks)
                .build();
    }
}
