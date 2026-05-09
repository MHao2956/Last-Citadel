package objects;
import java.awt.geom.Point2D;

public class Projectile {
    private Point2D.Float pos;
    private int id, projectileType, damage;
    private boolean active = true;
    private float xSpeed, ySpeed,rotation;

    public Projectile(float x, float y, float xSpeed, float ySpeed, int damage, float rotation, int id, int projectileType){
        pos = new Point2D.Float(x, y);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.damage = damage;
        this.rotation = rotation;
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
    public int getDamage(){
        return damage;
    }
    public float getRotation(){
        return rotation;
    }
}