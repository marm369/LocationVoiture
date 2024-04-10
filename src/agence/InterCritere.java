package agence;

import java.util.ArrayList;
import java.util.List;

public class InterCritere implements Critere {
	List <Critere> liste;
	public InterCritere(List <Critere> listeCritere)
	{
		liste=listeCritere;
	}
	void addCritere(Critere c)
	{
		liste.add(c);
	}
	
	@Override
	public boolean estSatisfaitPar(Voiture v) {
	for (Critere cr : liste) {
	 if (!cr.estSatisfaitPar(v)) 
	 {
	     return false;
	 }
	}
	 return true;
	    }
	
	
}
