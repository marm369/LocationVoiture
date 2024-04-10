package form;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import agence.Voiture;
import component.Header;
import java.awt.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Form_8 extends JPanel {
	
    private Header header;
    private JTable resultTable;
    public List<Voiture> voitures_criteres;
 
    public Form_8() {
        initComponents();
    }

    private void initComponents() {
        voitures_criteres = new ArrayList<>();
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

       
        header = new Header();
        add(header, BorderLayout.NORTH);
        resultTable = new JTable();

        JScrollPane scrollPane = new JScrollPane(resultTable);
        add(scrollPane, BorderLayout.CENTER);

     

        header.searchButton.addActionListener(e -> {
          
            String marque = header.marqueField.getText().trim();
            String anneeProd = header.anneeProdField.getText().trim();
            String prixMax = header.prixMaxField.getText().trim();
            voitures_criteres.clear();
          
            if (!anneeProd.matches("\\d{4}") && !anneeProd.isEmpty() ) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer une année valide (format : YYYY).", "Erreur", JOptionPane.ERROR_MESSAGE);
                return; 
            }
            if(!prixMax.isEmpty())
            {
            try {
              
                double prixValue = Double.parseDouble(prixMax);
                if (prixValue <= 0) {
                    JOptionPane.showMessageDialog(this, "Veuillez entrer un prix valide (nombre positif).", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

             
            } catch (NumberFormatException ex) {
                
                JOptionPane.showMessageDialog(this, "Veuillez entrer un prix valide (nombre).", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
            }
 
            voitures_criteres = rechercherVoitures(marque, anneeProd, prixMax);
            for(Voiture v: voitures_criteres)
            {
            	System.out.println(v);
            }
            
            updateTable(voitures_criteres);
        });

     // Ajoute un écouteur de sélection de ligne pour détecter la sélection d'une ligne dans le tableau
        resultTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    // Récupère l'index de la ligne sélectionnée
                    int selectedRow = resultTable.getSelectedRow();
                    if (selectedRow != -1) {
                        // Récupérer les informations de la voiture à partir de la ligne sélectionnée
                        String marque = resultTable.getValueAt(selectedRow, 0).toString();
                        String nom = resultTable.getValueAt(selectedRow, 1).toString();
                        int annee = Integer.parseInt(resultTable.getValueAt(selectedRow, 2).toString());
                        int prix = Integer.parseInt(resultTable.getValueAt(selectedRow, 3).toString());
                        Voiture voiture = new Voiture(marque, nom, annee, prix, "");
                        
                        // Affiche une boîte de dialogue pour saisir les informations du client et enregistrer la location
                        showClientInfoDialog(voiture);
                    }
                }
            }
        });

    }

    private List<Voiture> rechercherVoitures(String marque, String anneeProd , String prixMax) {
        String line;
        List<Voiture> voituresLouees = new ArrayList<>();
        try {
            // Lire le fichier locations.txt et stocker les voitures louées
            BufferedReader reader = new BufferedReader(new FileReader("locations.txt"));
            while ((line = reader.readLine()) != null) {
                String[] details = line.split("-");
                if (details.length > 1) {
                    String[] voitureDetails = details[1].split(" ");
                    if (voitureDetails.length >= 3) {
                        Voiture voiture = new Voiture(voitureDetails[0], voitureDetails[1], Integer.parseInt(voitureDetails[2]), 0, "");
                        voituresLouees.add(voiture);
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader("voitures.txt"));
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(" ");
                Voiture voiture = new Voiture(details[0], details[1], Integer.parseInt(details[2]), Integer.parseInt(details[3]), details[4]);
                // Si la voiture est déjà louée, ignorer cette voiture
                boolean isLouee = false;
                for (Voiture voitureLouee : voituresLouees) {
                    if (voitureLouee.getMarque().equals(voiture.getMarque()) && voitureLouee.getNom().equals(voiture.getNom()) && voitureLouee.getAnneeProd() == voiture.getAnneeProd()) {
                        isLouee = true;
                        break;
                    }
                }
                if (isLouee) {
                    continue;
                }
                if (!marque.isEmpty() && !voiture.getMarque().equals(marque)) {
                    continue;
                }
                if (!anneeProd.isEmpty() && voiture.getAnneeProd() != Integer.parseInt(anneeProd)) {
                    continue;
                }
                if (!prixMax.isEmpty() && voiture.getPrix() > Integer.parseInt(prixMax)) {
                    continue;
                }
                
                voitures_criteres.add(voiture);
            }
          
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return voitures_criteres;
    }

    private void showClientInfoDialog(Voiture voiture) {
        JPanel panel = new JPanel(new GridLayout(5, 2));
        JTextField nomField = new JTextField();
        panel.add(new JLabel("Nom:"));
        panel.add(nomField);

        JTextField prenomField = new JTextField();
        panel.add(new JLabel("Prénom:"));
        panel.add(prenomField);

        JTextField cinField = new JTextField();
        panel.add(new JLabel("CIN:"));
        panel.add(cinField);

        String[] civilites = {"Mr", "Mme", "Mlle"};
        JComboBox civiliteBox = new JComboBox<>(civilites);
        panel.add(new JLabel("Civilité:"));
        panel.add(civiliteBox);

        int result = JOptionPane.showConfirmDialog(null, panel, "Saisie des informations du client",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String nom = nomField.getText();
            String prenom = prenomField.getText();
            String cin = cinField.getText();
            String civilite = (String) civiliteBox.getSelectedItem();

            String clientInfo = nom + " " + prenom + " " + cin + " " + civilite;

            // Vérifie si le client existe déjà dans le fichier clients.txt
            boolean isNewClient = true;
            try (BufferedReader reader = new BufferedReader(new FileReader("clients.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.contains(cin)) {
                        isNewClient = false;
                        break;
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            if (isNewClient) {
                // Si le client est nouveau, l'ajoute au fichier clients.txt
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("clients.txt", true))) {
                    writer.write(clientInfo);
                    writer.newLine();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            boolean hasRentedCar = false;
            try (BufferedReader reader = new BufferedReader(new FileReader("locations.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.contains(cin)) {
                        hasRentedCar = true;
                        break;
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }


            if (hasRentedCar) {
                JOptionPane.showMessageDialog(null, "Ce client a déjà loué une voiture.", "Erreur", JOptionPane.ERROR_MESSAGE);
            } else {
                // Enregistre la location dans le fichier locations.txt
                String locationInfo = nom + " " + prenom + " " + cin +" " +civilite+ "-" + voiture.getMarque() + " " + voiture.getNom() + " " + voiture.getAnneeProd() + " " + voiture.getPrix();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("locations.txt", true))) {
                    writer.write(locationInfo);
                    writer.newLine();
                    JOptionPane.showMessageDialog(null, "Location enregistrée avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }


    private void updateTable(List<Voiture> resultats) {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public Class<?> getColumnClass(int column) {
                switch (column) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                        return String.class;
                    case 4:
                        return ImageIcon.class;
                    case 5:
                        return JButton.class;
                    default:
                        return String.class;
                }
            }
        };
  
  
        model.addColumn("Marque");
        model.addColumn("Nom");
        model.addColumn("Année");
        model.addColumn("Prix");
        model.addColumn("Image");
        model.setRowCount(0);
        for (Voiture voiture : resultats) {
            Vector<Object> row = new Vector<>();
            row.add(voiture.getMarque());
            row.add(voiture.getNom());
            row.add(voiture.getAnneeProd());
            row.add(voiture.getPrix());
            row.add(new ImageIcon(voiture.getImage()));
            model.addRow(row);
        }

        resultTable.setModel(model);
        resultTable.setRowHeight(100); 
      
        resultTable.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (value instanceof ImageIcon) {
                    ImageIcon icon = (ImageIcon) value;
                    Image img = icon.getImage().getScaledInstance(150, 100, Image.SCALE_SMOOTH);
                    ImageIcon resizedIcon = new ImageIcon(img);
                    setIcon(resizedIcon);
                    return this;
                } else {
                    return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                }
            }
        });

  
    }
   
}

