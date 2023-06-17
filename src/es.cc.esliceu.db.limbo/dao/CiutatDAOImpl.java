package es.cc.esliceu.db.limbo.dao;

import es.cc.esliceu.db.limbo.domain.Ciutat;
import es.cc.esliceu.db.limbo.util.Connexio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CiutatDAOImpl implements CiutatDAO{
    Connection con;

    @Override
    public Ciutat selectCiutat(){
        con = Connexio.getConnection();
        Ciutat ciutat = null;
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM ciutat;");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ciutat = new Ciutat(rs.getInt("id"), rs.getString("nom"), rs.getInt("provincia_id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ciutat;
    }
}
