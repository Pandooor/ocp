package de.pandooor.firstjdbc;


import de.pandooor.firstjdbc.model.DBConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PreparedStatementTest {

    public static void main(String[] args) {

        String sql = "INSERT INTO users (firstname, lastname) VALUES(?, ?)";

        try(Connection dbh = DBConnectionFactory.get(DBConnectionFactory.Type.MYSQL);
            PreparedStatement stmt = dbh.prepareStatement(sql)) {

            stmt.setString(1, "Hans");
            stmt.setString(2, "Mustermann");
            stmt.execute();

            stmt.setString(1, "Christine");
            stmt.setString(2, "Hansen");
            stmt.execute();

            stmt.setString(1, "Rolf");
            stmt.setString(2, "Petersen");
            stmt.execute();

            stmt.setString(1, "Max");
            stmt.setString(2, "Mustermann");
            stmt.execute();

            stmt.setString(1, "Anna");
            stmt.setString(2, "Mustermann");
            stmt.execute();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }



    }

}

