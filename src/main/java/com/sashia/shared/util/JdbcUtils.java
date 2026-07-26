package com.sashia.shared.util;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.List;
import java.util.Objects;

public final class JdbcUtils {

    private JdbcUtils() {
        throw new UnsupportedOperationException("JdbcUtils is a utility class and cannot be instantiated");
    }

    public static Long insert(JdbcClient.StatementSpec sqlStatement) {
        Objects.requireNonNull(sqlStatement, "sqlStatement must not be null");

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rows = sqlStatement.update(keyHolder, "id");

        if (rows == 0) {
            throw new IllegalStateException("Insert failed: no rows affected");
        }

        Number key = keyHolder.getKey();
        if (key == null) {
            throw new IllegalStateException("Insert succeeded but no generated ID returned");
        }

        return key.longValue();
    }

    public static int batchUpdate(JdbcTemplate jdbcTemplate, String sql, List<Object[]> batchArgs) {
        Objects.requireNonNull(jdbcTemplate);
        Objects.requireNonNull(sql);
        Objects.requireNonNull(batchArgs);

        if (batchArgs.isEmpty()) {
            return 0;
        }

        return jdbcTemplate.batchUpdate(sql, batchArgs).length;
    }

    public static void update(JdbcClient.StatementSpec sqlStatement) {
        Objects.requireNonNull(sqlStatement, "sqlStatement must not be null");
        sqlStatement.update();
    }

    public static boolean delete(JdbcClient.StatementSpec sqlStatement) {
        update(sqlStatement);
        return true;
    }

}