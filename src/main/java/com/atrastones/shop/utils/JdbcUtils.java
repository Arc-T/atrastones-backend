package com.atrastones.shop.utils;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.Objects;

/**
 * Utility class for common JDBC operations with Spring's {@link JdbcClient}.
 * <p>
 * This class centralizes boilerplate for inserts and updates, including:
 * <ul>
 *     <li>Handling generated keys for insert operations.</li>
 *     <li>Validating affected rows and throwing exceptions on failures.</li>
 * </ul>
 * <p>
 * All methods throw unchecked exceptions, allowing them to propagate to global
 * exception handlers (e.g., {@code @RestControllerAdvice}) for logging and API error responses.
 */
public final class JdbcUtils {

    /**
     * Private constructor to prevent instantiation of utility class.
     */
    private JdbcUtils() {
    }

    /**
     * Executes an INSERT statement and returns the generated primary key.
     *
     * @param sqlStatement the {@link JdbcClient.StatementSpec} with SQL and bound parameters.
     * @return the generated key as a {@link Long}.
     * @throws IllegalStateException if the insert affects zero rows or no key is returned.
     * @implNote This method uses {@link KeyHolder} to retrieve auto-generated keys.
     */
    public static Long insert(JdbcClient.StatementSpec sqlStatement) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        // Execute the insert and capture the number of affected rows
        int rows = sqlStatement.update(keyHolder);

        if (rows == 0)
            throw new IllegalStateException("Insert failed: no rows affected");

        // Extract the generated key
        Number key = keyHolder.getKey();

        if (Objects.isNull(key))
            throw new IllegalStateException("Insert succeeded but no ID returned");

        return key.longValue();
    }

    /**
     * Executes an UPDATE statement and validates that at least one row was affected.
     *
     * @param sqlStatement    the {@link JdbcClient.StatementSpec} with SQL and bound parameters
     * @param notFoundMessage the exception message if no rows are updated
     * @throws EntityNotFoundException if the update affects zero rows
     * @implNote Useful for enforcing "must exist" semantics when updating entities.
     */
    public static void update(JdbcClient.StatementSpec sqlStatement, String notFoundMessage) {

        int rows = sqlStatement.update();

        if (rows == 0) {
            throw new EntityNotFoundException(notFoundMessage);
        }
    }

    /**
     * Executes a DELETE statement and returns whether any rows were affected.
     *
     * @param sqlStatement the {@link JdbcClient.StatementSpec} with SQL and bound parameters
     * @return {@code true} if at least one row was deleted, {@code false} otherwise
     * @implNote This method does not throw an exception on zero affected rows,
     * giving the caller the option to handle "not found" cases differently.
     */
    public static boolean delete(JdbcClient.StatementSpec sqlStatement, String notFoundMessage) {
        int rows = sqlStatement.update();
        if (rows == 0)
            throw new EntityNotFoundException(notFoundMessage);
        return rows > 0;
    }

}
