package de.pandooor.model;


import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMapper<T extends AbstractEntity> implements Mapper<T> {

    protected final String TABLE;

    protected AbstractMapper(String table) {
        TABLE = table;
    }

    @Override
    public List<T> findAll(int start, int num) throws SQLException {

        List<T> data = new ArrayList<>();

        String sql = "SELECT * FROM " + TABLE + " COMPANY LIMIT ? OFFSET ?";

        try(Connection dbh = DBConnectionFactory.get();
            PreparedStatement stmt = dbh.prepareStatement(sql)) {

            stmt.setInt(1, num);
            stmt.setInt(2, start);

            ResultSet results = stmt.executeQuery();

            while(results.next()) {
                data.add(convert(results));
            }
        }

        return data;
    }


    @Override
    public List<T> findAll() throws SQLException {

        List<T> data = new ArrayList<>();

        try(Connection dbh = DBConnectionFactory.get();
            Statement stmt = dbh.createStatement()) {

            ResultSet results = stmt.executeQuery("SELECT * FROM " + TABLE);

            while(results.next()) {
                data.add(convert(results));
            }
        }

        return data;
    }

    @Override
    public T findOneById(int id) throws SQLException {

        String sql = "SELECT * FROM " + TABLE + " WHERE id = " + id;

        try(Connection dbh = DBConnectionFactory.get();
            Statement stmt = dbh.createStatement()) {

            ResultSet results = stmt.executeQuery(sql);

            if(results.next()) {
                return convert(results);
            }
        }
        return null;
    }

    @Override
    public boolean save(T t) throws SQLException {

        if(t.getId() > 0) {
            return update(t);
        }
        else {
            return insert(t);
        }
    }

    private boolean insert(T t) throws SQLException {

        // INSERT INTO users (firsttname, lastname) VALUES('Peter', 'Parker')

        StringBuilder fields = new StringBuilder();
        StringBuilder values = new StringBuilder();

        // Eigenschaften der Elternklasse abfragen
//		Field[] superFields = t.getClass().getSuperclass().getDeclaredFields();

        // Eigenschaften der Klasse abfragen
        for(Field f : t.getClass().getDeclaredFields()) {

            if(f.getName().equals("serialVersionUID"))
                continue;

            if(fields.length() > 0) {
                fields.append(",");
                values.append(",");
            }
            fields.append(f.getName()); // Name der Eigenschaft
            values.append("?");
        }

        String sql = "INSERT INTO " + TABLE + " (" + fields.toString() +") VALUES(" + values.toString() +")";

        return executePrepared(sql, t);
    }

    private boolean update(T t) throws SQLException {

        StringBuilder fields = new StringBuilder();

        for(Field f : t.getClass().getDeclaredFields()) {

            if(f.getName().equals("serialVersionUID"))
                continue;

            if(fields.length() > 0) {
                fields.append(",");
            }
            fields.append(f.getName()).append(" = ?");
        }


        String sql = "UPDATE " + TABLE + " SET " + fields.toString() + " WHERE id = " + t.getId();
        return executePrepared(sql, t);

    }


    private boolean executePrepared(String sql, T t) throws SQLException {

        try(Connection dbh = DBConnectionFactory.get();
            PreparedStatement stmt = dbh.prepareStatement(sql)) {


            int i = 0;
            for(Field f : t.getClass().getDeclaredFields()) {

                if(f.getName().equals("serialVersionUID"))
                    continue;

                f.setAccessible(true); // Macht private Eigenschaften lesbar
                try {
                    stmt.setObject(++i, f.get(t)); // Frage Feld f aus dem Objekt t ab
                }
                catch (IllegalAccessException e) {
                    //TODO: Richtige Meldung bringen
                }
            }

            stmt.execute();
            // TODO: ID abfragen und in das Objekt schreiben

            return stmt.getUpdateCount() > 0;
        }
    }



    @Override
    public boolean delete(T t) throws SQLException {
        return delete(t.getId());
    }

    @Override
    public boolean delete(int id) throws SQLException {

        String sql = "DELETE FROM " + TABLE + " WHERE id = " + id;

        try(Connection dbh = DBConnectionFactory.get();
            Statement stmt = dbh.createStatement()) {

            return stmt.executeUpdate(sql) > 0;

        }
    }
}

