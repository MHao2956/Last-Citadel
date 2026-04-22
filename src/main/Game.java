package main;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Game extends JFrame {

    private final GameScreen GS;
    private BufferedImage img;
    private double timePerFrame;
    private long lastFrame;

    private long lastUpdate;
    private double timePerUpdate;

    private int updates;
    private long lastTimeUPS;


    public Game(){
        timePerFrame = 1000000000.0 / 120.0;
        timePerUpdate = 1000000000.0 / 60.0;
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

    private void loopGame(){
        while(true){
            if(System.nanoTime() - lastUpdate >= timePerUpdate){
                updateGame();

                callUPS();
            }
            if(System.nanoTime() - lastFrame >= timePerFrame){
                lastFrame = System.nanoTime();
                repaint();
            }else{
                // do nothing
            }

        }
    }

    private void callUPS() {
        if (System.currentTimeMillis() - lastTimeUPS >= 1000){
            System.out.println("UPS: " + updates);
            updates = 0;
            lastTimeUPS = System.currentTimeMillis();
        }
    }

    private void updateGame() {
        updates++;
        lastUpdate = System.nanoTime();
        //System.out.println("Game Updated!");
    }

    public static void main (String[] args){
        Game game = new Game();
        game.loopGame();
    }
}