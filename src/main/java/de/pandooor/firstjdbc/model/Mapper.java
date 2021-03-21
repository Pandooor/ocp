package de.pandooor.firstjdbc.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface Mapper<T extends AbstractEntity> {

    // CRUD

    T findOneById(int id) throws SQLException;

    List<T> findAll() throws SQLException;

    List<T> findAll(int start, int num) throws SQLException;

//	boolean insert(T t) throws SQLException;
//
//	boolean update(T t) throws SQLException;

    boolean save(T t) throws SQLException;

    boolean delete(T t) throws SQLException;

    boolean delete(int id) throws SQLException;

    T convert(ResultSet results) throws SQLException;

    boolean createTable() throws SQLException;
}