package es.cc.esliceu.db.limbo.dao;

import es.cc.esliceu.db.limbo.domain.Ciutat;

import java.sql.SQLException;

public interface CiutatDAO {
    Ciutat selectCiutat() throws SQLException;
}
