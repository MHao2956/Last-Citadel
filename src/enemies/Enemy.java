package enemies;

import java.awt.Rectangle;
import static helpz.Constants.Diretion.*; // dau * la lay het

public class Enemy {
    
    private float x,y;
    private Rectangle bounds; //latter on add hitbox
    private int health;
    private int ID;
    private int enemyType;
    private int lastDir;


    public Enemy(float x, float y, int ID, int enemyType){
        this.x = x;
        this.y = y;
        this.ID = ID;
        bounds = new Rectangle((int) x, (int) y, 32, 32 );
        lastDir = RIGHT;
    }

    public void move(float speed, int dir){
        switch (dir) {
            case LEFT:
                this.x -= speed;
                break;
            case UP:
                this.y -= speed;
                break;
            case RIGHT:
                this.x +=  speed;
                break;
            case DOWN:
                this.y += speed;
                break;
        }
    }

  


        public float getX(){
            return x;
        }
        public float getY(){
            return y;
        }
        public int getID(){
            return ID;
        }
        public int getEnemyType(){
            return enemyType;
        }
        public int getHealth(){
            return health;
        }
        public Rectangle getBounds(){
            return bounds;
        }
        public int getLastDir(){
            return lastDir;
        }
    }

