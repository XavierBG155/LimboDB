package es.cc.esliceu.db.limbo.dao;

import es.cc.esliceu.db.limbo.domain.Usuari;

import java.sql.SQLException;

public interface UsuariDAO {
    void registreUsuari(Usuari usuari) throws SQLException;
    boolean loginUsuari(String dada, String s) throws SQLException;
    Usuari selectUser(String username) throws SQLException;

    void canviaContrasenya(String passAntinc, String passNou);

    void canviaDades(String nom, String llin1, String llin2, String userNOM, String userLlinatge1, String userLlinatge2);
}
