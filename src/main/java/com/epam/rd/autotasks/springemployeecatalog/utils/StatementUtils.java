package com.epam.rd.autotasks.springemployeecatalog.utils;

import org.springframework.jdbc.core.PreparedStatementCreator;

import java.sql.ResultSet;

public final class StatementUtils {

    private StatementUtils() {
    }

    public static PreparedStatementCreator getPreparedStatementCreator(String query, Long id) {
        String finalQuery = String.format(query, id);
        return connection -> connection.prepareStatement(finalQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
    }

    public static PreparedStatementCreator getPreparedStatementCreator(String query) {
        return connection -> connection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
    }
}