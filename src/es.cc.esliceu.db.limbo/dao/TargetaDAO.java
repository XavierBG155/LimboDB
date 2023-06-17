package es.cc.esliceu.db.limbo.dao;

import es.cc.esliceu.db.limbo.domain.Targeta;

import java.sql.SQLException;
import java.util.List;

public interface TargetaDAO {
    void afegirTargeta(String targeta, long numero, String caducitat, int codiSeguretat, int id) throws SQLException;

    List<Targeta> selectTargetes(String nomUsuari) throws SQLException;

    void eliminarTargeta(int targetaId) throws SQLException;
}
