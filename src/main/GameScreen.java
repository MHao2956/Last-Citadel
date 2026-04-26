package main;

import javax.swing.JPanel;

import inputs.KeyboardListener;
import inputs.MyMouseListener;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class GameScreen extends JPanel {
    private Random random;
    private BufferedImage img;

    private Dimension size;

    private ArrayList<BufferedImage> sprite = new ArrayList<>();
    private KeyboardListener keyboardListener;

    public GameScreen(Game game){
        this.img = img;

        setPanelSize();

    }

    public void initInputs(){
        myMouseListener = new MyMouseListener(game);
        keyboardListener = new KeyboardListener();

        addMouseListener(myMouseListener);
        addMouseMotionListener(myMouseListener);
        addKeyListener(keyboardListener);

        requestFocus();
    }

    private void setPanelSize() {
        size = new Dimension(640,740);
        setMinimumSize(size);
        setPreferredSize(size);
        setMinimumSize(size);
    }

    // private void loadSprite() {
    //     for (int y = 0; y < 3; y++){
    //         for (int x = 0; x < 10; x++){
    //             sprite.add(img.getSubimage(x * 32, y * 32, 32, 32));

    //         }
    //     }
    // }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        game.getRender().render(g);
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
