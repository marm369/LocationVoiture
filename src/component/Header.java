
package component ;
import java.awt.*;
import javax.swing.*;


public class Header extends JPanel {
    public  JLabel marqueLabel;
    public JLabel anneeProdLabel;
    public JLabel prixMaxLabel;
    public JTextField marqueField;
    public JTextField anneeProdField;
    public JTextField prixMaxField;
    public JButton searchButton;
   
    public Header() {
        initComponents();
        setOpaque(false);
    }

    private void initComponents() {
  

        marqueLabel = new JLabel("Marque:");
        marqueLabel.setForeground(new Color(50, 50, 50));
        
        marqueField = new JTextField(2);
        marqueField.setToolTipText("Marque");
        marqueField.setBorder(BorderFactory.createLineBorder(new Color(0, 128, 255)));
        marqueField.setFont(new Font("Arial", Font.PLAIN, 14));
        marqueField.setBackground(new Color(255, 255, 255));
        marqueField.setForeground(new Color(50, 50, 50));

        anneeProdLabel = new JLabel("Année de Production:");
        anneeProdLabel.setForeground(new Color(50, 50, 50));

        anneeProdField = new JTextField(2);
        anneeProdField.setToolTipText("Année de Production");
        anneeProdField.setBorder(BorderFactory.createLineBorder(new Color(0, 128, 255)));
        anneeProdField.setFont(new Font("Arial", Font.PLAIN, 14));
        anneeProdField.setBackground(new Color(255, 255, 255));
        anneeProdField.setForeground(new Color(50, 50, 50));

        prixMaxLabel = new JLabel("Prix Max:");
        prixMaxLabel.setForeground(new Color(50, 50, 50));

        prixMaxField = new JTextField(2);
        prixMaxField.setToolTipText("Prix Max");
        prixMaxField.setBorder(BorderFactory.createLineBorder(new Color(0, 128, 255)));
        prixMaxField.setFont(new Font("Arial", Font.PLAIN, 14));
        prixMaxField.setBackground(new Color(255, 255, 255));
        prixMaxField.setForeground(new Color(50, 50, 50));

        searchButton = new JButton("Chercher");
        searchButton.setBackground(new Color(70, 130, 180));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFont(new Font("Arial", Font.BOLD, 14));

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
            .addComponent(marqueLabel)
            .addComponent(marqueField)
            .addComponent(anneeProdLabel)
            .addComponent(anneeProdField)
            .addComponent(prixMaxLabel)
            .addComponent(prixMaxField)
            .addComponent(searchButton)
        );

        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(marqueLabel)
            .addComponent(marqueField)
            .addComponent(anneeProdLabel)
            .addComponent(anneeProdField)
            .addComponent(prixMaxLabel)
            .addComponent(prixMaxField)
            .addComponent(searchButton)
        );

    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.decode("#DEEEF5"));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        g2.fillRect(0, 0, 25, getHeight());
        g2.fillRect(getWidth() - 25, getHeight() - 25, getWidth(), getHeight());
        super.paintComponent(grphcs);
    }

    public JButton getSearchButton() {
        return searchButton;
    }
}
