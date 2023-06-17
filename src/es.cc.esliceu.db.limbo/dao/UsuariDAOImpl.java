package es.cc.esliceu.db.limbo.dao;

import es.cc.esliceu.db.limbo.GeneradorHash;
import es.cc.esliceu.db.limbo.domain.Usuari;
import es.cc.esliceu.db.limbo.util.Color;
import es.cc.esliceu.db.limbo.util.Connexio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class UsuariDAOImpl implements UsuariDAO{
    Connection con;

    @Override
    public void registreUsuari(Usuari usuari) throws SQLException {
        con = Connexio.getConnection();
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
    public boolean loginUsuari(String dada, String s) throws SQLException {
        con = Connexio.getConnection();
        PreparedStatement ps;
        if (Objects.equals(dada, "contrasenya")) {
            ps = con.prepareStatement("SELECT * FROM client WHERE contrasenya = ?");
            ps.setString(1, GeneradorHash.generaHash(s));
        } else {
            ps = con.prepareStatement("SELECT * FROM client WHERE username = ?");
            ps.setString(1, s);
        }
        ResultSet rsComprovacio = ps.executeQuery();

        if (Objects.equals(dada, "username") && rsComprovacio.next()) {
            System.out.println(Color.GREEN_BRIGHT + "Nom d'usuari vàlid");
            return true;
        } else if (Objects.equals(dada, "contrasenya") && rsComprovacio.next()) {
            System.out.println(Color.GREEN_BRIGHT + "Contrasenya vàlida");
            return true;
        } else if (!rsComprovacio.next() && Objects.equals(dada, "contrasenya")) {
            System.out.println(Color.RED_BRIGHT + "Contrasenya invàlida" + Color.RESET);
            return false;
        } else if (!rsComprovacio.next() && Objects.equals(dada, "username")) {
            System.out.println(Color.RED_BRIGHT + "Username invàlid" + Color.RESET);
            return false;
        } else {
            System.out.println("Usuari no existeix");
            return false;
        }

    }

    @Override
    public Usuari selectUser(String username) {
        con = Connexio.getConnection();
        Usuari user = null;
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM client WHERE username = ?;");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new Usuari(rs.getInt("numero_client"), rs.getString("email"), rs.getString("nom"), rs.getString("cognom1"),
                        rs.getString("cognom2"), rs.getString("username"), rs.getString("contrasenya"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;

    }





    @Override
    public void canviaContrasenya(String passAntinc, String passNou) {
        con = Connexio.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM client WHERE contrasenya = ?;");
            ps.setString(1, GeneradorHash.generaHash(passAntinc));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ps = con.prepareStatement("UPDATE client SET contrasenya = ? WHERE contrasenya = ?");
                ps.setString(1, GeneradorHash.generaHash(passNou));
                ps.setString(2, GeneradorHash.generaHash(passAntinc));
                ps.execute();
                System.out.println(Color.GREEN_BRIGHT + "Canviada correctament");
                con.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void canviaDades(String nom, String llin1, String llin2, String userNOM, String userLlinatge1, String userLlinatge2) {
        con = Connexio.getConnection();
        try {
            PreparedStatement ps;
            if (nom.length() > 0) {
                ps = con.prepareStatement("UPDATE client SET nom = ? WHERE nom = ?");
                ps.setString(1, nom);
                ps.setString(2, userNOM);
                ps.execute();
            }
            if (llin1.length() > 0) {
                ps = con.prepareStatement("UPDATE client SET cognom1 = ? WHERE cognom1 = ?");
                ps.setString(1, llin1);
                ps.setString(2, userLlinatge1);
                ps.execute();
            }
            if (llin2.length() > 0) {
                ps = con.prepareStatement("UPDATE client SET cognom2 = ? WHERE cognom2 = ?");
                ps.setString(1, llin2);
                ps.setString(2, userLlinatge2);
                ps.execute();
            }
            System.out.println(Color.GREEN_BRIGHT + "Dades canviades correctament.");
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
