package agence;

import java.util.Objects;

public class Client implements Comparable <Client>
{
	private String nom;
	private String prenom;
	private String CIN;
	private String civilite;
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getCIN() {
		return CIN;
	}
	public void setCIN(String cIN) {
		CIN = cIN;
	}
	public String getCivilite() {
		return civilite;
	}
	public void setCivilite(String civilite) {
		this.civilite = civilite;
	}
	public Client(String nom, String prenom, String cIN, String civilite)
	{
		super();
		this.nom = nom;
		this.prenom = prenom;
		CIN = cIN;
		this.civilite = civilite;
	}

	  public boolean equals(Object obj) {
		    if (this == obj) {
		        return true;
		    }
		    if (obj == null || getClass() != obj.getClass()) {
		        return false;
		    }
		    Client other = (Client) obj;
		    return Objects.equals(prenom, other.prenom)&&Objects.equals(nom, other.nom)
		    		&& Objects.equals(CIN, other.CIN)&&Objects.equals(civilite, other.civilite);
		}


	@Override
	   public int compareTo(Client other) {
        // Comparaison alphabétique des noms et prénoms
        int result = nom.compareTo(other.nom);
        if (result == 0) {
            result = prenom.compareTo(other.prenom);
        }
        return result;
    }
	  @Override
	    public String toString() {
	        return civilite + " " + prenom + " " + nom + " " + CIN ;
	    }
}