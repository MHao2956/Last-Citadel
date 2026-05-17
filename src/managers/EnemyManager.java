package managers;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import helpz.LoadSave;
import objects.PathPoint;
import enemies.Enemy;
import scenes.Playing;
import static helpz.Constants.Diretion.*;
import static helpz.Constants.Enemies.*;
import static helpz.Constants.Tiles.*;

import java.awt.Color;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class EnemyManager {

    private BufferedImage[] enemyImgs;
    private Playing playing;
    private List<Enemy> enemies = new CopyOnWriteArrayList<>();
    private PathPoint start, end;
    private int HPBarWidth = 20;
    private BufferedImage slowEffect;
    

    public EnemyManager(Playing playing, PathPoint start, PathPoint end){
        this.playing = playing;
        enemyImgs = new BufferedImage[4];
        this.start = start;
        this.end = end;

        loadEffectImg();
        
//        addEnemy(ENEMY1);// so o muon spam enemy * 32
//        addEnemy(ENEMY2);
//        addEnemy(ENEMY3);
//        addEnemy(ENEMY4);
//
        loadEnemyImgs();
    }

    private void loadEffectImg(){
        slowEffect = LoadSave.getSpriteAtlas().getSubimage(32 * 9, 32 * 2, 32, 32);
    }

    public void loadEnemyImgs(){
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        
        for(int i = 0; i < 4; i++)
            enemyImgs[i] = atlas.getSubimage( i* 32, 32, 32, 32);  
        
    }

    public void update(){

        updateWaveManager();


        for (Enemy e : enemies){
       if(e.isAlive())
           updateEnemyMoveNew(e);
        }   
    }

    private void updateWaveManager() {
        playing.getWaveManager().update();
    }



    public void updateEnemyMoveNew(Enemy e){
        if(e.getLastDir() == -1)
            setNewDirectionAndMove(e);
        int newX = (int)(e.getX() + getSpeedAndWidth(e.getLastDir(), e.getEnemyType()));
        int newY = (int)(e.getY() + getSpeedAndHeight(e.getLastDir(), e.getEnemyType()));

        if(getTileType(newX,newY) == ROAD_TILE){
            e.move(GetSpeed(e.getEnemyType()), e.getLastDir());
        }else if(isAtEnd(e)){
            e.kill();
            playing.removeOneLife();
        }else {
            setNewDirectionAndMove(e);
        }
    }

    private void setNewDirectionAndMove(Enemy e){
        int dir = e.getLastDir();

        //move into the curent tile 100%
        int xCord = (int)e.getX() / 32;
        int yCore = (int)e.getY() / 32;

        fixEnemyOffsetTile(e, dir, xCord, yCore);

        if(isAtEnd(e))
            return;

        if(dir == LEFT || dir == RIGHT){
            int newY = (int)(e.getY() + getSpeedAndHeight(UP, e.getEnemyType())); 
        
            if(getTileType((int) e.getX(), newY) ==  ROAD_TILE)
                e.move(GetSpeed(e.getEnemyType()), UP);
            else
                e.move(GetSpeed(e.getEnemyType()), DOWN);
            }else {
                int newX = (int)(e.getX() + getSpeedAndWidth(RIGHT, e.getEnemyType()));
                if(getTileType(newX, (int)e.getY()) == ROAD_TILE)
                    e.move(GetSpeed(e.getEnemyType()), RIGHT);
                    else
                        e.move(GetSpeed(e.getEnemyType()), LEFT);
                
            }
        }


        private void fixEnemyOffsetTile(Enemy e, int dir, int xCord, int yCore){
            switch (dir) {
                case RIGHT:
                    if(xCord < 19)
                        xCord++;
                    break;
                case DOWN:
                    if(yCore < 19)
                        yCore++;
                    break;
            }
            e.setPos( xCord * 32, yCore * 32);
        }


    private boolean isAtEnd(Enemy e) {
        int currentX = (int) (e.getX() / 32);
        int currentY = (int) (e.getY() / 32);

        if (currentX == end.getxCord() && currentY == end.getyCord()) {
            return true;
        }
        
        if (currentX >= 20 || currentX < 0 || currentY >= 20 || currentY < 0) {
            return true;
        }

        return false;
    }
    

    private int getTileType(int x, int y) {
		return playing.getTileType(x, y);
	}


      private float getSpeedAndHeight(int dir, int enemyType){
          //to do auto - generated method sub
        if(dir == UP){
            return -GetSpeed(enemyType);
        }else if(dir == DOWN){
            return GetSpeed(enemyType) + 32;
        }
        return 0;
    }



    private float getSpeedAndWidth(int dir, int enemyType){
        //to do auto - generated method sub
        if(dir == LEFT){
            return -GetSpeed(enemyType);
        }else if(dir == RIGHT){
            return GetSpeed(enemyType) + 32;
        }
        return 0;
    }

    public void spawnEnemy(int nextEnemy) {
        addEnemy(nextEnemy);
    }

 public void addEnemy(int enemyType) {

    int x = start.getxCord() * 32;
    int y = start.getyCord() * 32;

    Enemy enemy = EnemyFactory.createEnemy(enemyType, x, y, this);

    if (enemy != null) {
        enemies.add(enemy);
    }
}

    public void draw(Graphics g){ 
        for (Enemy e : enemies){
        if(e.isAlive()){
        drawEnemy(e, g);
        drawHealthBar(e, g);
        drawEffects(e, g);
           } 
        }
    }

    private void drawEffects(Enemy e, Graphics g){
        if( e.isFrozen())
            g.drawImage(slowEffect, (int) e.getX(), (int)e.getY(), null);
        
    }

    private void drawHealthBar(Enemy e, Graphics g){
        g.setColor(Color.RED);
        g.fillRect((int)e.getX()+16 -(getNewHPBarWidth(e)/2),(int)e.getY()-10, getNewHPBarWidth(e), 3);
    }
    private int getNewHPBarWidth(Enemy e){
        return (int)(HPBarWidth * e.getHealthBarFloat());
    }
    private void drawEnemy(Enemy e, Graphics g){
        g.drawImage(enemyImgs[e.getEnemyType()],(int)e.getX() ,(int)e.getY(), null);
    }
    public List<Enemy> getEnemies() {
        return enemies;
    }

    public int getAmountOfAliveEnemies(){
        int size = 0;
        for(Enemy e: enemies)
            if(e.isAlive())
                size++;

        return size;
    }
    public void rewardPlayer(int enemyType){
        playing.rewardPlayer(enemyType);
}

    public void reset(){
        enemies.clear();
    }
}