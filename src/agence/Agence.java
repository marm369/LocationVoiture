package agence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;






public class Agence {
    private List<Voiture> voitures;
    private List<Client> clients;
    private Map <Client ,Voiture> locations;

    public Agence(List<Voiture> l,List<Client>c) {
        voitures = l;
        clients = c;
        this.locations = new TreeMap<>(); 
    }
    public Agence(List<Voiture> l,Map<Client, Voiture> lc) {
        voitures = l;
        locations = lc;
    }
    public Agence(){
    	  clients = new ArrayList<>(); 
    	  voitures = new ArrayList<>(); 
    	  locations = new TreeMap<>();
    	  }

    public List<Voiture> getVoitures() {
		return voitures;
	}
	public void setVoitures(List<Voiture> voitures) {
		this.voitures = voitures;
	}
	public List<Client> getClients() {
		return clients;
	}
	public void setClients(List<Client> clients) {
		this.clients = clients;
	}
	public Map<Client, Voiture> getLocations() {
		return locations;
	}
	public void setLocations(Map<Client, Voiture> locations) {
		this.locations = locations;
	}
	public Iterator<Voiture> selectionne(Critere c) {
        Iterator<Voiture> v = voitures.iterator();
        List<Voiture> v_cr = new ArrayList<>();
        while (v.hasNext()) {
            if (c.estSatisfaitPar(v.next()))
                v_cr.add(v.next());
        }
        return v_cr.iterator();
    }

    public void afficheSelectionne(Critere c) {
        Iterator<Voiture> iter = selectionne(c);
        while (iter.hasNext()) {
            Voiture voiture = iter.next();
            System.out.println(voiture);
        }
    }

    public List<Voiture> selectionneTous(InterCritere interCritere) {
        List<Voiture> voituresSelectionnees = new ArrayList<>();
        Iterator<Voiture> iter = locations.values().iterator();

        while (iter.hasNext()) {
            Voiture voiture = iter.next();
            if (interCritere.estSatisfaitPar(voiture)) {
                voituresSelectionnees.add(voiture);
            }
        }

        return voituresSelectionnees;
    }

    public void afficheVoitures(List<Voiture> voitures) {
        for (Voiture voiture : voitures) {
            System.out.println(voiture);
        }
    }
    public void loueVoiture(Client client, Voiture v) throws VoitureNotFoundException, VoitureDejaLoueeException {
        if (!voitures.contains(v)) {
            throw new VoitureNotFoundException("La voiture n'existe pas dans l'agence.");
        }

        if (locations.containsValue(v)) {
            throw new VoitureDejaLoueeException("La voiture est déjà louée.");
        }

        locations.put(client, v);
    }

    public class VoitureNotFoundException extends Exception {
        public VoitureNotFoundException(String message) {
            super(message);
        }
    }
    public class VoitureDejaLoueeException extends Exception {
        public VoitureDejaLoueeException(String message) {
            super(message);
        }
    }
    public boolean estLoueur(Client client)
   {
	   return locations.containsKey(client);
   }
    public boolean estLoue(Voiture v) {
       return locations.containsValue(v);
   }
	public void rendVoiture(Client client)
	{
		locations.remove(client);
		
	}
	
