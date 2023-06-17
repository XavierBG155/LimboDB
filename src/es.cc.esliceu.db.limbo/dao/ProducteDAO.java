package es.cc.esliceu.db.limbo.dao;

import es.cc.esliceu.db.limbo.domain.Categoria;
import es.cc.esliceu.db.limbo.domain.Producte;

import java.sql.SQLException;
import java.util.List;

public interface ProducteDAO {
    Producte producteAleatori() throws SQLException;

    List<Producte> filtraProductes(String nomProducte, String descripcio, String marca, String categoria) throws SQLException;

    List<Categoria> getProductes() throws SQLException;
}
