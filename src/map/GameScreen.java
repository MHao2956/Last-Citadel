package map;

import java.awt.Color;
import javax.swing.JPanel;
import java.awt.Graphics;

public class GameScreen extends JPanel {
    public GameScreen(){

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.setColor(Color.RED);
        g.fillRect(50, 50, 100, 100);
        g.fillRect(150, 50, 100, 100);
        g.fillRect(100, 150, 100, 200);
        
    }
}
