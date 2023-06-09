package es.cc.esliceu.db.limbo.dao;

import es.cc.esliceu.db.limbo.GeneradorHash;
import es.cc.esliceu.db.limbo.domain.Usuari;
import es.cc.esliceu.db.limbo.util.Color;

import java.sql.*;

public class LimboDAOImpl implements LimboDAO {

    Connection con = connexio();

    public static Connection connexio() {
        String DB_USERNAME = "xavi";
        String DB_PASSWORD = "1234";
        String DB_HOST = "localhost:3306";
        String DB_DBNAME = "limbo";
        Connection con;

        try {
            con = DriverManager.getConnection(
                    "jdbc:mysql://" + DB_HOST + "/" + DB_DBNAME,
                    DB_USERNAME, DB_PASSWORD
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return con;
    }

    @Override
    public void registreUsuari(Usuari usuari) throws SQLException {
        String sql = "INSERT INTO client (email, nom, cognom1, cognom2, username, contrasenya) VALUES ('"
                + usuari.getEMAIL() + "','" + usuari.getNOM() + "','" + usuari.getLlinatge1()
                + "','" + usuari.getLlinatge2() + "','" + usuari.getUSERNAME() + "','"
                + GeneradorHash.generaHash(usuari.getPASSWORD()) + "');";
        PreparedStatement statementComprovacio = con.prepareStatement("SELECT * FROM client WHERE username = ?");
        statementComprovacio.setString(1, usuari.getUSERNAME());
        ResultSet rsComprovacio = statementComprovacio.executeQuery();
        if (rsComprovacio.next()) {
            System.out.printf(Color.RED_BRIGHT + "Nom d'usuari %s existent", rsComprovacio.getString("username"));
        } else {
            statementComprovacio = con.prepareStatement("SELECT * FROM client WHERE email = ?");
            statementComprovacio.setString(1, usuari.getEMAIL());
            rsComprovacio = statementComprovacio.executeQuery();
            if (rsComprovacio.next()) {
                System.out.printf(Color.RED_BRIGHT + "Email %s existent", rsComprovacio.getString("email"));
            } else {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.execute();
                ps.close();
                try (PreparedStatement statement = con.prepareStatement(
                        "SELECT * FROM client WHERE username = ?"
                )) {
                    statement.setString(1, usuari.getUSERNAME());
                    ResultSet rs = statement.executeQuery();
                    if (rs.next()) {
                        System.out.printf(Color.GREEN_BRIGHT + "Usuari %s creat correctament amb id %d", rs.getString("username"), rs.getInt("numero_client"));
                    }
                    con.close();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }

    }

    @Override
    public void loginUsuari(String s) throws SQLException {
        PreparedStatement statementComprovacio = con.prepareStatement("SELECT * FROM client WHERE username = ?");
        statementComprovacio.setString(1, s);
        ResultSet rsComprovacio = statementComprovacio.executeQuery();
        if (rsComprovacio.next()) {
            System.out.println(Color.CYAN_BRIGHT + "Nom d'usuari correcte");
        } else {
            statementComprovacio = con.prepareStatement("SELECT * FROM client WHERE contrasenya = ?");
            statementComprovacio.setString(1, GeneradorHash.generaHash(s));
            rsComprovacio = statementComprovacio.executeQuery();
            if (rsComprovacio.next()) {
                System.out.printf(Color.CYAN_BRIGHT + "Contrasenya correcta");
            }
        }
    }

    @Override
    public void ajuda(Usuari usuari) throws SQLException {

    }
}
