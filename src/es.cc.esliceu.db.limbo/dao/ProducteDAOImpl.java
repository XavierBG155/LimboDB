package es.cc.esliceu.db.limbo.dao;

import es.cc.esliceu.db.limbo.domain.Categoria;
import es.cc.esliceu.db.limbo.domain.Producte;
import es.cc.esliceu.db.limbo.util.Connexio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProducteDAOImpl implements ProducteDAO{
    Connection con;

    public Producte producteAleatori() {
        con = Connexio.getConnection();
        Producte producte = null;
        int aleatori = (int) Math.round((Math.random() * 76) + 1);
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM producte WHERE id = ?;");
            ps.setInt(1, aleatori);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                producte = new Producte(rs.getString("nom"), rs.getString("descripcio"), rs.getDouble("pvp"), rs.getInt("iva"),
                        rs.getString("marca"), rs.getString("unitat_mesura"), rs.getDouble("pes"), rs.getInt("categoria"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return producte;
    }

    @Override
    public List<Producte> filtraProductes(String nomProducte, String descripcio, String marca, String categoria) throws SQLException {
        con = Connexio.getConnection();
        List<Producte> llistaProductes = new ArrayList<>();

        String sql = "SELECT * FROM producte";
        PreparedStatement ps;
        ResultSet rs = null;
        int filtres = 0;
        if (nomProducte.length() > 0) {
            sql += " WHERE nom LIKE ";
            ps = con.prepareStatement(sql + "?");
            ps.setString(1, "%" + nomProducte + "%");
            rs = ps.executeQuery();
            filtres++;
        }
        if (descripcio.length() > 0) {
            if (filtres > 0) {
                sql += " AND descripcio LIKE ";
            } else sql += " WHERE descripcio LIKE ";
            ps = con.prepareStatement(sql + "?");
            ps.setString(1, "%" + descripcio + "%");
            rs = ps.executeQuery();
            filtres++;
        }
        if (marca.length() > 0) {
            if (filtres > 0) {
                sql += " AND marca LIKE ";
            } else sql += " WHERE marca LIKE ";
            ps = con.prepareStatement(sql + "?");
            ps.setString(1, "%" + marca + "%");
            rs = ps.executeQuery();
            filtres++;
        }
        if (categoria.length() > 0) {
            if (filtres > 0) {
                sql += " AND categoria LIKE ";
            } else sql += " WHERE categoria LIKE ";
            ps = con.prepareStatement(sql + "?");
            ps.setString(1, "%" + categoria + "%");
            rs = ps.executeQuery();
        }
        if (nomProducte.length() == 0 && descripcio.length() == 0 && marca.length() == 0 && categoria.length() == 0) {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
        }
        afegirProductesALlista(llistaProductes, rs);
        con.close();
        return llistaProductes;
    }

    private void afegirProductesALlista(List<Producte> llistaProductes, ResultSet rs) throws SQLException {
        while (rs.next()) {
            Producte producte = new Producte(rs.getString("nom"), rs.getString("descripcio"), rs.getDouble("pvp"), rs.getInt("iva"),
                    rs.getString("marca"), rs.getString("unitat_mesura"), rs.getDouble("pes"), rs.getInt("categoria"));
            llistaProductes.add(producte);
        }

    }

    @Override
    public List<Categoria> getProductes() throws SQLException {

        con = Connexio.getConnection();
        List<Categoria> llistaCategories = new ArrayList<>();

        PreparedStatement ps = con.prepareStatement("SELECT * FROM categoria;");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Categoria categoria = new Categoria(rs.getString("id"), rs.getString("nom"), rs.getString("descripcio"));
            llistaCategories.add(categoria);
        }
        con.close();
        return llistaCategories;
    }
}
