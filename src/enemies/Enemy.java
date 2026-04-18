package enemies;
import java.awt.Rectangle;

public class Enemy {
    
    private float x,y;
    private Rectangle bounds;
    private int health;
    private int ID;
    private int enemyType;


    public Enemy(float x, float y, int ID, int enemyType){
        this.x = x;
        this.y = y;
        this.ID = ID;
        bounds = new Rectangle((int) x, (int) y, 32, 32 );

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
    }

