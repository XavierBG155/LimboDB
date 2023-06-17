package es.cc.esliceu.db.limbo.domain;

import java.util.Date;

public class Compra {
    int id;
    int targetaID;
    int adrecaID;
    int clientID;
    String idTransaccio;
    Date data;

    public Compra(int id, int targetaID, int adrecaID, int clientID, String idTransaccio, Date data) {
        this.id = id;
        this.targetaID = targetaID;
        this.adrecaID = adrecaID;
        this.clientID = clientID;
        this.idTransaccio = idTransaccio;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTargetaID() {
        return targetaID;
    }

    public void setTargetaID(int targetaID) {
        this.targetaID = targetaID;
    }

    public int getAdrecaID() {
        return adrecaID;
    }

    public void setAdrecaID(int adrecaID) {
        this.adrecaID = adrecaID;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public String getIdTransaccio() {
        return idTransaccio;
    }

    public void setIdTransaccio(String idTransaccio) {
        this.idTransaccio = idTransaccio;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
