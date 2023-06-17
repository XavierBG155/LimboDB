package es.cc.esliceu.db.limbo.dao;

import es.cc.esliceu.db.limbo.domain.Adreca;

import java.sql.SQLException;
import java.util.List;

public interface AdrecaDAO {

    List<Adreca> selectAdreces(String nomUsuari) throws SQLException;

    void registreAdreca(Adreca adreca, String nomUsuari) throws SQLException;
}
