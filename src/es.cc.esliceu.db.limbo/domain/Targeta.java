package es.cc.esliceu.db.limbo.domain;

public class Targeta {
    int ID;
    String tipus;
    long numero;
    String caducitat;
    int codiSeguretat;
    int client_id;

    public Targeta(int ID, String tipus, long numero, String caducitat, int codiSeguretat, int client_id) {
        this.ID = ID;
        this.tipus = tipus;
        this.numero = numero;
        this.caducitat = caducitat;
        this.codiSeguretat = codiSeguretat;
        this.client_id = client_id;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTipus() {
        return tipus;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }

    public long getNumero() {
        return numero;
    }

    public void setNumero(long numero) {
        this.numero = numero;
    }

    public String getCaducitat() {
        return caducitat;
    }

    public void setCaducitat(String caducitat) {
        this.caducitat = caducitat;
    }

    public int getCodiSeguretat() {
        return codiSeguretat;
    }

    public void setCodiSeguretat(int codiSeguretat) {
        this.codiSeguretat = codiSeguretat;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }
}
