package form;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Form_6 extends JPanel {
    private JLabel titleLabel;
    private JTable clientTable;
    private DefaultTableModel tableModel;

    public Form_6() {
        initComponents();
    }

    private void initComponents() {
        setBackground(new Color(242, 242, 242));

        titleLabel = new JLabel();
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        titleLabel.setForeground(new Color(106, 106, 106));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("Informations Client");

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Civilité");
        tableModel.addColumn("Prénom");
        tableModel.addColumn("Nom");
        tableModel.addColumn("CIN");

        clientTable = new JTable(tableModel);
        clientTable.setFont(new Font("SansSerif", Font.PLAIN, 16));
        clientTable.setBackground(Color.WHITE);
        clientTable.setSelectionBackground(new Color(0, 102, 204));
        clientTable.setSelectionForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(clientTable);

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(titleLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(titleLabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                                .addContainerGap())
        );

        loadClientsToTable(); // Chargez tous les clients dans le tableau
    }

    private void loadClientsToTable() {
        try (BufferedReader br = new BufferedReader(new FileReader("clients.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 4) {
                    Object[] rowData = {parts[3], parts[1], parts[0], parts[2]};
                    tableModel.addRow(rowData);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void updateClientTable() {
        
        tableModel.setRowCount(0);
      
        loadClientsToTable();
    }
}