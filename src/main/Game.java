package main;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Game extends JFrame {

    private final GameScreen GS;
    private BufferedImage img;
    public Game(){
        importImg();
        setSize(640,640);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        GS = new GameScreen(img);
        add(GS);
        setVisible(true);
    }

    private void importImg() {
        InputStream is = getClass().getResourceAsStream("/spriteatlas.png");

        try {
            img = ImageIO.read(is);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main (String[] args){
        Game g = new Game();
    }
}