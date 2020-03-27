import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import javax.swing.plaf.ColorUIResource;

public class TableComponent extends JComponent{  
    //green oval for table
   public void paintComponent(Graphics g){  

       Graphics2D g2 = (Graphics2D) g;
       g2.setColor(new ColorUIResource(0, 170, 0));
       g2.fillOval(0,0,1300,650);
      
   }
}