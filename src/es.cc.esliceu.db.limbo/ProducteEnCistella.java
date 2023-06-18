package es.cc.esliceu.db.limbo;

import es.cc.esliceu.db.limbo.domain.Producte;

public class ProducteEnCistella {
    private Producte producte;
    private int quantitat;

    public ProducteEnCistella(Producte producte, int quantitat) {
        this.producte = producte;
        this.quantitat = quantitat;
    }

    public Producte getProducte() {
        return producte;
    }

    public int getQuantitat() {
        return quantitat;
    }

    public void pujarQuantitat(){
        this.quantitat++;
    }
    public void baixarQuantitat(){
        if (this.quantitat > 0) this.quantitat--;
    }

    public String getNom(){
        return this.producte.getNom();
    }

    public double getPreu(){
        return this.producte.getPvp();
    }
}
