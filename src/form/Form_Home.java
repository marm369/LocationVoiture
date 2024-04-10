package form;

import model.Model_Card;
import swing.ScrollBar;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import agence.Agence;
import swing.*;
import component.*;

public class Form_Home extends JPanel {
	   private Card card1;
	    private Card card2;
	    private Card card3;
	    private JLabel jLabel1;
	    private JLayeredPane panel;
	    private PanelBorder panelBorder1;
	    private JScrollPane spTable;
	    private Table table;
	  

    public Form_Home() {
        initComponents();
    }

    public void initComponents() {

        panel = new JLayeredPane();
        card1 = new Card();
        card2 = new Card();
        card3 = new Card();
        panelBorder1 = new PanelBorder();
        jLabel1 = new JLabel();
        spTable = new JScrollPane();
        table = new Table();
      
        setBackground(new Color(242, 242, 242));

        panel.setLayout(new GridLayout(1, 0, 10, 0));

        card1.setColor1(new Color (235, 176, 22));
        card1.setColor2(new Color (254, 144, 99));
        panel.add(card1);

        card2.setColor1(new Color (247,159,149));
        card2.setColor2(new Color (234,88,99));
        panel.add(card2);

        card3.setColor1(new Color (183, 206, 102));
        card3.setColor2(new Color (33, 173, 186));
        panel.add(card3);

        panelBorder1.setBackground(new Color(255, 255, 255));

        jLabel1.setFont(new Font("sansserif", 1, 18)); 
        jLabel1.setForeground(new Color(127, 127, 127));
        jLabel1.setText("Galerie des Voitures");

        spTable.setBorder(null);

        table.setModel(new DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "Marque", "Nom", "Annee Production", "Prix", "Image"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        spTable.setViewportView(table);

        GroupLayout panelBorder1Layout = new GroupLayout(panelBorder1);
        panelBorder1.setLayout(panelBorder1Layout);
        panelBorder1Layout.setHorizontalGroup(
            panelBorder1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(panelBorder1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(panelBorder1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(spTable))
                .addContainerGap())
        );
        panelBorder1Layout.setVerticalGroup(
            panelBorder1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spTable, GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(panel, GroupLayout.DEFAULT_SIZE, 875, Short.MAX_VALUE)
                        .addComponent(panelBorder1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(panelBorder1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap())
        );
        Agence a = new Agence();
        int nb2= a.compterNombreDeLignes("C:\\Users\\minou\\eclipse-workspace\\Locations\\clients.txt");
       int nb1= a.compterNombreDeLignes("C:\\Users\\minou\\eclipse-workspace\\Locations\\voitures.txt");
        int nb3 = a.compterNombreDeLignes("C:\\Users\\minou\\eclipse-workspace\\Locations\\locations.txt");
 
        card1.setData(new Model_Card(new ImageIcon(getClass().getResource("/icon/car.png")), "Nombre de Voitures", Integer.toString(nb1), ""));
        card2.setData(new Model_Card(new ImageIcon(getClass().getResource("/icon/client.png")), "Nombre de Clients", Integer.toString(nb2), ""));
        card3.setData(new Model_Card(new ImageIcon(getClass().getResource("/icon/rent.png")), "Nombre de Voitures Louées", Integer.toString(nb3), ""));
   
        spTable.setVerticalScrollBar(new ScrollBar());
        spTable.getVerticalScrollBar().setBackground(Color.WHITE);
        spTable.getViewport().setBackground(Color.WHITE);
        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);
        spTable.setCorner(JScrollPane.UPPER_RIGHT_CORNER, p);
        try (BufferedReader br = new BufferedReader(new FileReader("voitures.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                String marque = parts[0];
                String modele = parts[1];
                String annee = parts[2];
                String prix = parts[3];
                String icon = parts[4];
                System.out.println(marque+" " +modele);
                table.addRow(new Object[]{marque,modele,annee,prix,icon});
                  
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void loadTableData() {
        Agence b = new Agence();
        int nb2= b.compterNombreDeLignes("C:\\Users\\minou\\eclipse-workspace\\Locations\\clients.txt");
       int nb1= b.compterNombreDeLignes("C:\\Users\\minou\\eclipse-workspace\\Locations\\voitures.txt");
        int nb3 = b.compterNombreDeLignes("C:\\Users\\minou\\eclipse-workspace\\Locations\\locations.txt");
 
        card1.setData(new Model_Card(new ImageIcon(getClass().getResource("/icon/car.png")), "Nombre de Voitures", Integer.toString(nb1), ""));
        card2.setData(new Model_Card(new ImageIcon(getClass().getResource("/icon/client.png")), "Nombre de Clients", Integer.toString(nb2), ""));
        card3.setData(new Model_Card(new ImageIcon(getClass().getResource("/icon/rent.png")), "Nombre de Voitures Louées", Integer.toString(nb3), ""));
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Effacer les données actuelles du tableau

        try (BufferedReader br = new BufferedReader(new FileReader("voitures.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                String marque = parts[0];
                String modele = parts[1];
                String annee = parts[2];
                String prix = parts[3];
                String icon = parts[4];
                model.addRow(new Object[]{marque, modele, annee, prix, icon});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
}
