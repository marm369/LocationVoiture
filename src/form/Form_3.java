package form;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.io.*;

public class Form_3 extends JPanel {
    private JTextField marqueField;
    private JTextField nomField;
    private JTextField anneeField;
    private JTextField nouveauPrixField;
    private JButton modifierButton;

    public Form_3() {
        initComponents();
    }

    private void initComponents() {
        setBackground(new Color(255, 255, 255)); // Set background color

        JLabel titleLabel = new JLabel("Modifier Prix Voiture");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setForeground(new Color(70, 130, 180)); // Set title color

        JLabel marqueLabel = new JLabel("Marque:");
        JLabel nomLabel = new JLabel("Nom Voiture:");
        JLabel anneeLabel = new JLabel("Annee de Production:");
        JLabel nouveauPrixLabel = new JLabel("Nouveau Prix:");

        marqueField = new JTextField();
        nomField = new JTextField();
        anneeField = new JTextField();
        nouveauPrixField = new JTextField();
        modifierButton = new JButton("Modifier Prix");
        modifierButton.setBackground(new Color(70, 130, 180)); // Set button background color
        modifierButton.setForeground(Color.WHITE); // Set button text color

        modifierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifierPrixVoiture();
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
                        .addComponent(nouveauPrixLabel)
                        .addComponent(nouveauPrixField)
                        .addComponent(modifierButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(nouveauPrixLabel)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(nouveauPrixField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(modifierButton)
        ));

        setLayout(new BorderLayout());
        add(formPanel, BorderLayout.CENTER);
    }

    private void modifierPrixVoiture() {
        String marque = marqueField.getText();
        String nom = nomField.getText();
        String annee = anneeField.getText();
        String voitureAModifier = marque + " " + nom + " " + annee;
        String nouveauPrixStr = nouveauPrixField.getText();
        if (marque.isEmpty() || nom.isEmpty() || annee.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return; 
        }

        if (!annee.matches("\\d{4}")) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer une année valide (format : YYYY).", "Erreur", JOptionPane.ERROR_MESSAGE);
            return; 
        }
        try {
            
            double prixValue = Double.parseDouble(nouveauPrixStr);
            if (prixValue <= 0) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer un prix valide (nombre positif).", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }catch (NumberFormatException ex){
            
            JOptionPane.showMessageDialog(this, "Veuillez entrer un prix valide (nombre).", "Erreur", JOptionPane.ERROR_MESSAGE);
        } 
        try {
            File inputFile = new File("voitures.txt");
            File tempFile = new File("temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;
            boolean carFound = false;

            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.trim().startsWith(voitureAModifier)) {
                    carFound = true;
                    String[] parts = currentLine.split(" ");
                    parts[parts.length - 2] = nouveauPrixStr; // Modifier le prix
                    currentLine = String.join(" ", parts);
                }
                writer.write(currentLine + System.getProperty("line.separator"));
            }

            writer.close();
            reader.close();

            if (!carFound) {
                JOptionPane.showMessageDialog(this, "La Voiture n'existe pas");
                return;
            }

            if (!inputFile.delete()) {
                JOptionPane.showMessageDialog(this, "Impossible de supprimer le fichier original.");
                return;
            }

            if (!tempFile.renameTo(inputFile)) {
                JOptionPane.showMessageDialog(this, "Impossible de renommer le fichier temporaire.");
            } else {
                JOptionPane.showMessageDialog(this, "Prix de la voiture modifié avec succès !");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Une erreur est survenue lors de la modification du prix de la voiture.", "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
