package me.imstring.discordshopper.repositories;

import java.sql.SQLException;
import java.util.Iterator;

public interface Repository<T> {
    void save(T entity) throws SQLException;

    void update(T entity) throws SQLException;

    void delete(T entity) throws SQLException;

    void registerTable() throws SQLException;

    T findById(String id) throws SQLException;

    Iterator<T> findAll() throws SQLException;
}
