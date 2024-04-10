
package form;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class Form_4 extends JPanel {
    private JTable table;
    private DefaultTableModel model;

    public Form_4() {
        initComponents();
    }

    public void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        Vector<String> columnNames = new Vector<>();
        columnNames.add("Marque");
        columnNames.add("Nom");
        columnNames.add("Annee");
        columnNames.add("Nom Complet");
        columnNames.add("CIN");
        columnNames.add("Image");

        Vector<Vector<Object>> data = new Vector<>();

        try {
            File locationsFile = new File("locations.txt");
            BufferedReader locationsReader = new BufferedReader(new FileReader(locationsFile));
            String locationsLine;

            while ((locationsLine = locationsReader.readLine()) != null) {
                String[] parts = locationsLine.split("-");
                if (parts.length == 2) {
                    String loueurInfo = parts[0].trim();
                    String[] loueurParts = loueurInfo.split(" ");
                  
                    if (loueurParts.length >= 4) {
                        String nomComplet = loueurParts[3] + " " + loueurParts[0] + " " + loueurParts[1];
                        String cin = loueurParts[2];
                        String voitureInfo = parts[1].trim();
                        String[] voitureParts = voitureInfo.split(" ");
                        if (voitureParts.length >= 4) {
                            Vector<Object> row = new Vector<>();
                            String marque = voitureParts[0].trim();
                            String nom = voitureParts[1].trim();
                            String annee = voitureParts[2].trim();

                            row.add(marque);
                            row.add(nom);
                            row.add(annee);
                            row.add(nomComplet);
                            row.add(cin);

                            String imagePath = findImagePath(marque, nom, annee);
                            if (!imagePath.isEmpty()) {
                                row.add(new ImageIcon(imagePath));
                            } else {
                                row.add(null);
                            }

                            data.add(row);
                        }
                    }
                }
            }

            locationsReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        model = new DefaultTableModel(data, columnNames) {
            @Override
            public Class<?> getColumnClass(int column) {
                switch (column) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        return String.class;
                    case 5:
                        return ImageIcon.class;
                    default:
                        return Object.class;
                }
            }
        };

        table = new JTable(model);
        table.setRowHeight(100);
      
        table.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
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
        
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    public void updateTable() {
        model.setRowCount(0); 

        try {
            File locationsFile = new File("locations.txt");
            BufferedReader locationsReader = new BufferedReader(new FileReader(locationsFile));
            String locationsLine;

            while ((locationsLine = locationsReader.readLine()) != null) {
                String[] parts = locationsLine.split("-");
                if (parts.length == 2) {
                    String loueurInfo = parts[0].trim();
                    String[] loueurParts = loueurInfo.split(" ");
                  
                    if (loueurParts.length >= 4) {
                        String nomComplet = loueurParts[3] + " " + loueurParts[0] + " " + loueurParts[1];
                        String cin = loueurParts[2];
                        String voitureInfo = parts[1].trim();
                        String[] voitureParts = voitureInfo.split(" ");
                        if (voitureParts.length >= 4) {
                            Vector<Object> row = new Vector<>();
                            String marque = voitureParts[0].trim();
                            String nom = voitureParts[1].trim();
                            String annee = voitureParts[2].trim();

                            row.add(marque);
                            row.add(nom);
                            row.add(annee);
                            row.add(nomComplet);
                            row.add(cin);

                            String imagePath = findImagePath(marque, nom, annee);
                            if (!imagePath.isEmpty()) {
                                row.add(new ImageIcon(imagePath));
                            } else {
                                row.add(null);
                            }

                            model.addRow(row);
                        }
                    }
                }
            }

            locationsReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String findImagePath(String marque, String nom, String annee) throws IOException {
        File voituresFile = new File("voitures.txt");
        BufferedReader voituresReader = new BufferedReader(new FileReader(voituresFile));
        String voituresLine;
        
        while ((voituresLine = voituresReader.readLine()) != null) {
            if (voituresLine.startsWith(marque + " " + nom + " " + annee)) {
                String[] parts = voituresLine.split(" ");
                voituresReader.close();
                return parts[parts.length - 1].trim(); 
            }
        }

        voituresReader.close();
        return ""; 
    }
}
