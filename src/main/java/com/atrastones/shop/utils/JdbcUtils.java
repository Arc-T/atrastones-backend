package com.atrastones.shop.utils;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class JdbcUtils {

    private JdbcUtils() {
        throw new UnsupportedOperationException("JdbcUtils is a utility class and cannot be instantiated");
    }

    public static Long insert(JdbcClient.StatementSpec sqlStatement) {
        Objects.requireNonNull(sqlStatement, "sqlStatement must not be null");

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rows = sqlStatement.update(keyHolder);

        if (rows == 0) {
            throw new IllegalStateException("Insert failed: no rows affected");
        }

        Number key = keyHolder.getKey();
        if (key == null) {
            throw new IllegalStateException("Insert succeeded but no generated ID returned");
        }

        return key.longValue();
    }

    public static List<Long> insertBatch(List<JdbcClient.StatementSpec> sqlStatements) {
        Objects.requireNonNull(sqlStatements, "sqlStatements must not be null");

        if (sqlStatements.isEmpty()) {
            return Collections.emptyList();
        }

        List<Long> generatedIds = new ArrayList<>(sqlStatements.size());

        for (JdbcClient.StatementSpec stmt : sqlStatements) {
            Long id = insert(stmt); // reuse single insert method
            generatedIds.add(id);
        }

        return Collections.unmodifiableList(generatedIds);
    }

    public static void update(JdbcClient.StatementSpec sqlStatement, String notFoundMessage) {
        Objects.requireNonNull(sqlStatement, "sqlStatement must not be null");
        Objects.requireNonNull(notFoundMessage, "notFoundMessage must not be null");

        int rows = sqlStatement.update();
        if (rows == 0) {
            throw new EntityNotFoundException(notFoundMessage);
        }
    }

    public static boolean delete(JdbcClient.StatementSpec sqlStatement, String notFoundMessage) {
        update(sqlStatement, notFoundMessage);
        return true;
    }

}