package de.pandooor.firstjdbc;

import de.pandooor.firstjdbc.model.DBConnectionFactory;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CallableStatementTest {

    public static void main(String[] args) {

        try(Connection dbh = DBConnectionFactory.get(DBConnectionFactory.Type.MYSQL);
            CallableStatement stmt = dbh.prepareCall("{call get_all_users(?)}")) {

            stmt.setString(1, "Peter");

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                System.out.println(rs.getString("firstname") + " " + rs.getString("lastname"));
            }

        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }


    }

}
