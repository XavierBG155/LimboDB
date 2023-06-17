package es.cc.esliceu.db.limbo.dao;

import es.cc.esliceu.db.limbo.domain.Adreca;
import es.cc.esliceu.db.limbo.util.Color;
import es.cc.esliceu.db.limbo.util.Connexio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdrecaDAOImpl implements AdrecaDAO{
    Connection con;
    static UsuariDAO usuariDAO = new UsuariDAOImpl();
    @Override
    public void registreAdreca(Adreca adreca, String nomUsuari) throws SQLException {
        con = Connexio.getConnection();

        PreparedStatement ps = con.prepareStatement("INSERT INTO adreca (client_id, carrer, numero, pis, porta, CP, ciutat_id) VALUES (?,?,?,?,?,?,?)");
        ps.setInt(1, usuariDAO.selectUser(nomUsuari).getID());
        ps.setString(2, adreca.getCarrer());
        ps.setString(3, adreca.getNumero());
        ps.setInt(4, adreca.getPis());
        ps.setString(5, adreca.getPorta());
        ps.setString(6, adreca.getCP());
        ps.setInt(7, adreca.getCiutat_id());
        ps.execute();
        System.out.println(Color.GREEN_BRIGHT + "Adreça afegida correctament");
        con.close();
    }

    @Override
    public List<Adreca> selectAdreces(String nomUsuari) throws SQLException {
        con = Connexio.getConnection();
        List<Adreca> llistaAdreces = new ArrayList<>();

        PreparedStatement ps = con.prepareStatement("SELECT * FROM adreca;");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Adreca adreca = new Adreca(rs.getInt("id"), rs.getInt("client_id"), rs.getString("carrer"), rs.getString("numero"),
                    rs.getInt("pis"), rs.getString("porta"), rs.getString("CP"), rs.getInt("ciutat_id"));
            llistaAdreces.add(adreca);
        }
        con.close();
        return llistaAdreces;
    }
}
