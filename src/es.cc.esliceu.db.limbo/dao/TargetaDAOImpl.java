package es.cc.esliceu.db.limbo.dao;

import es.cc.esliceu.db.limbo.Limbo;
import es.cc.esliceu.db.limbo.domain.Targeta;
import es.cc.esliceu.db.limbo.util.Color;
import es.cc.esliceu.db.limbo.util.Connexio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TargetaDAOImpl implements TargetaDAO{
    Connection con;

    static UsuariDAO usuariDAO = new UsuariDAOImpl();
    @Override
    public void afegirTargeta(String tipus, long numero, String caducitat, int codiSeguretat, int id) throws SQLException {
        con = Connexio.getConnection();
        Targeta targeta = new Targeta(0, tipus, numero, caducitat, codiSeguretat, id);

        PreparedStatement ps = con.prepareStatement("INSERT INTO targeta (tipus, numero, data_caducitat, codi_seguretat, client_id) VALUES (?,?,?,?,?)");
        ps.setString(1, targeta.getTipus());
        ps.setLong(2, targeta.getNumero());
        ps.setString(3, targeta.getCaducitat());
        ps.setInt(4, targeta.getCodiSeguretat());
        ps.setInt(5,id);
        ps.execute();
        System.out.println(Color.GREEN_BRIGHT + "Targeta afegida correctament");
        con.close();
    }

    @Override
    public List<Targeta> selectTargetes(String nomUsuari) throws SQLException {
        con = Connexio.getConnection();
        List<Targeta> llistaTargetes = new ArrayList<>();

        PreparedStatement ps = con.prepareStatement("SELECT * FROM targeta;");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Targeta targeta = new Targeta(rs.getInt("id"), rs.getString("tipus"), rs.getLong("numero"),
                    rs.getString("data_caducitat"), rs.getInt("codi_seguretat"),
                    usuariDAO.selectUser(nomUsuari).getID());
            llistaTargetes.add(targeta);
        }
        con.close();
        return llistaTargetes;
    }

    @Override
    public void eliminarTargeta(int targetaId) throws SQLException {
        con = Connexio.getConnection();

        PreparedStatement ps = con.prepareStatement("SELECT * FROM targeta WHERE id = ?");
        ps.setInt(1,targetaId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()){
            ps = con.prepareStatement("DELETE FROM targeta WHERE id = ?");
            ps.execute();
            System.out.println(Color.GREEN_BRIGHT+"Eliminida correctament.");
            con.close();
        } else{
            System.out.println(Color.RED_BRIGHT + "Targeta no vàlida");
        }

    }
}
