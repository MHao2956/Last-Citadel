package scenes;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import enemies.Enemy;
import helpz.LoadSave;
import main.Game;
import managers.EnemyManager;
import managers.WaveManager;
import objects.PathPoint;
import ui.ActionBar;

public class Playing extends GameScene implements SceneMethods {

    private int[][] lvl; 
    private ActionBar actionBar;
    private int mouseX, mouseY;
    private EnemyManager enemyManager;
    private WaveManager waveManager;
    private PathPoint start, end;

    public Playing(Game game) {
        super(game);
        loadDefaultLevel();
        

        actionBar = new ActionBar(0, 640, 640, 160, this); 

        enemyManager = new EnemyManager(this, start, end);

        waveManager = new WaveManager(this);
    }

    private void loadDefaultLevel(){


        lvl = LoadSave.GetLevelData("new_level");
         ArrayList<PathPoint> points = LoadSave.GetLevelPathPoints("new_level");

        start = points.get(0);
        end = points.get(1);
    }

    public void setLevel(int[][] lvl, PathPoint start, PathPoint end){
    this.lvl = lvl;
    this.start = start;
    this.end = end;
    enemyManager = new EnemyManager(this, start, end);
}
    public void update(){
        updateTick();
        waveManager.update();
        if(isAllEnemiesDead()){
            if(isThereMoreWaves()){
                waveManager.startWaveTimer();
                //check timer
                if(isWaveTimerOver()){
                    
                }

                //Increase wave index
            }
        }
        if(isTimeForNewEnemy()){
            spawnEnemy();
        }

        enemyManager.update();
    }

    private boolean isWaveTimerOver() {
    }

    private boolean isThereMoreWaves() {
        return waveManager.isThereMoreWaves();
    }

    private boolean isAllEnemiesDead() {

        if(waveManager.isThereMoreEnemiesInWave()){
            return false;
        }

        for(Enemy e : enemyManager.getEnemies())
            if(e.isAlive())
                return false;

        return true;
    }

    private void spawnEnemy() {
        enemyManager.spawnEnemy(waveManager.getNextEnemy());
    }

    private boolean isTimeForNewEnemy() {
        if(waveManager.isTimeForNewEnemy()){
            if(waveManager.isThereMoreEnemiesInWave()){
                return true;
            }
        }

        return false;
    }

    @Override
    public void render(Graphics g){
        
        drawLevel(g);
        actionBar.draw(g);
        enemyManager.draw(g);

    }

    private void drawLevel(Graphics g){

        if (lvl == null) return;
        for(int y = 0; y < lvl.length; y++){
            for(int x = 0; x < lvl[y].length; x++){
                int id = lvl[y][x];
                   if(isAnimation(id)){
                    g.drawImage(getSprite(id, animationIndex), x*32, y*32, null);
                } else
                    g.drawImage(getSprite(id), x*32, y*32, null);


            }
        }
    }


    public int getTileType(int x, int y){
        int xCord = x / 32;
        int yCord = y / 32;

        if(xCord < 0 || xCord > 19)
            return 0;
        if(yCord < 0 || yCord > 19)
            return 0;


        int id = lvl[y / 32][x / 32];
        return game.getTileManager().getTile(id).getTileType();
    }


    @Override
    public void mouseClicked(int x, int y){
        if(y >= 640)
            actionBar.mouseClicked(x, y);
        // else
        //     enemyManager.addEnemy(x,y);      
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


    public WaveManager getWaveManager() {
        return waveManager;
    }


}
