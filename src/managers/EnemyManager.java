package managers;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import helpz.LoadSave;
import enemies.Enemy;
import scenes.Playing;
import static helpz.Constants.Diretion.*;
import static helpz.Constants.Tiles.*;



public class EnemyManager {

    private BufferedImage[] enemyImgs;
    private Playing playing;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private float speed = 0.5f;
    

    public EnemyManager(Playing playing){
        this.playing = playing;
        enemyImgs = new BufferedImage[4];
        addEnemy(3 * 32, 9 * 32);
        loadEnemyImgs();
    }

    public void loadEnemyImgs(){
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        enemyImgs[0] = atlas.getSubimage(0, 32, 32, 32);
        enemyImgs[1] = atlas.getSubimage(32, 32, 32, 32);  
        enemyImgs[2] = atlas.getSubimage(2 * 32, 32, 32, 32);  
        enemyImgs[3] = atlas.getSubimage(3 * 32, 32, 32, 32);    
    }

    public void update(){
        for (Enemy e : enemies){
        //is next tile road(pos, dir)
        if(isNextTileRoad(e)){
            //move enemy
           }
        }
    }

    public boolean isNextTileRoad(Enemy e){
        // e pos
        // e dir
        // tile at new possible pos
        int newX = (int)(e.getX() + getSpeedX(e.getLastDir()));
        int newY = (int)(e.getY() + getSpeedY(e.getLastDir()));

        if(getTileType(newX,newY) == ROAD_TILE){
            //keep moving in same direction
            e.move(speed, e.getLastDir());
        }else {
            //find new direction
        }
        return false;
    }

    private int getTileType(int x, int y){
       return  playing.getTileType(x,y);
    }


      private float getSpeedY(int dir){
          //to do auto - generated method sub
        if(dir == UP){
            return -speed;
        }else if(dir == DOWN){
            return speed;
        }
        return 0;
    }



    private float getSpeedX(int dir){
        //to do auto - generated method sub
        if(dir == LEFT){
            return -speed;
        }else if(dir == RIGHT){
            return speed;
        }
        return 0;
    }

    public void addEnemy(int x, int y){
        enemies.add(new Enemy(x, y, 0, 0));
    }

    public void draw(Graphics g){
        for (Enemy e : enemies){
        drawEnemy(e, g);
        }
    }

    private void drawEnemy(Enemy e, Graphics g){
        g.drawImage(enemyImgs[0],(int)e.getX() ,(int)e.getY(), null);
    }





}
