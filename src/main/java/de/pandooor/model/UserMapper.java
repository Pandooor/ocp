package de.pandooor.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserMapper extends AbstractMapper<User> {

    public UserMapper() throws SQLException {
        super("users");
        createTable();
    }

    /**
     * Wandelt relationale Daten in Objekte um
     * @param results relationale Daten
     * @return Daten-Objekt
     */
    @Override
    public User convert(ResultSet results) throws SQLException {
        User user = new User();
        user.setId(results.getInt("id"));
        user.setFirstname(results.getString("firstname"));
        user.setLastname(results.getString("lastname"));
        return user;
    }

    @Override
    public boolean createTable() throws SQLException {

        String sql = "CREATE TABLE IF NOT EXISTS users ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "firstname CHAR(50) NOT NULL,"
                + "lastname CHAR(50) NOT NULL)";

        try(Connection dbh = DBConnectionFactory.get();
            Statement stmt = dbh.createStatement()) {

            return stmt.executeUpdate(sql) > 0;
        }
    }
}
