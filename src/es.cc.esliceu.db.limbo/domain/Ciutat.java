package es.cc.esliceu.db.limbo.domain;

public class Ciutat {
    int ID;
    String nom;
    int provincia_id;

    public Ciutat(int ID, String nom, int provincia_id) {
        this.ID = ID;
        this.nom = nom;
        this.provincia_id = provincia_id;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getProvincia_id() {
        return provincia_id;
    }

    public void setProvincia_id(int provincia_id) {
        this.provincia_id = provincia_id;
    }
}
