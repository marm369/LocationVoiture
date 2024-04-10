package agence;


import agence.Critere;
import agence.Voiture;

public class CritereMarque implements Critere {
	private String marqueCible;

    CritereMarque(String marqueCible) {
        this.marqueCible = marqueCible;
    }
    @Override
    public boolean estSatisfaitPar(Voiture v) {
        return v.getMarque().equals(marqueCible);
    }

}
