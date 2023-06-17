package es.cc.esliceu.db.limbo.dao;

import es.cc.esliceu.db.limbo.domain.Compra;
import es.cc.esliceu.db.limbo.util.Connexio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompraDAOImpl implements CompraDAO{
    Connection con;

    @Override
    public List<Compra> selectCompra(String nomUsuari) throws SQLException {
        con = Connexio.getConnection();
        List<Compra> llistaCompres = new ArrayList<>();

        PreparedStatement ps = con.prepareStatement("SELECT * FROM categoria;");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Compra compra = new Compra(rs.getInt("id"), rs.getInt("targeta_id"), rs.getInt("adreca_id"),
                    rs.getInt("client_id"), rs.getString("id_transaccio"), rs.getDate("data"));
            llistaCompres.add(compra);
        }
        con.close();
        return llistaCompres;
    }

    @Override
    public double getDetallCompra(int id) throws SQLException {
        con = Connexio.getConnection();
        double preu = 0;

        PreparedStatement ps = con.prepareStatement("SELECT * FROM detall_compra WHERE compra_id = ?;");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) preu = rs.getDouble("pvp");
        con.close();
        return preu;
    }


}
