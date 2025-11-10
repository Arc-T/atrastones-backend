package com.atrastones.shop.utils;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Utility class for common JDBC operations using Spring's {@link JdbcClient}.
 * <p>
 * This class centralizes boilerplate for inserts, updates, and deletes, including:
 * <ul>
 *     <li>Handling generated keys for insert operations.</li>
 *     <li>Validating affected rows and throwing exceptions on failures.</li>
 * </ul>
 * <p>
 * All methods throw unchecked exceptions, allowing them to propagate to global
 * exception handlers (e.g., {@code @RestControllerAdvice}) for logging and API error responses.
 */
public final class JdbcUtils {

    // Prevent instantiation
    private JdbcUtils() {
        throw new UnsupportedOperationException("JdbcUtils is a utility class and cannot be instantiated");
    }

    /**
     * Executes a single INSERT statement and returns the generated primary key.
     *
     * @param sqlStatement the {@link JdbcClient.StatementSpec} with SQL and bound parameters
     * @return the generated key as a {@link Long}
     * @throws NullPointerException     if {@code sqlStatement} is null
     * @throws IllegalStateException    if the insert affects zero rows or no key is returned
     */
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

    /**
     * Executes multiple INSERT statements in a batch and returns their generated IDs.
     *
     * @param sqlStatements a list of {@link JdbcClient.StatementSpec}, one per insert
     * @return an unmodifiable list of generated IDs, in the same order as {@code sqlStatements}
     * @throws NullPointerException     if {@code sqlStatements} is null
     * @throws IllegalStateException    if any insert affects zero rows or returns no key
     */
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

    /**
     * Executes an UPDATE statement and validates that at least one row was affected.
     *
     * @param sqlStatement    the {@link JdbcClient.StatementSpec} with SQL and bound parameters
     * @param notFoundMessage the exception message if no rows are updated
     * @throws NullPointerException      if {@code sqlStatement} or {@code notFoundMessage} is null
     * @throws EntityNotFoundException   if the update affects zero rows
     */
    public static void update(JdbcClient.StatementSpec sqlStatement, String notFoundMessage) {
        Objects.requireNonNull(sqlStatement, "sqlStatement must not be null");
        Objects.requireNonNull(notFoundMessage, "notFoundMessage must not be null");

        int rows = sqlStatement.update();
        if (rows == 0) {
            throw new EntityNotFoundException(notFoundMessage);
        }
    }

    /**
     * Executes a DELETE statement and validates that at least one row was affected.
     *
     * @param sqlStatement    the {@link JdbcClient.StatementSpec} with SQL and bound parameters
     * @param notFoundMessage the exception message if no rows are deleted
     * @return {@code true} if at least one row was deleted
     * @throws NullPointerException      if {@code sqlStatement} or {@code notFoundMessage} is null
     * @throws EntityNotFoundException   if no rows are deleted
     */
    public static boolean delete(JdbcClient.StatementSpec sqlStatement, String notFoundMessage) {
        update(sqlStatement, notFoundMessage);
        return true;
    }
}