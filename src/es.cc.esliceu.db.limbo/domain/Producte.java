package es.cc.esliceu.db.limbo.domain;

public class Producte {
    String nom;
    String descripcio;
    double pvp;
    int iva;

    String marca;
    String unitatDeMesura;
    double pes;
    int categoria;

    public Producte(String nom, String descripcio, double pvp, int iva, String marca, String unitatDeMesura, double pes, int categoria) {
        this.nom = nom;
        this.descripcio = descripcio;
        this.pvp = pvp;
        this.iva = iva;
        this.marca = marca;
        this.unitatDeMesura = unitatDeMesura;
        this.pes = pes;
        this.categoria = categoria;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public double getPvp() {
        return pvp;
    }

    public void setPvp(double pvp) {
        this.pvp = pvp;
    }

    public int getIva() {
        return iva;
    }

    public void setIva(int iva) {
        this.iva = iva;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getUnitatDeMesura() {
        return unitatDeMesura;
    }

    public void setUnitatDeMesura(String unitatDeMesura) {
        this.unitatDeMesura = unitatDeMesura;
    }

    public double getPes() {
        return pes;
    }

    public void setPes(double pes) {
        this.pes = pes;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }
}
