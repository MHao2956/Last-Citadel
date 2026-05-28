package objects;
import java.awt.geom.Point2D;

public class Projectile {
    private Point2D.Float pos;
    private int id, projectileType, dmg ,towerTier;
    private boolean active = true;
    private float xSpeed, ySpeed,rotation;

    public Projectile(float x, float y, float xSpeed, float ySpeed, int dmg, 
        float rotation, int id, int projectileType, int towerTier){
        pos = new Point2D.Float(x, y);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.dmg = dmg;
        this.rotation = rotation;
        this.towerTier = towerTier;
        this.id = id;
        this.projectileType = projectileType;
}
    public void move(){
        pos.x += xSpeed;
        pos.y += ySpeed;
    }
    public Point2D.Float getPos(){
        return pos;
    }
    public void setPos(float x, float y){
        this.pos = pos;
    }
    public int getID(){
        return id;
    }
    public int getProjectileType(){
        return projectileType;
    }
    public boolean isActive(){
        return active;
    }
    public void setActive(boolean active){
        this.active = active;
    }
    public int getDmg(){
        return dmg;
    }
    public float getRotation(){
        return rotation;
    }
    public int getTowerTier() {
        return towerTier;
    }
}