package scenes;

import java.awt.Graphics;
import main.Game;

public class Playing extends GameScene implements SceneMethods {

    private int[][] lvl; 
    private TitleManager titleManager;
    public Playing(Game game) {
        super(game);

        lvl = LevelBuild.getLevelData();
        titleManager = new TitleManager();
    }

    @Override
    public void render(Graphics g){
        for(int y = 0; y < lvl.length; y++){
            for(int x = 0; x < lvl[y].length; x++){
                int id = lvl[y][x];
                g.drawImage(titleManager.getSprite(id), x*32, y*32, null)
            }
        }
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
    public void mouseRelease(int x, int y){
        
    }
}
