package enemies;

import java.awt.Rectangle;
import static helpz.Constants.Diretion.*; // dau * la lay het
//asbstract class because can not create an enenemy from enemy class, craeate from enemy1,2,3, ko tao enemy trong super class
public abstract class Enemy {
    
    protected float x,y;
    protected Rectangle bounds; //latter on add hitbox
    protected int health;
    protected int maxHealth;
    protected int ID;
    protected int enemyType;
    protected int lastDir;
    protected boolean alive = true;
    protected int slowTickLimit = 120;
    protected int slowTick = slowTickLimit;

    public Enemy(float x, float y, int ID, int enemyType){
        this.x = x;
        this.y = y;
        this.ID = ID;
        this.enemyType = enemyType;
        bounds = new Rectangle((int) x, (int) y, 32, 32 );
        lastDir = -1; // tell the enemyManager is -1 is the fist update so i need to find a direction that i can go no matter where i am
        setStartHealth();
    }
    public void setStartHealth(){
        health=helpz.Constants.Enemies.GetStartHealth(enemyType);
        maxHealth = health;
    }
    public void hurt(int damage){
       this.health -= damage;
       if(health <= 0)
           alive = false;
    }

    public void slow(){
        slowTick = 0;
    }

    public void move(float speed, int dir){
        lastDir = dir;

        if(slowTick < slowTickLimit){
            slowTick++;
            speed *= 0.5f;
        }

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
        updateHitbox();
    }
    private void updateHitbox() {
        bounds.x = (int) x;
        bounds.y = (int) y;
    }

    public void setPos(int x, int y){
        //dont use this one for move, this is for pos fix
        this.x = x;
        this.y = y;
    }

    public float getHealthBarFloat(){
        return health/ (float) maxHealth;}


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
        public boolean isAlive(){
            return alive;
        }
        public boolean isSlowed(){
            return slowTick < slowTickLimit;
        }
    }

