package form;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Form_1 extends JPanel {
    private JTextField marqueField;
    private JTextField nomField;
    private JTextField anneeField;
    private JTextField prixField;
    private JTextField imageField;
    private JButton addButton;

    public Form_1() {
        initComponents();
    }

    private void initComponents() {
        setBackground(new Color(255, 255, 255)); // Set background color

        JLabel titleLabel = new JLabel("Ajouter une Voiture");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setForeground(new Color(70, 130, 180)); // Set title color

        JLabel marqueLabel = new JLabel("Marque:");
        JLabel nomLabel = new JLabel("Nom Voiture:");
        JLabel anneeLabel = new JLabel("Annee de Production:");
        JLabel prixLabel = new JLabel("Prix:");
        JLabel imageLabel = new JLabel("Image:");

        marqueField = new JTextField();
        nomField = new JTextField();
        anneeField = new JTextField();
        prixField = new JTextField();
        imageField = new JTextField();
        addButton = new JButton("Ajouter Voiture");
        addButton.setBackground(new Color(70, 130, 180)); // Set button background color
        addButton.setForeground(Color.WHITE); // Set button text color

        imageField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                browseImage(imageField);
            }
        });
        
    


        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCarToFile();
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
                        .addComponent(prixLabel)
                        .addComponent(prixField)
                        .addComponent(imageLabel)
                        .addComponent(imageField)
                        .addComponent(addButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(prixLabel)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(prixField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(imageLabel)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(imageField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(addButton)
        ));

        setLayout(new BorderLayout());
        add(formPanel, BorderLayout.CENTER);
    }

    private void browseImage(JTextField imageField) {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Image Files", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(filter);
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            imageField.setText(fileChooser.getSelectedFile().getPath());
        }
    }

    private void addCarToFile() {
        String marque = marqueField.getText();
        String nom = nomField.getText();
        String annee = anneeField.getText();
        String prix = prixField.getText();
        String image = imageField.getText();

       
        if (marque.isEmpty() || nom.isEmpty() || annee.isEmpty() || prix.isEmpty() || image.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!annee.matches("\\d{4}")) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer une année valide (format : YYYY).", "Erreur", JOptionPane.ERROR_MESSAGE);
            return; 
        }

        try {
          
            double prixValue = Double.parseDouble(prix);
            if (prixValue <= 0) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer un prix valide (nombre positif).", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            image = image.replace("\\", "\\\\");

            FileWriter writer = new FileWriter("voitures.txt", true);
            writer.write(marque + " " + nom + " " + annee + " " + prix + " " + image + "\n");
            writer.close();

            JOptionPane.showMessageDialog(this, "Voiture ajoutée avec succès !");
        } catch (NumberFormatException ex) {
            
            JOptionPane.showMessageDialog(this, "Veuillez entrer un prix valide (nombre).", "Erreur", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
     
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Une erreur est survenue lors de l'ajout de la voiture.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }


}