	public Iterator lesVoituresLouees()
	{
		return locations.values().iterator();
	}
	 public Iterator<Client> lesClients() {
	        return locations.keySet().iterator();
	    }
	 public void rechercherParCriteres(Agence agence) {
	        Scanner scanner = new Scanner(System.in);
	        List<Critere> criteres = new ArrayList<>();

	        System.out.print("Entrez la marque de la voiture (ou appuyez sur Entrée pour ignorer) : ");
	        String marque = scanner.nextLine();
	        if (!marque.isEmpty()) 
	        {
	            criteres.add(new CritereMarque(marque));
	        }
	        System.out.print("Entrez l'année de la voiture (ou appuyez sur Entrée pour ignorer) : ");
	        String anneeStr = scanner.nextLine();
	        if (!anneeStr.isEmpty()) 
	        {
	            int annee = Integer.parseInt(anneeStr);
	            criteres.add(new CritereAnnee(annee));
	        }

	        System.out.print("Entrez le prix maximum de la voiture (ou appuyez sur Entrée pour ignorer) : ");
	        
	            String prixMax = scanner.nextLine();
	            if (!prixMax.isEmpty()) 
	            {
	            	int prix = Integer.parseInt(prixMax);
	                criteres.add(new CriterePrix(prix));
	            }
	           
	        if (criteres.isEmpty()) {
	            System.out.println("Veuillez entrer au moins un critère.");
	            return;
	        }
	        InterCritere interCritere = new InterCritere(criteres);
	        List<Voiture> resultatsRecherche = agence.selectionneTous(interCritere);

	        if (resultatsRecherche.isEmpty()) {
	            System.out.println("Aucune voiture ne correspond aux critères de recherche.");
	        } else {
	            System.out.println("Résultats de la recherche :");
	            agence.afficheVoitures(resultatsRecherche);
	        }
	    }
	 /*
	  public void EcrireClient() 
	  {
		    try (BufferedWriter writer = new BufferedWriter(new FileWriter("clients.txt"))) 
		    {
		        int nombreChambresCategorie = 0;
		        for (Client cli : clients) {
		            writer.write(cli.toString());
		            writer.newLine(); 
		        }

		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}
	    public void EcrireLocation() {
	        try (BufferedWriter writer = new BufferedWriter(new FileWriter("locations.txt"))) {
	            for (Map.Entry<Client, Voiture> entry : locations.entrySet()) {
	                Client client = entry.getKey();
	                Voiture voiture = entry.getValue();

	                // Écrivez les informations dans le fichier
	                writer.write(client.toString() + " - " + voiture.toString());
	                writer.newLine();
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	  public void EcrireVoiture() 
	  {
		    try (BufferedWriter writer = new BufferedWriter(new FileWriter("voitures.txt"))) 
		    {
		        int nombreChambresCategorie = 0;
		        for (Voiture voi : voitures) {
		            writer.write(voi.toString());
		            writer.newLine(); 
		        }

		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}
	  public void lireClientsDepuisFichier(String cheminFichier) {
		    File file = new File(cheminFichier);

		    if (!file.exists()) {
		        System.out.println("Le fichier " + cheminFichier + " n'existe pas.");
		        return;
		    }

		    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
		        String ligne;
		        while ((ligne = reader.readLine()) != null) {
		            String[] informationsClient = ligne.split(" ");
		           if(informationsClient.length == 4) {
		             
		                String nom = informationsClient[0];
		                String prenom = informationsClient[1];
		                String codeCarte = informationsClient[2];
		                String civilite = informationsClient[3];        
		                Client client = new Client(nom, prenom, codeCarte, civilite);
		                System.out.println(nom  +" " + codeCarte);
		                clients.add(client);
		           }
		        }
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}


	    public void lireVoituresDepuisFichier(String cheminFichier) {
	        try (BufferedReader reader = new BufferedReader(new FileReader(cheminFichier))) {
	            String ligne;
	            while ((ligne = reader.readLine()) != null) {
	                String[] informationsVoiture = ligne.split(" "); 
	                Voiture voiture = new Voiture(informationsVoiture[0], informationsVoiture[1], Integer.parseInt(informationsVoiture[2]), Integer.parseInt(informationsVoiture[3]),informationsVoiture[4]);
	                voitures.add(voiture);
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    public void lireLocationsDepuisFichier(String cheminFichier) {
	        try (BufferedReader reader = new BufferedReader(new FileReader(cheminFichier))) {
	            String ligne;
	            while ((ligne = reader.readLine()) != null) {
	                String[] informationsLocation = ligne.split("-");
	                Client client = trouverClient(informationsLocation[0].trim()); 
	                Voiture voiture = trouverVoiture(informationsLocation[1].trim());
	                if (client != null && voiture != null) {
	                    locations.put(client, voiture);
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    private Client trouverClient(String informationsClient) {
	        String[] critereClient = informationsClient.split(" ");
	        for (Client client : clients) {
	            if (client.getCIN().equals(critereClient[2])) 
	            {
	                return client;
	            }
	        }
	        return null;
	    }
	    private Voiture trouverVoiture(String informationsVoiture) {
	    	  String[] critereVoiture = informationsVoiture.split(" ");
	        for (Voiture voiture : voitures) {
	        	
	            if (voiture.getNom().equals(critereVoiture[1]))  {
	                return voiture;
	            }
	        }
	        return null;
	    }*/
	 public  int compterNombreDeLignes(String cheminFichier) {
	        int nombreDeLignes = 0;

	        try (BufferedReader reader = new BufferedReader(new FileReader(cheminFichier))) {
	            while (reader.readLine() != null) {
	           
	                nombreDeLignes++;
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        return nombreDeLignes;
	    }
}