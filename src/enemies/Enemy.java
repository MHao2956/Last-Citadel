package enemies;

import java.awt.Rectangle;
import static helpz.Constants.Diretion.*; // dau * la lay het
//asbstract class because can not create an enenemy from enemy class, craeate from enemy1,2,3, ko tao enemy trong super class
public abstract class Enemy {
    
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
        this.enemyType = enemyType;
        bounds = new Rectangle((int) x, (int) y, 32, 32 );
        lastDir = -1; // tell the enemyManager is -1 is the fist update so i need to find a direction that i can go no matter where i am
    }
    public void setStartHealth(){
        health=helpz.Constants.Enemies.GetStartHealth(enemyType);
    }

    public void move(float speed, int dir){
        lastDir = dir;
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

    public void setPos(int x, int y){
        //dont use this one for move, this is for pos fix
        this.x = x;
        this.y = y;
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

