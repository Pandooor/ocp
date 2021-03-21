package de.pandooor.firstjdbc;


import de.pandooor.firstjdbc.model.DBConnectionFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ResultSetTest {

    public static void main(String[] args) {

        try(Connection dbh = DBConnectionFactory.get(DBConnectionFactory.Type.MYSQL);
            Statement stmt = dbh.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {

            ResultSet rs = stmt.executeQuery("SELECT * FROM users");

            System.out.println(rs.isBeforeFirst());

            System.out.println(rs.next());
            System.out.println(rs.getInt("id"));
            System.out.println(rs.getString("firstname"));

            // 1 = id, 2 = firstname, 3 = lastname
            System.out.println(rs.getString(2));

            System.out.println();
            System.out.println(rs.next());
            System.out.println(rs.getInt("id"));
            System.out.println(rs.getString("firstname"));
            System.out.println(rs.getString("lastname"));

            System.out.println();
            System.out.println(rs.absolute(4)); // Absolute Position im Ergebnis
            System.out.println(rs.getInt("id"));
            System.out.println(rs.getString("firstname"));
            System.out.println(rs.getString("lastname"));

            System.out.println();
            System.out.println(rs.previous());
            System.out.println(rs.getInt("id"));
            System.out.println(rs.getString("firstname"));
            System.out.println(rs.getString("lastname"));

            System.out.println();
            System.out.println(rs.previous());
            System.out.println(rs.getInt("id"));
            System.out.println(rs.getString("firstname"));
            System.out.println(rs.getString("lastname"));

            System.out.println();
            System.out.println(rs.previous());
            System.out.println(rs.getInt("id"));
            System.out.println(rs.getString("firstname"));
            System.out.println(rs.getString("lastname"));

            System.out.println();
            System.out.println(rs.relative(3)); // von der aktuellen Position vor- oder rückwärts
            System.out.println(rs.getInt("id"));
            System.out.println(rs.getString("firstname"));
            System.out.println(rs.getString("lastname"));

            System.out.println();
            System.out.println(rs.relative(-3));
            System.out.println(rs.getInt("id"));
            System.out.println(rs.getString("firstname"));
            System.out.println(rs.getString("lastname"));

            System.out.println();
            System.out.println(rs.first());
            System.out.println(rs.getInt("id"));
            System.out.println(rs.getString("firstname"));
            System.out.println(rs.getString("lastname"));

            System.out.println();
            System.out.println(rs.last());
            rs.updateString(2, "Hans"); // zweite Spalte des aktuellen Dattensatzes verändern
            rs.updateRow(); // Änderungen werden persistent gemacht

            System.out.println();
            rs.beforeFirst();
            rs.afterLast();





        } catch (SQLException e) {
            e.printStackTrace();
        }



    }
}

