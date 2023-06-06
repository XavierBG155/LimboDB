package es.cc.esliceu.db.limbo.dao;

import es.cc.esliceu.db.limbo.domain.Usuari;

import java.sql.*;

public class LimboDAOImpl implements LimboDAO {


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
        Connection con = connexio();

        String sql = "INSERT INTO client (email, nom, cognom1, cognom2, username, contrasenya) VALUES ('"
                + usuari.getEMAIL() + "','" + usuari.getNOM() + "','" + usuari.getLlinatge1()
                + "','" + usuari.getLlinatge2() + "','" + usuari.getUSERNAME() + "','"
                + usuari.getPASSWORD() + "');";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.execute();
        ps.close();
        try (PreparedStatement statement = con.prepareStatement(
                "SELECT * FROM client WHERE username = ?"
        )) {
            statement.setString(1, usuari.getUSERNAME());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                /*Usuari usuariSelect = new Usuari(rs.getString("email"), rs.getString("nom"), rs.getString("cognom1"), rs.getString("cognom2"), rs.getString("username"), rs.getString("contrasenya"));
                 */
                System.out.printf("Usuari %s creat correctament amb id %d", rs.getString("username"), rs.getInt("numero_client"));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void loginUsuari(Usuari usuari) throws SQLException {

    }

    @Override
    public void ajuda(Usuari usuari) throws SQLException {

    }
}
