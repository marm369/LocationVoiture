package swing;
import model.Model_Menu;
import java.awt.*;
import javax.swing.*;

public class MenuItem extends JPanel{
    private JLabel lbIcon;
    private JLabel lbName;
	private boolean selected;
	private boolean over;

	    public MenuItem(Model_Menu data) {
	        initComponents();
	        setOpaque(false);
	        if (data.getType() == Model_Menu.MenuType.MENU) {
	            lbIcon.setIcon(data.toIcon());
	            lbName.setText(data.getName());
	        } else if (data.getType() == Model_Menu.MenuType.TITLE) {
	            lbIcon.setText(data.getName());
	            lbIcon.setFont(new Font("sansserif", 1, 20));
	            lbName.setVisible(false);
	        } else {
	            lbName.setText(" ");
	        }
	    }
	    public void setSelected(boolean selected) {
	        this.selected = selected;
	        repaint();
	    }
	    public void setOver(boolean over) {
	        this.over = over;
	        repaint();
	    }
	  
	    private void initComponents() {

	        lbIcon = new JLabel();
	        lbName = new JLabel();

	        lbIcon.setForeground(new Color(255, 255, 255));
	        lbName.setForeground(new Color(255, 255, 255));
	        lbName.setText("Menu Name");

	        GroupLayout layout = new GroupLayout(this);
	        this.setLayout(layout);
	        layout.setHorizontalGroup(
	            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addGap(40, 40, 40)
	                .addComponent(lbIcon)
	                .addGap(18, 18, 18)
	                .addComponent(lbName)
	                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	        );
	        layout.setVerticalGroup(
	            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	            .addComponent(lbIcon, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	            .addComponent(lbName, GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
	        );
	    }

	    @Override
	    protected void paintComponent(Graphics grphcs) {
	        if (selected || over) {
	            Graphics2D g2 = (Graphics2D) grphcs;
	            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	            if (selected) {
	                g2.setColor(new Color(255, 255, 255, 80));
	            } else {
	                g2.setColor(new Color(255, 255, 255, 20));
	            }
	            g2.fillRoundRect(10, 0, getWidth() - 20, getHeight(), 5, 5);
	        }
	        super.paintComponent(grphcs);
	    }

	
	 

}
