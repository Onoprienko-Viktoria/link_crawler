package com.onoprienko.linkscrawler.dao.jdbc;

import com.onoprienko.linkscrawler.dao.DomainDao;
import com.onoprienko.linkscrawler.dao.jdbc.mapper.DomainRowMapper;
import com.onoprienko.linkscrawler.entity.Domain;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcDomainDao implements DomainDao {
    private static final String FIND_ALL_SQL = "SELECT name, all_links, internal_links, external_links FROM domains";
    private static final String FIND_DOMAIN_BY_NAME_SQL = "SELECT name, all_links, internal_links, external_links FROM domains WHERE name=?";
    private static final String ADD_DOMAIN_SQL = """
            INSERT INTO domains (name, all_links, external_links, internal_links)
            VALUES(?, ?, ?, ?)
            """;


    @Override
    public List<Domain> findAll() {
        List<Domain> domains = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                domains.add(DomainRowMapper.mapRow(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Can not find domains");
        }
        return domains;
    }

    @Override
    public void addDomain(Domain domain) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_DOMAIN_SQL)) {
            preparedStatement.setString(1, domain.getName());
            preparedStatement.setDouble(2, domain.getAllLinks());
            preparedStatement.setDouble(3, domain.getExternalLinks());
            preparedStatement.setDouble(4, domain.getInternalLinks());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Can not add domain");
        }
    }

    @Override
    public Domain getDomain(String domainName) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_DOMAIN_BY_NAME_SQL)) {
            preparedStatement.setString(1, domainName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return DomainRowMapper.mapRow(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can not get domain");
        }
        return null;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5433/dws", "user", "pass");
    }
}
