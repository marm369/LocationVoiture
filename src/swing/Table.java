package swing;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Table extends JTable {

    public Table() {
        setShowHorizontalLines(true);
        setGridColor(new Color(230, 230, 230));
        setRowHeight(80);
        getTableHeader().setReorderingAllowed(false);
        // Initialisation du modèle de tableau avec des colonnes vides
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Marque", "Modèle", "Année", "Prix", "Image"}, 0);
        setModel(model);
    }

    public void addRow(Object[] row) {
        DefaultTableModel model = (DefaultTableModel) getModel();
        model.addRow(row);

        if (getColumnModel().getColumnCount() > 4) {
            getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    JLabel label = new JLabel();
                    if (value != null) {
                        if (value instanceof String) {
                            String imagePath = (String) value;
                            ImageIcon icon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(170, 80, Image.SCALE_SMOOTH));
                            label.setIcon(icon);
                        } else if (value instanceof ImageIcon) {
                            label.setIcon((ImageIcon) value);
                        } else {
                            System.out.println("Type d'image non pris en charge : " + value.getClass().getName());
                        }
                    } else {
                        System.out.println("Impossible de lire l'image : " + value);
                    }
                    return label;
                }
            });
        }
    }

}
