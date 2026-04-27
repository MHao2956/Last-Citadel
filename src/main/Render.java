package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class Render {
    private Game game;

    public Render (Game game){
        this.game = game;


    }

    public void render(Graphics g){
        switch (GameStates.gameStates){

            case MENU:
                game.getMenu().render(g);

                break;
            case PLAYING:
                game.getPlaying().render(g);

                break;
            case SETTINGS:
                game.getSettings().render(g);

                break;
        }
    }


//    public Color getRndColor(){
//        int r = random.nextInt(256);
//        int g = random.nextInt(256);
//        int b = random.nextInt(256);
//        return new Color (r, g, b);
//    }
}
