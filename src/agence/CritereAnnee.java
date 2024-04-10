package agence;

import agence.Critere;
import agence.Voiture;

public class CritereAnnee implements Critere {
	private int anneePr;

    public CritereAnnee(int annee) {
        this.anneePr = annee;
    }
    @Override
    public boolean estSatisfaitPar(Voiture v) {
    	 return v.getAnneeProd() == anneePr;
    }

}
