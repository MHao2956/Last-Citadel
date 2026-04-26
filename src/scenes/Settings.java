package scenes;

import java.awt.Color;
import java.awt.Graphics;

import main.Game;

public class Settings extends GameScene implements SceneMethods {
    public Settings(Game game){
        super(game);
    }

    @Override
    public void render(Graphics g){
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, 640, 640);
    }

    @Override
    public void mouseClicked(int x, int y){

    }

    @Override
    public void mouseMoved(int x, int y){

    }

    @Override
    public void mousePressed(int x, int y){
        
    }

    @Override
    public void mouseReleased(int x, int y){
        
    }

    @Override
    public void mouseDragged(int x, int y){
        
    }
}
