package scenes;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import helpz.LoadSave;
import main.Game; 
import ui.ActionBar;

public class Playing extends GameScene implements SceneMethods {

    private int[][] lvl; 
    private ActionBar actionBar;
    private int mouseX, mouseY;

    public Playing(Game game) {
        super(game);
        loadDefaultLevel();
        
        actionBar = new ActionBar(0, 640, 640, 100, this); 

    }

    private void loadDefaultLevel(){
        lvl = LoadSave.GetLevelData("new_level.txt");

        if (lvl == null) {
            lvl = new int[20][20];
        }
    }

    public void update() {
        updateTick();
    }

    public void setLevel(int[][] lvl){
        this.lvl = lvl;
    }

    @Override
    public void render(Graphics g){
        
        drawLevel(g);
        actionBar.draw(g);
    }

    private void drawLevel(Graphics g){

        if (lvl == null) return;
        for(int y = 0; y < lvl.length; y++){
            for(int x = 0; x < lvl[y].length; x++){
                int id = lvl[y][x];
                if (isAnimation(id)) {
                    g.drawImage(getSprite(id, animationIndex), x*32, y*32, null);
                } else
                    g.drawImage(getSprite(id), x * 32, y * 32, null);
            }
        }
    }

    // private BufferedImage getSprite(int spriteID){
    //     return game.getTileManager().getSprite(spriteID);
    // }

    @Override
    public void mouseClicked(int x, int y){
        if(y >= 640)
            actionBar.mouseClicked(x, y);      
    }

    
    @Override
    public void mouseMoved(int x, int y){
        if(y >= 640)
            actionBar.mouseMoved(x, y);
        else {
            mouseX = (x/32)*32;
            mouseY = (y/32)*32;
        }
    }

    @Override
    public void mousePressed(int x, int y){
        if(y >= 640){
            actionBar.mousePressed(x, y);
        }       
    }
    @Override
    public void mouseReleased(int x, int y){
        actionBar.mouseReleased(x, y);
     
    }

    @Override
    public void mouseDragged(int x, int y){

    }

    
}
