package agence;

import java.util.Objects;

public class Voiture {
	private String Marque;
	private String nom;
	private int anneeProd;
	private int prix;
	private String image;
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Voiture(String marq , String nom , int annee, int prix , String img)
	{
		this.Marque=marq;
		this.nom=nom;
		this.anneeProd=annee;
		this.prix=prix;
		this.image=img;
	}
	@Override
	public String toString() {
		return Marque +" " + nom + " " + anneeProd + " " + prix;
	}
	
	public boolean equals(Voiture v)
	{
		return Objects.equals(Marque, v.Marque) && anneeProd == v.anneeProd && Objects.equals(nom, v.nom)
				&& prix == v.prix;
	}
	public String getMarque() {
		return Marque;
	}
	public void setMarque(String marque) {
		Marque = marque;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public int getAnneeProd() {
		return anneeProd;
	}
	public void setAnneeProd(int anneeProd) {
		this.anneeProd = anneeProd;
	}
	public int getPrix() {
		return prix;
	}
	public void setPrix(int prix) {
		this.prix = prix;
	}
	
	

}
