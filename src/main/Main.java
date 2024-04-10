package main;

import event.EventMenuSelected;
import component.Menu;
import form.*;
import form.Form_Home;
import java.awt.*;
import javax.swing.*;
import swing.*;

public class Main extends JFrame {
    private Form_Home home;
    private Form_1 form1;
    private Form_2 form2;
    private Form_3 form3;
    private Form_4 form4;
    private Form_5 form5;
    private Form_6 form6;
    private Form_7 form7;
    private Form_8 form8;
    private Form_9 form9;
    private Form_10 form10;
    private JPanel mainPanel;
    private Menu menu;
    private PanelBorder panelBorder1;

    public Main() {
        initComponents();
        setBackground(new Color(34, 100, 0, 0));
        home = new Form_Home();
        form1 = new Form_1();
        form2 = new Form_2();
        form3 = new Form_3();
        form4 = new Form_4();
        form5 = new Form_5();
        form6 = new Form_6();
        form7 = new Form_7();
        form8 = new Form_8();
        form9 = new Form_9();
        form10 = new Form_10();

        menu.initMoving(Main.this);
        menu.addEventMenuSelected(new EventMenuSelected() {
            @Override
            public void selected(int index) {
            	if (index == 0) {
            	    home.loadTableData();
            	    setForm(home);
            	} else if (index == 2) {
            	    setForm(form1);
            	} else if (index == 3) {
            	    setForm(form2);
            	} else if (index == 4) {
            	    setForm(form3);
            	} else if (index == 5) {
            		
            		form4.updateTable();
            	    setForm(form4);
            	} else if (index == 7) {
            		form5.updateClients();
            	    setForm(form5);
            	} else if (index == 8) {
            		form6.updateClientTable();
            	    setForm(form6);
            	} else if (index == 9) {
            	    setForm(form7);
            	} 
            	else if (index == 10)
            	{
            		setForm(form10);
            	}
            	else if (index == 12) {
            	    setForm(form8);
            	} else if (index == 13) {
            	    setForm(form9);
            	}
                else
                {
                	System.exit(0);
                }
            }
        });
       setForm(new Form_Home());
    }

    private void setForm(JComponent com) {
        mainPanel.removeAll();
        mainPanel.add(com);
        mainPanel.repaint();
        mainPanel.revalidate();//recalcule sa mise en page
    }

    private void initComponents() {
        panelBorder1 = new PanelBorder();
        menu = new Menu();
        mainPanel = new JPanel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        panelBorder1.setBackground(new Color(242, 242, 242));
      
      
        mainPanel.setOpaque(false);
        mainPanel.setLayout(new BorderLayout());
        
        GroupLayout panelBorder1Layout = new GroupLayout(panelBorder1);
      
        panelBorder1.setLayout(panelBorder1Layout);
        panelBorder1Layout.setHorizontalGroup(
            panelBorder1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addComponent(menu, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,Short.MAX_VALUE)
                .addGroup(panelBorder1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(panelBorder1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(mainPanel,GroupLayout.PREFERRED_SIZE, 900,Short.MAX_VALUE)
                        .addContainerGap())))
        );

        panelBorder1Layout.setVerticalGroup(
            panelBorder1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(menu, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainPanel, GroupLayout.PREFERRED_SIZE, 750, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(panelBorder1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(panelBorder1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pack();
        setLocationRelativeTo(null);
    }
    public static void main(String args[]) {
    	 UIManager.LookAndFeelInfo[] lookAndFeels = UIManager.getInstalledLookAndFeels();
         
         System.out.println("Look and Feels disponibles :");
         for (UIManager.LookAndFeelInfo info : lookAndFeels) {
             System.out.println(info.getName());
         }
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        EventQueue.invokeLater(() -> new Main().setVisible(true));
    }
}
