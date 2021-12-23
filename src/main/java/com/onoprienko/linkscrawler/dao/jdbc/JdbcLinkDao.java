package com.onoprienko.linkscrawler.dao.jdbc;

import com.onoprienko.linkscrawler.dao.LinkDao;
import com.onoprienko.linkscrawler.dao.jdbc.mapper.LinkRowMapper;
import com.onoprienko.linkscrawler.entity.Link;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcLinkDao implements LinkDao {
    private static final String FIND_ALL_SQL = "SELECT domain, url, nesting_level, external_links FROM links WHERE domain=?";
    private static final String ADD_LINK_SQL = """
            INSERT INTO links (domain, url, nesting_level, external_links)
            VALUES(?, ?, ?, ?)
            """;

    @Override
    public List<Link> findAllByDomain(String domain) {
        List<Link> links = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            preparedStatement.setString(1, domain);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    links.add(LinkRowMapper.mapRow((resultSet)));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can not find links");
        }
        return links;
    }

    @Override
    public void addLink(Link link) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_LINK_SQL)) {
            preparedStatement.setString(1, link.getDomainName());
            preparedStatement.setString(2, link.getUrl());
            preparedStatement.setInt(3, link.getNestingLevel());
            preparedStatement.setInt(4, link.getExternalLinks());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Can not add link");
        }
    }

    @Override
    public boolean isLinkWithDomainAlreadyExist(String domainName) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            preparedStatement.setString(1, domainName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can not check links");
        }
        return false;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5433/dws", "user", "pass");
    }
}
