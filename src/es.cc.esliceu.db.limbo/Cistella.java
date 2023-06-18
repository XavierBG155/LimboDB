package es.cc.esliceu.db.limbo;

import es.cc.esliceu.db.limbo.ProducteEnCistella;
import es.cc.esliceu.db.limbo.domain.Producte;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cistella {

    private Map<Producte, ProducteEnCistella> entrades;

    public Cistella() {
        this.entrades = new HashMap<>();
    }

    public void afegirProducte (Producte producte){
        ProducteEnCistella producteEnCistella = entrades.get(producte);
        if (producteEnCistella != null){
            producteEnCistella.pujarQuantitat();
        } else {
            ProducteEnCistella producteEnCistella1 = new ProducteEnCistella(producte, 1);
            entrades.put(producte, producteEnCistella1);
        }
    }
    public void eliminarProducte (Producte producte){
        ProducteEnCistella producteEnCistella = entrades.get(producte);
        if (producteEnCistella != null){
            producteEnCistella.baixarQuantitat();
        }
    }

    public int getQuantity(Producte producte){
        ProducteEnCistella producteEnCistella = entrades.get(producte);
        if (producteEnCistella != null){
            return producteEnCistella.getQuantitat();
        }
        return 0;
    }

    public double calculaTotal(){
        double total = 0;
        for (ProducteEnCistella producteEnCistella : entrades.values()){
            double costoTotalProducte = producteEnCistella.getProducte().getPvp()*producteEnCistella.getQuantitat();
            total += costoTotalProducte;
        }
        return total;
    }

    public List<ProducteEnCistella> llistaProductes() {
        return new ArrayList<>(entrades.values());
    }
}
