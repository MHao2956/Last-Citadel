package core;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class GameScreen extends JPanel {
    private Random random;
    private BufferedImage img;
    private ArrayList<BufferedImage> sprite = new ArrayList<>();

    public GameScreen(BufferedImage img){
        this.img = img;
        loadSprite();

        random = new Random();
    }

    private void loadSprite() {
        
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(img.getSubimage(0, 32, 32, 32), 0, 0, null);

//        for (int y = 0; y < 20; y++){
//            for (int x = 0; x < 20; x++){
//                g.setColor(getRndColor());
//                g.fillRect(x * 32, y * 32 , 32, 32);
//            }
//        }
    }

    public Color getRndColor(){
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        return new Color (r, g, b);
    }
}
