package de.pandooor.model;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBConnectionFactory {

    private DBConnectionFactory() {
    }

    public static Connection get() throws SQLException {
        return get(Type.SQLITE);
    }

    private static Connection getSQLite() throws SQLException {
        String url = "jdbc:sqlite:mydata.db";
        return DriverManager.getConnection(url);
    }

    private static Connection getMySql() throws SQLException {

        // Default-Port für Win: 3306
        String url = "jdbc:mysql://localhost:8889/user_db?serverTimezone=UCT";
        return DriverManager.getConnection(url, "root", "root");
    }

    public static Connection get(Type type) throws SQLException {

        switch(type) {

            case SQLITE:
                return getSQLite();

            case MYSQL:
                return getMySql();

            case ORACLE:
            case H2:
            default:
                throw new SQLException("Database-type not supported.");
        }
    }

    public static enum Type {

        MYSQL, SQLITE, ORACLE, H2;

    }
}

