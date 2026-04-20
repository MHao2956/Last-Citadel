package main;

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
        for (int y = 0; y < 3; y++){
            for (int x = 0; x < 10; x++){
                sprite.add(img.getSubimage(x * 32, y * 32, 32, 32));

            }
        }
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        //g.drawImage(sprite.get(10), 0, 0, null);
        //g.drawImage(img.getSubimage(0, 32, 32, 32), 0, 0, null);

        for (int y = 0; y < 20; y++){
            for (int x = 0; x < 20; x++){
                g.drawImage(sprite.get(getRndNumber()), x * 32, y * 32, null);
            }
        }
    }
    private int getRndNumber(){
        return random.nextInt(30);
    }

    public Color getRndColor(){
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        return new Color (r, g, b);
    }
}
