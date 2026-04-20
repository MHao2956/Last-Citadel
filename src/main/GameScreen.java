package main;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;

public class GameScreen extends JPanel {
    public GameScreen(){

    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.BLUE);
        g.drawRect(50, 50, 50, 50);
        g.fillRect(50, 50, 50 , 50);
    }
}
