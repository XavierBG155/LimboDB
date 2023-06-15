package es.cc.esliceu.db.limbo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexio {
    Connection con;
    Connexio() throws SQLException {
        String DB_USERNAME = "xavi";
        String DB_PASSWORD = "1234";
        String DB_HOST = "localhost:3306";
        String DB_DBNAME = "limbo";
        con = DriverManager.getConnection(
                "jdbc:mysql://" + DB_HOST + "/" + DB_DBNAME,
                DB_USERNAME, DB_PASSWORD
        );
    }
}
