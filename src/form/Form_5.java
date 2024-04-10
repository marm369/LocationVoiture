package form;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import agence.Client;

public class Form_5 extends JPanel {
    private JTable clientTable;
    private DefaultTableModel tableModel;

    public Form_5() {
        initComponents();
        loadClientsFromFile();
    }

    private void initComponents() {
        setBackground(new Color(240, 240, 240)); // Couleur de fond

        JLabel titleLabel = new JLabel("Liste des Clients loueurs");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        titleLabel.setForeground(new Color(0, 102, 204)); // Couleur du titre

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Civilité");
        tableModel.addColumn("Prénom");
        tableModel.addColumn("Nom");
        tableModel.addColumn("CIN");

        clientTable = new JTable(tableModel);
        clientTable.setFont(new Font("SansSerif", Font.PLAIN, 16));
        clientTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 18));
        clientTable.getTableHeader().setReorderingAllowed(false);

        // Couleurs du tableau
        clientTable.setBackground(Color.WHITE);
        clientTable.setForeground(new Color(0, 51, 102));
        clientTable.setSelectionBackground(new Color(0, 102, 204));
        clientTable.setSelectionForeground(Color.WHITE);

        // Couleurs des en-têtes de colonnes
        JTableHeader header = clientTable.getTableHeader();
        header.setBackground(new Color(0, 102, 204));
        header.setForeground(Color.WHITE);

        // Aligner les valeurs des colonnes au centre
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        clientTable.setDefaultRenderer(Object.class, centerRenderer);

        JScrollPane scrollPane = new JScrollPane(clientTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Supprimer la bordure par défaut

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(titleLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(40, 40, 40))
        );

        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(titleLabel)
                                .addGap(20, 20, 20)
                                .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(20, 20, 20))
        );
    }

    private void loadClientsFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("locations.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("-", 2); 
                if (parts.length == 2) {
                    String[] details = parts[0].split(" "); 
                    if (details.length >= 4) { 
                        String civilite = details[3];
                        String prenom = details[1];
                        String nom = details[0];
                        String CIN = details[2];

                        Object[] rowData = {civilite, prenom, nom, CIN};
                        tableModel.addRow(rowData);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void updateClients() {
       
        tableModel.setRowCount(0);

      
        loadClientsFromFile();
    }



  
}