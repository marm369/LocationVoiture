package form;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class Form_10 extends JPanel {
    private JLabel titleLabel;
    private JTextField cinField;
    private JButton deleteButton;

    public Form_10() {
        initComponents();
    }

    private void initComponents() {
        setBackground(new Color(242, 242, 242));

        titleLabel = new JLabel("Supprimer un Client");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setForeground(new Color(50, 50, 50));

        cinField = new JTextField();
        cinField.setFont(new Font("Arial", Font.PLAIN, 16));
        cinField.setColumns(15);

        deleteButton = new JButton("Supprimer");
        deleteButton.setFont(new Font("Arial", Font.BOLD, 18));
        deleteButton.setBackground(new Color(0, 51, 102));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.addActionListener(e -> deleteClient());

        // Mise en page avec GroupLayout
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(titleLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cinField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(deleteButton) // Bouton en bas
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGap(20)
                .addComponent(titleLabel)
                .addGap(20)
                .addComponent(cinField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(deleteButton) // Bouton en bas
                .addContainerGap(80, Short.MAX_VALUE)
        );
    }

    private void deleteClient() {
   
        String cin = cinField.getText().trim();

        if (!cin.isEmpty()) {
            
            if (isClientLoueur(cin)) {
                JOptionPane.showMessageDialog(this, "Impossible de supprimer un client loueur. Veuillez rendre la voiture louée.", "Erreur de suppression", JOptionPane.ERROR_MESSAGE);
                cinField.setText("");
            } else {
                
                if (isClientExiste(cin)) {
                  
                    int option = JOptionPane.showConfirmDialog(this, "Voulez-vous vraiment supprimer ce client ?", "Confirmation de suppression", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                       
                        removeClient(cin);
                        JOptionPane.showMessageDialog(this, "Le client a été supprimé avec succès.", "Suppression réussie", JOptionPane.INFORMATION_MESSAGE);
                        
                        cinField.setText("");
                    }
                } else {
               
                    JOptionPane.showMessageDialog(this, "Ce client n'existe pas dans l'agence.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private boolean isClientLoueur(String cin) {
        // Lire le fichier locations.txt et vérifier si le client est un loueur
        try (BufferedReader br = new BufferedReader(new FileReader("locations.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\s+");
                if (parts[2].equals(cin)) {
                    // Le client est un loueur
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Le client n'est pas un loueur
        return false;
    }

    private boolean isClientExiste(String cin) {
        System.out.println(cin);
        // Lire le fichier clients.txt et vérifier si le client existe
        try (BufferedReader br = new BufferedReader(new FileReader("clients.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Diviser la ligne en parties en utilisant l'espace comme séparateur
                String[] parts = line.split("\\s+");

                // Vérifier si la troisième partie (indice 2) correspond à cin
                if (parts[2].trim().equals(cin)) {
                    // Le client existe
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Le client n'existe pas
        return false;
    }


    private void removeClient(String cin) {
    
        // Supprimer le client du fichier locations.txt
        removeLineFromFile("clients.txt", cin);
    }

    private void removeLineFromFile(String fileName, String lineToRemove) {
        try {
            File inputFile = new File(fileName);
            File tempFile = new File("temp.txt");

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