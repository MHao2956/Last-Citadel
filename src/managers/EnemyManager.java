package managers;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import helpz.LoadSave;
import objects.PathPoint;
import enemies.ENEMY1;
import enemies.ENEMY2;
import enemies.ENEMY3;
import enemies.ENEMY4;
import enemies.Enemy;
import scenes.Playing;
import static helpz.Constants.Diretion.*;
import static helpz.Constants.Enemies.*;
import static helpz.Constants.Tiles.*;



public class EnemyManager {

    private BufferedImage[] enemyImgs;
    private Playing playing;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private float speed = 0.5f;
    private PathPoint start, end;
    

    public EnemyManager(Playing playing, PathPoint start, PathPoint end){
        this.playing = playing;
        enemyImgs = new BufferedImage[4];
        this.start = start;
        this.end = end;
        
        addEnemy(ENEMY1);// so o muon spam enemy * 32
        addEnemy(ENEMY2);
        addEnemy(ENEMY3);
        addEnemy(ENEMY4);
         
        loadEnemyImgs();
    }

    public void loadEnemyImgs(){
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        
        for(int i = 0; i < 4; i++)
            enemyImgs[i] = atlas.getSubimage( i* 32, 32, 32, 32);  
        
    }

    public void update(){

        updateWaveManager();

        if(isTimeForNewEnemy()){
            spawnEnemy();
        }

        for (Enemy e : enemies){
        //is next tile road(pos, dir)
        updateEnemyMove(e);
        }
    }

    private void updateWaveManager() {
        playing.getWaveManager().update();
    }

    private void spawnEnemy() {
        addEnemy(playing.getWaveManager().getNextEnemy());
    }

    private boolean isTimeForNewEnemy() {
        if(playing.getWaveManager().isTimeForNewEnemy()){
            if(playing.getWaveManager().isThereMoreEnemiesInWave()){
                return true;
            }
        }

        return false;
    }

    public void updateEnemyMove(Enemy e){
        if(e.getLastDir() == -1)
            setNewDirectionAndMove(e);
        // e pos
        // e dir
        // tile at new possible pos
        int newX = (int)(e.getX() + getSpeedAndWidth(e.getLastDir()));
        int newY = (int)(e.getY() + getSpeedAndHeight(e.getLastDir()));

        if(getTileType(newX,newY) == ROAD_TILE){
            //keep moving in same direction
            e.move(speed, e.getLastDir());
        }else if(isAtEnd(e)){
            System.out.println("Lives lost!");
        }else {
            //find new direction
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
            int newY = (int)(e.getY() + getSpeedAndHeight(UP)); 
        
            if(getTileType((int) e.getX(), newY) ==  ROAD_TILE)
                e.move(speed, UP);
            else
                e.move(speed, DOWN);
            }else {
                int newX = (int)(e.getX() + getSpeedAndWidth(RIGHT));
                if(getTileType(newX, (int)e.getY()) == ROAD_TILE)
                    e.move(speed, RIGHT);
                    else
                        e.move(speed, LEFT);
                
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


        private boolean isAtEnd(Enemy e){
            if(e.getX() == end.getxCord() * 32)
                if(e.getY() == end.getyCord() * 32)
                    return true;
                return false;
        }
    

    private int getTileType(int x, int y) {
		return playing.getTileType(x, y);
	}


      private float getSpeedAndHeight(int dir){
          //to do auto - generated method sub
        if(dir == UP){
            return -speed;
        }else if(dir == DOWN){
            return speed + 32;
        }
        return 0;
    }



    private float getSpeedAndWidth(int dir){
        //to do auto - generated method sub
        if(dir == LEFT){
            return -speed;
        }else if(dir == RIGHT){
            return speed + 32;
        }
        return 0;
    }

    public void addEnemy( int enemyType){

        int x = start.getxCord() * 32;
        int y = start.getyCord() * 32;

        switch(enemyType){
            case ENEMY1:
                enemies.add(new ENEMY1(x, y, 0));
                break;
            case ENEMY2:
                enemies.add(new ENEMY2(x, y, 0));
                break;
            case ENEMY3:
                enemies.add(new ENEMY3(x, y, 0));
                break;
            case ENEMY4:
                enemies.add(new ENEMY4(x, y, 0));
                break;
        }
        
    }

    public void draw(Graphics g){ 
        for (Enemy e : enemies){
        drawEnemy(e, g);
        }
    }

    private void drawEnemy(Enemy e, Graphics g){
        g.drawImage(enemyImgs[e.getEnemyType()],(int)e.getX() ,(int)e.getY(), null);
    }





}
