package me.imstring.discordshopper.repositories;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Repository<T> {
    void save(T entity) throws SQLException;

    void update(T entity) throws SQLException;

    void delete(T entity) throws SQLException;

    Optional<T> findById(String id) throws SQLException;

    List<T> findAll() throws SQLException;

    void registerTable() throws SQLException;
}
