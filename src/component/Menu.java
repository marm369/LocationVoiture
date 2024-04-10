package component;
import java.lang.*;
import java.util.ArrayList;
import java.util.List;
import event.EventMenuSelected;
import model.Model_Menu;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import swing.ListMenu;


public class Menu extends JPanel {

    private EventMenuSelected event;
    private JLabel jLabel1;
    private ListMenu<String> listMenu1;
    private JPanel panelMoving;
    private int x;
    private int y;
    public void addEventMenuSelected(EventMenuSelected event) {
        this.event = event;
        listMenu1.addEventMenuSelected(event);
    }

    public Menu() {
        initComponents();
        setOpaque(false);
        listMenu1.setOpaque(false);
        init();
    }
 


    private void init() {
        listMenu1.addItem(new Model_Menu("0", "Dashboard", Model_Menu.MenuType.MENU));
        
        listMenu1.addItem(new Model_Menu("", "Gestion des Voitures", Model_Menu.MenuType.TITLE));
        
        listMenu1.addItem(new Model_Menu("2", "Ajouter une voiture", Model_Menu.MenuType.MENU));
        listMenu1.addItem(new Model_Menu("3", "Supprimer une voiture", Model_Menu.MenuType.MENU));
        listMenu1.addItem(new Model_Menu("4", "Modifier une voiture", Model_Menu.MenuType.MENU));
        listMenu1.addItem(new Model_Menu("5", "Afficher les voitures louées", Model_Menu.MenuType.MENU));
        
        listMenu1.addItem(new Model_Menu("", "Gestion des Clients", Model_Menu.MenuType.TITLE));
        
        listMenu1.addItem(new Model_Menu("7", "Afficher les loueurs", Model_Menu.MenuType.MENU));
        listMenu1.addItem(new Model_Menu("8", "Afficher les clients", Model_Menu.MenuType.MENU));
        listMenu1.addItem(new Model_Menu("9", "Un client est loueur?", Model_Menu.MenuType.MENU));
        listMenu1.addItem(new Model_Menu("10", "Supprimer un client", Model_Menu.MenuType.MENU));
      
        listMenu1.addItem(new Model_Menu("", "Gestion de Location", Model_Menu.MenuType.TITLE));
        
        listMenu1.addItem(new Model_Menu("12", "Louer une voiture", Model_Menu.MenuType.MENU));
        listMenu1.addItem(new Model_Menu("13", "Rendre une voiture", Model_Menu.MenuType.MENU));
       
      
 
        listMenu1.addItem(new Model_Menu("14", "Quitter", Model_Menu.MenuType.MENU));
    
    }

    @SuppressWarnings("unchecked")
    private void initComponents() 
    {

        panelMoving = new JPanel();
        jLabel1 = new JLabel();
        listMenu1 = new ListMenu<>();

        panelMoving.setOpaque(false);

        jLabel1.setFont(new Font("sansserif", 1, 18));
        jLabel1.setForeground(new Color(255, 255, 255));
        jLabel1.setIcon(new ImageIcon(getClass().getResource("/icon/logo.png"))); 
        jLabel1.setText("Application de Location");

        javax.swing.GroupLayout panelMovingLayout = new GroupLayout(panelMoving);
        panelMoving.setLayout(panelMovingLayout);
        panelMovingLayout.setHorizontalGroup(
            panelMovingLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panelMovingLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelMovingLayout.setVerticalGroup(
            panelMovingLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, panelMovingLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addContainerGap())
        );

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(panelMoving, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(listMenu1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelMoving, GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(listMenu1, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE))
        );
    }

    @Override
    protected void paintChildren(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint g = new GradientPaint(0, 0, Color.decode("#1CB5E0"), 0, getHeight(), Color.decode("#000046"));
        g2.setPaint(g);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        g2.fillRect(getWidth() - 20, 0, getWidth(), getHeight());
        super.paintChildren(grphcs);
    }


    public void initMoving(JFrame fram) {
        panelMoving.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                x = me.getX();
                y = me.getY();
            }
        });
        panelMoving.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent me) {
                fram.setLocation(me.getXOnScreen() - x, me.getYOnScreen() - y);
            }
        });
    }

}
