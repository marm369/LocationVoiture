package form;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.io.*;

public class Form_2 extends JPanel {
    private JTextField marqueField;
    private JTextField nomField;
    private JTextField anneeField;
    private JButton deleteButton;

    public Form_2() {
        initComponents();
    }

    private void initComponents() {
        setBackground(new Color(255, 255, 255)); // Set background color

        JLabel titleLabel = new JLabel("Supprimer Voiture");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setForeground(new Color(70, 130, 180)); // Set title color

        JLabel marqueLabel = new JLabel("Marque:");
        JLabel nomLabel = new JLabel("Nom Voiture:");
        JLabel anneeLabel = new JLabel("Annee de Production:");

        marqueField = new JTextField();
        nomField = new JTextField();
        anneeField = new JTextField();
        deleteButton = new JButton("Supprimer Voiture");
        deleteButton.setBackground(new Color(70, 130, 180)); // Set button background color
        deleteButton.setForeground(Color.WHITE); // Set button text color

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteCar();
            }
        });

        JPanel formPanel = new JPanel();
        formPanel.setBorder(new LineBorder(new Color(70, 130, 180), 2)); // Add blue border
        formPanel.setBackground(new Color(255, 255, 255)); // Set formPanel background color

        GroupLayout layout = new GroupLayout(formPanel);
        formPanel.setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(40, 40, 40)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(titleLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(marqueLabel)
                        .addComponent(marqueField)
                        .addComponent(nomLabel)
                        .addComponent(nomField)
                        .addComponent(anneeLabel)
                        .addComponent(anneeField)
                        .addComponent(deleteButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGap(40, 40, 40))
        );

        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(20, 20, 20)
                    .addComponent(titleLabel)
                    .addGap(20, 20, 20)
                    .addComponent(marqueLabel)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(marqueField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(nomLabel)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(nomField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(anneeLabel)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(anneeField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(deleteButton)
        ));

        setLayout(new BorderLayout());
        add(formPanel, BorderLayout.CENTER);
    }

    private void deleteCar() {
        String marque = marqueField.getText();
        String nom = nomField.getText();
        String annee = anneeField.getText();
        String voitureToDelete = marque + " " + nom + " " + annee;
      
        if (marque.isEmpty() || nom.isEmpty() || annee.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return; 
        }

   
        if (!annee.matches("\\d{4}")) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer une année valide (format : YYYY).", "Erreur", JOptionPane.ERROR_MESSAGE);
            return; 
        }
        try {
            File inputFile = new File("voitures.txt");
            File tempFile = new File("temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;
            boolean found = false;
            int i =1;

            while ((currentLine = reader.readLine()) != null) {
                String trimmedLine = currentLine.trim();
                if (trimmedLine.startsWith(voitureToDelete)) {
                    found = true;
                    if (isCarRented(marque, nom, annee)) {
                        JOptionPane.showMessageDialog(this, "Cette voiture est louée et ne peut pas être supprimée.");
                        i=0;
                        writer.write(currentLine + System.getProperty("line.separator"));
                    }
                } else {
                    writer.write(currentLine + System.getProperty("line.separator"));
                }
            }

            writer.close();
            reader.close();

            if (!found) {
                JOptionPane.showMessageDialog(this, "Impossible de supprimer la voiture car elle n'existe pas.");
                return;
            }

            if (!inputFile.delete() ) {
                JOptionPane.showMessageDialog(this, "Impossible de supprimer le fichier original.");
                return;
            }

            if (!tempFile.renameTo(inputFile) && i!=0 ) {
                JOptionPane.showMessageDialog(this, "Impossible de renommer le fichier temporaire.");
            } else if ( i!=0 ){
                JOptionPane.showMessageDialog(this, "Voiture supprimée avec succès !");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Une erreur est survenue lors de la suppression de la voiture.", "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }



    private boolean isCarRented(String marque, String nom, String annee) {
        try {
            File inputFile = new File("locations.txt");
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            String currentLine;
            while((currentLine = reader.readLine()) != null) {
                String[] parts = currentLine.split("-");
                if(parts.length > 1) {
                    String carInfo = parts[1];
                    if(carInfo.contains(marque) && carInfo.contains(nom) && carInfo.contains(annee)) {
                        return true;
                    }
                }
            }
            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
