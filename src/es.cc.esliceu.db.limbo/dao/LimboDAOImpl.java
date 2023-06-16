package es.cc.esliceu.db.limbo.dao;

import es.cc.esliceu.db.limbo.GeneradorHash;
import es.cc.esliceu.db.limbo.domain.Usuari;
import es.cc.esliceu.db.limbo.util.Color;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Objects;
import java.util.Properties;

public class LimboDAOImpl implements LimboDAO {

    Connection con;

    public void connexio() throws IOException {
        /*FileInputStream input = new FileInputStream("C:\\Users\\berga\\Desktop\\classe\\LimboDB\\resources\\limbo.properties");*/
        FileInputStream input = new FileInputStream("C:\\Users\\xavie\\IdeaProjects\\LimboDB\\resources\\limbo.properties");
        Properties props = new Properties();
        props.load(input);
        String URL = props.getProperty("url");
        String USERNAME = props.getProperty("user");
        String PASSWORD = props.getProperty("password");

        try {
            DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void registreUsuari(Usuari usuari) throws SQLException {
        try {
            connexio();
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void loginUsuari(String dada, String s) throws SQLException {
        try {
            connexio();
            PreparedStatement statementComprovacio = con.prepareStatement("SELECT * FROM client WHERE ? = ?");
            statementComprovacio.setString(1, dada);
            statementComprovacio.setString(2, s);
            ResultSet rsComprovacio = statementComprovacio.executeQuery();

            if (rsComprovacio.next() && Objects.equals(dada, "username")) {
                System.out.println(Color.GREEN_BRIGHT + "Nom d'usuari vàlid");
            } else if (rsComprovacio.next() && Objects.equals(dada, "contrasenya")) {
                System.out.println(Color.GREEN_BRIGHT + "Contrasenya vàlida");
            } else {
                System.out.println(dada + " no vàlid");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void ajuda(Usuari usuari) throws SQLException {
    }
}
