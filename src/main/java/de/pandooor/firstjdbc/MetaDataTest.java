package de.pandooor.firstjdbc;

import de.pandooor.firstjdbc.model.DBConnectionFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;


public class MetaDataTest {

    public static void main(String[] args) {

        try(Connection dbh = DBConnectionFactory.get(DBConnectionFactory.Type.MYSQL);
            Statement stmt = dbh.createStatement()) {

            ResultSet rs = stmt.executeQuery("SELECT * FROM users");

            ResultSetMetaData meta = rs.getMetaData();

            int count = meta.getColumnCount();
            for(int i = 1; i <= count; i++) {
                System.out.println(meta.getColumnName(i));
                System.out.println(meta.getColumnTypeName(i));
                System.out.println();
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}