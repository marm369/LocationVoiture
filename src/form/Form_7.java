package form;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class Form_7 extends JPanel {
    private JTextField cinField;
    private String voitureLouee;
    private String modeleVoiture;
    private String anneeVoiture;
    private String prixLocation;
    private String imagePath; 

    private JTable infoTable;
    private DefaultTableModel tableModel;
    private JLabel imageLabel;

    public Form_7() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel titleLabel = new JLabel("Rechercher Location");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        titleLabel.setForeground(new Color(70, 130, 180));
        topPanel.add(titleLabel);

        JLabel cinLabel = new JLabel("CIN :");
        cinLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));

        cinField = new JTextField();
        cinField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        cinField.setPreferredSize(new Dimension(150, 30));

        JButton searchButton = new JButton("Rechercher");
        searchButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        searchButton.addActionListener(e -> rechercherLocation());
        searchButton.setBackground(new Color(70, 130, 180));
        searchButton.setForeground(Color.WHITE);

        topPanel.add(cinLabel);
        topPanel.add(cinField);
        topPanel.add(searchButton);

        add(topPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel();
        infoTable = new JTable(tableModel);
        infoTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        infoTable.setRowHeight(25);
        infoTable.setGridColor(Color.BLACK);
        infoTable.setBackground(new Color(240, 240, 240));

        JScrollPane scrollPane = new JScrollPane(infoTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(200, 200));

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(imageLabel, BorderLayout.EAST);

        add(centerPanel, BorderLayout.CENTER);
    }

    private void rechercherLocation() {
        String cin = cinField.getText();
        System.out.println(cin);

        if (!cin.isEmpty()) {
            boolean isLoueur = checkLocation(cin);

            if (isLoueur) {
                // Le client loue actuellement une voiture
                afficherInformationsVoiture();
            } else {
                JOptionPane.showMessageDialog(this, "Le client ne loue pas actuellement de voiture.", "Location non trouvée", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez saisir le CIN du client.", "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void afficherInformationsVoiture() {
        String[] columnNames = {"Champ", "Valeur"};
        String[] rowData1 = {"Voiture", voitureLouee};
        String[] rowData2 = {"Modèle", modeleVoiture};
        String[] rowData3 = {"Année", anneeVoiture};
        String[] rowData4 = {"Prix de location", prixLocation};

        Object[][] data = {rowData1, rowData2, rowData3, rowData4};

        tableModel.setDataVector(data, columnNames);

        // Afficher l'image de la voiture
        afficherImageVoiture();
    }

    private void afficherImageVoiture() {
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image image = imageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(image);

        imageLabel.setIcon(imageIcon);
    }

    private boolean checkLocation(String cin) {
        try (BufferedReader br = new BufferedReader(new FileReader("locations.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split("-");
                String[] voitureDetails = parts[1].split(" ");
                String[] clientDetails = parts[0].split(" ");
         
                if (clientDetails[2].equals(cin)) {
                    // Le client loue actuellement une voiture
                    System.out.println("Client trouvé dans le fichier locations.txt : " + line);

         
                    voitureLouee = voitureDetails[0];
                    modeleVoiture = voitureDetails[1];
                    anneeVoiture = voitureDetails[2];
                    prixLocation = voitureDetails[3];
                

                    return true;
                }
            }
            System.out.println("Client non trouvé dans le fichier locations.txt.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Le client ne loue pas actuellement de voiture
        return false;
    }

   
}