package es.cc.esliceu.db.limbo.util;

import javax.mail.Quota;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connexio {


    public static Connection getConnection() {

        Connection con = null;
        try {
            FileInputStream input = new FileInputStream("C:\\Users\\xavie\\IdeaProjects\\LimboDB\\resources\\limbo.properties");
            Properties props = new Properties();
            props.load(input);
            String URL = props.getProperty("url");
            String USERNAME = props.getProperty("user");
            String PASSWORD = props.getProperty("password");

            String driverName = "com.mysql.cj.jdbc.Driver";
            Class.forName(driverName);

            try {

                con = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            } catch (SQLException ex) {
                System.out.println("Connexió a la base de dades fallida.");
            }

        } catch (ClassNotFoundException ex) {

            System.out.println("Driver no trobat.");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return con;
    }
}
