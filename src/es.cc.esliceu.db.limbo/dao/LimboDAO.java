package es.cc.esliceu.db.limbo.dao;

import es.cc.esliceu.db.limbo.domain.Usuari;

import java.sql.SQLException;

public interface LimboDAO {
    void registreUsuari(Usuari usuari) throws SQLException;
    void loginUsuari(Usuari usuari) throws SQLException;
    void ajuda(Usuari usuari) throws SQLException;
}
