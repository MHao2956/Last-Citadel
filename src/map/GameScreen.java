package map;

import java.awt.Color;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.util.Random;

public class GameScreen extends JPanel {
    private Random random;
    public GameScreen(){
        random = new Random();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        for(int y = 0; y < 20; y++){
            for(int x = 0; x < 20; x++){
                g.setColor(getRndColor());
                g.fillRect(x*32, y*32, 32, 32);
            }
        }

    }

    private Color getRndColor(){
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);

        return new Color(r, g, b);
    }
}
