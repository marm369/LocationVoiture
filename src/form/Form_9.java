package form;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class Form_9 extends JPanel {
    private JLabel titleLabel;
    private JButton returnButton;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField cinField;
    private JTextField licensePlateField;

    public Form_9() {
        initComponents();
    }

    private void initComponents() {
        setBackground(Color.decode("#DEEEF5")); 

        titleLabel = new JLabel();
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        titleLabel.setForeground(new Color(106, 106, 106));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("Rendre une Voiture");

        returnButton = new JButton("Rendre la Voiture");
        returnButton.setFont(new Font("SansSerif", Font.BOLD, 16)); // Modification de la taille du bouton
        returnButton.addActionListener(e -> rendreVoiture());

        firstNameField = new JTextField();
        lastNameField = new JTextField();
        cinField = new JTextField();
        licensePlateField = new JTextField();

        JLabel firstNameLabel = new JLabel("Prénom : ");
        JLabel lastNameLabel = new JLabel("Nom : ");
        JLabel cinLabel = new JLabel("CIN : ");
        JLabel licensePlateLabel = new JLabel("Nom de voiture : ");

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(titleLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(returnButton, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE) // Modification de la taille du bouton
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(firstNameLabel)
                                                        .addComponent(lastNameLabel)
                                                        .addComponent(cinLabel)
                                                        .addComponent(licensePlateLabel))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(firstNameField)
                                                        .addComponent(lastNameField)
                                                        .addComponent(cinField)
                                                        .addComponent(licensePlateField))))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(titleLabel)
                                .addGap(20, 20, 20)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(firstNameLabel)
                                        .addComponent(firstNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lastNameLabel)
                                        .addComponent(lastNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(cinLabel)
                                        .addComponent(cinField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(licensePlateLabel)
                                        .addComponent(licensePlateField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(20, 20, 20)
                                .addComponent(returnButton)
                                .addContainerGap(20, Short.MAX_VALUE))
        );
    }

    private void rendreVoiture() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String cin = cinField.getText().trim();
        String licensePlate = licensePlateField.getText().trim();

        try {
            if (!firstName.isEmpty() && !lastName.isEmpty() && !cin.isEmpty() && !licensePlate.isEmpty()) {
                // Vérifier si le client a loué une voiture
                String carInfo = getCarInfo(cin);
                System.out.println(carInfo);
                if (!carInfo.equals("Informations de la voiture non trouvées")) {
                    System.out.println("hana");
                    // Afficher les informations de la voiture dans une boîte de dialogue avec l'image
                    boolean success = showCarInfoDialog(carInfo, getImagePathFromCarInfo(carInfo));
                    if (success) {
                        // Réinitialiser les champs après avoir rendu la voiture
                        firstNameField.setText("");
                        lastNameField.setText("");
                        cinField.setText("");
                        licensePlateField.setText("");

                        // Supprimer la ligne correspondant au client dans le fichier locations.txt
                        removeClientFromLocations(cin);
                    }
                } else {
                    System.out.print("hahah");
                    JOptionPane.showMessageDialog(this, "Vous ne louez pas cette voiture.",
                            "Erreur de location", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                throw new Exception("Veuillez remplir tous les champs.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean showCarInfoDialog(String carInfo, String imagePath) {
        // Extraire les informations de la voiture
        String[] carInfoParts = carInfo.split(" ");
        String modelName = carInfoParts[0];
        String licensePlate = carInfoParts[1];
        String productionYear = carInfoParts[2];

        // Construire le texte de la boîte de dialogue
        String dialogText = "Est-ce que vous voulez rendre cette voiture :\n\n"
                + "Model de la voiture: " + modelName + "\n"
                + "Matricule de la voiture: " + licensePlate + "\n"
                + "Année de production: " + productionYear + "\n\n"
                + "Voulez-vous continuer ?";

        // Charger l'image de la voiture
        ImageIcon carImageIcon = new ImageIcon(imagePath);
        JLabel carImageLabel = new JLabel(carImageIcon);

        // Couleur d'arrière-plan pour la boîte de dialogue
        Color dialogBackgroundColor = Color.WHITE; // Rose pâle

        // Afficher la boîte de dialogue avec l'image
        int result = JOptionPane.showOptionDialog(
                this,
                new Object[]{dialogText, carImageLabel},
                "Rendre la Voiture",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new Object[]{"Oui", "Non"},
                "Oui"
        );

        // Renvoyer vrai si l'utilisateur clique sur "Oui", sinon faux
        return result == JOptionPane.YES_OPTION;
    }

    private String getImagePathFromCarInfo(String carInfo) {
        String[] parts = carInfo.split(" ");
        return parts[parts.length - 1];
    }

    private String getCarInfoFromModel(String modelName) {
        try (BufferedReader br = new BufferedReader(new FileReader("voitures.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts[1].equals(modelName)) {
                    StringBuilder carInfo = new StringBuilder();
                    for (int i = 0; i < parts.length - 1; i++) {
                        carInfo.append(parts[i]).append(" ");
                    }
                    carInfo.append(parts[parts.length - 1]);
                    return carInfo.toString();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Informations de la voiture non trouvées";
    }

    private String getCarInfoFromCIN(String cin) {
        try (BufferedReader br = new BufferedReader(new FileReader("locations.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\s+");
                if (parts[2].contains(cin)) {
                    String modelName = parts[4];
                    return getCarInfoFromModel(modelName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Informations de la voiture non trouvées";
    }

    private String getCarInfo(String cin) {
        return getCarInfoFromCIN(cin);
    }

    private void removeClientFromLocations(String lineToRemove) {
        try {
            File inputFile = new File("locations.txt");
            File tempFile = new File("Locationstemp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                String[] parts = currentLine.split("\\s+");

                // Vérifier si la troisième partie (indice 2) ne correspond pas à cin
                if (!parts[2].trim().equals(lineToRemove)) {
                    writer.write(currentLine + System.getProperty("line.separator"));
                }
            }

            writer.close();
            reader.close();

            // Vider complètement le fichier original
            PrintWriter pw = new PrintWriter(inputFile);
            pw.close();

            // Copier le contenu depuis le fichier temporaire vers le fichier original
            Files.copy(tempFile.toPath(), inputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}