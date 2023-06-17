package es.cc.esliceu.db.limbo.dao;

import es.cc.esliceu.db.limbo.domain.Compra;

import java.sql.SQLException;
import java.util.List;

public interface CompraDAO {
    List<Compra> selectCompra(String nomUsuari) throws SQLException;

    double getDetallCompra(int id) throws SQLException;
}
