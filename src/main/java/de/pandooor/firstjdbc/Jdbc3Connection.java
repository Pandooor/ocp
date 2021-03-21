package de.pandooor.firstjdbc;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Jdbc3Connection {

    public static void main(String[] args) {

        Properties prop = new Properties();
//		prop.setProperty("db.host", "localhost");
//		prop.setProperty("db.port", "3306");
//		prop.setProperty("db.dbname", "user_db");
//		prop.setProperty("user", "root");
//		prop.setProperty("password", "root");
//
//		try(OutputStream out = new FileOutputStream("basic.xml")) {
//			prop.storeToXML(out, "Bla bla bla", "UTF-8");
//		}
//		catch(IOException e) {
//			System.out.println(e.getMessage());
//		}


        try(InputStream in = new FileInputStream("basic.xml")) {
            prop.loadFromXML(in);
        }
        catch(IOException e) {
            System.out.println(e.getMessage());
        }


        try {

            String url = "jdbc:mysql://%s:%s/%s?serverTimezone=UCT";

            url = String.format(url,
                    prop.getProperty("db.host"),
                    prop.getProperty("db.port"),
                    prop.getProperty("db.dbname"));

            Connection dbh = DriverManager.getConnection(url, prop);

            dbh.close();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

}

