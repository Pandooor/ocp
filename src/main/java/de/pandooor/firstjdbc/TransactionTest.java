package de.pandooor.firstjdbc;


import de.pandooor.firstjdbc.model.DBConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

public class TransactionTest {

    public static void main(String[] args) {

        try(Connection dbh = DBConnectionFactory.get(DBConnectionFactory.Type.MYSQL);
            Statement stmt = dbh.createStatement()) {

            dbh.setAutoCommit(false); // Automatisches übertragen der Anweisungen wird abgestellt

            stmt.executeUpdate("INSERT INTO users (firstname, lastname) VALUES('Peter', 'Parker')");
            stmt.executeUpdate("INSERT INTO users (firstname, lastname) VALUES('Peter', 'Hansen')");
            stmt.executeUpdate("INSERT INTO users (firstname, lastname) VALUES('Hans', 'Parkersen')");

            dbh.commit(); // Überträgt alle Befehle zusammen als eine Anweisung an die DB

        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("----------- SAVEPOINTS ------------");


        try(Connection dbh = DBConnectionFactory.get(DBConnectionFactory.Type.MYSQL);
            Statement stmt = dbh.createStatement()) {


            Savepoint s1 = null;
            Savepoint s2 = null;
            Savepoint s3 = null;

            try {
                dbh.setAutoCommit(false); // Automatisches übertragen der Anweisungen wird abgestellt

                stmt.executeUpdate("INSERT INTO users (firstname, lastname) VALUES('Peter', 'Parker...')");
                s1 = dbh.setSavepoint();

                stmt.executeUpdate("INSERT INTO users (firstname, lastname) VALUES('Peter', 'Hansen')");
                s2 = dbh.setSavepoint();

                stmt.executeUpdate("INSERT INTO users (firstname, last_name) VALUES('Hans', 'Parkersen')");
                s3 = dbh.setSavepoint();

                dbh.commit(); // Überträgt alle Befehle zusammen als eine Anweisung an die DB

                //dbh.releaseSavepoint(s1); // Entfernt einen Savepoint
            }
            catch(SQLException e) {
                // Ausführung des Statements
                System.out.println(e.getMessage());

                dbh.rollback(s1); // Die Warteschlange der Befehle bis zum s1 zurückrollen
                dbh.commit();
            }
        }
        catch(SQLException e) {
            // Aufbau der Datenbankverbindung und des Statementts
            System.out.println(e.getMessage());
        }



    }

}
