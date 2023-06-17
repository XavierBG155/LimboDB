package es.cc.esliceu.db.limbo.domain;

public class Usuari {
    int ID;
    String USERNAME;
    String EMAIL;
    String PASSWORD;
    String NOM;
    String Llinatge1, Llinatge2;

    public Usuari(int ID, String EMAIL, String NOM, String llinatge1,
                  String llinatge2, String USERNAME, String PASSWORD) {
        this.ID = ID;
        this.USERNAME = USERNAME;
        this.EMAIL = EMAIL;
        this.PASSWORD = PASSWORD;
        this.NOM = NOM;
        this.Llinatge1 = llinatge1;
        this.Llinatge2 = llinatge2;
    }

    public int getID() {return ID;}

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public String getNOM() {
        return NOM;
    }

    public void setNOM(String NOM) {
        this.NOM = NOM;
    }

    public String getLlinatge1() {
        return Llinatge1;
    }

    public void setLlinatge1(String llinatge1) {
        Llinatge1 = llinatge1;
    }

    public String getLlinatge2() {
        return Llinatge2;
    }

    public void setLlinatge2(String llinatge2) {
        Llinatge2 = llinatge2;
    }

}
