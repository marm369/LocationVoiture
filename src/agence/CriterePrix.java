package agence;

import agence.Critere;
import agence.Voiture;

public class CriterePrix implements Critere {
    private int prixMax;
    
    public CriterePrix(int prixMax) {
        this.prixMax = prixMax;
    }

    @Override
    public boolean estSatisfaitPar(Voiture v) {
        return v.getPrix()< prixMax;
    }
}

