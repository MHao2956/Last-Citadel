package managers;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import scenes.Playing;
import objects.Projectile;
import enemies.Enemy;
import objects.Tower;
import helpz.LoadSave;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import static helpz.Constants.Towers.*;
import static helpz.Constants.Projectile.*;


public class ProjectileManager {
    private Playing playing;
    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private ArrayList<Explosion> explosions = new ArrayList<>();
    private BufferedImage[] proj_imgs, explo_imgs;
    private int proj_id = 0;
    

    public ProjectileManager(Playing playing) {
        this.playing = playing;
        importImgs();

    }
    private void importImgs(){
        BufferedImage atlas = helpz.LoadSave.getSpriteAtlas();
        proj_imgs = new BufferedImage[3];
        for(int i = 0; i < 3; i++)
            proj_imgs[i] = atlas.getSubimage((7+i) * 32, 32, 32, 32);
        importExplosion(atlas);
    }

    private void importExplosion(BufferedImage atlas){
        explo_imgs = new BufferedImage[7];

        for(int i = 0; i < 7; i++)
            explo_imgs[i] = atlas.getSubimage(i * 32, 32 * 2, 32, 32);
    }

    public void newProjectile(Tower t,Enemy e){
        int type = t.getTowerType();
        int xDistance=(int)(t.getX() - e.getX());
        int yDistance=(int)(t.getY() - e.getY());
        int totalDistance = Math.abs(xDistance) + Math.abs(yDistance);
        float xPercent = (float)Math.abs(xDistance)/totalDistance;
   
        float xSpeed= xPercent*helpz.Constants.Projectile.GetSpeed(type);
        float ySpeed= helpz.Constants.Projectile.GetSpeed(type)-xSpeed;
        if (t.getX()>e.getX()) 
                xSpeed*=-1;
        if (t.getY()>e.getY())
                ySpeed*=-1;

        float rotate = 0;

        if(type == FIRE){
        float arcValue=(float)Math.atan(yDistance/(float) xDistance);
        rotate = (float) Math.toDegrees(arcValue);

            if(xDistance < 0)
                rotate+=180;
        }
     
        projectiles.add(new Projectile(t.getX()+16,t.getY()+16,xSpeed,ySpeed,t.getDamage(),rotate,proj_id++,type));
    }
    public void update() {
        for(Projectile p:projectiles){
            if(p.isActive()){
                p.move();
                if(isProjHittingEnemy(p)){
                    p.setActive(false);
                    if(p.getProjectileType() == ROCKET){
                       explosions.add(new Explosion(p.getPos()));
                        
                } 
            }else{
         
            }
        }
    }
        for(Explosion e : explosions)
            e.update();
    }
    private boolean isProjHittingEnemy(Projectile p) {
        for(Enemy e:playing.getEnemyManager().getEnemies()){
            if(e.getBounds().contains(p.getPos())){
                e.hurt(p.getDamage());
                return true;
            }
        }

       return false;
    }
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;


        // for(int i = 0; i < explo_imgs.length; i++)
        //     g2d.drawImage(explo_imgs[i], 300 + i * 32, 300, null);

        for(Projectile p:projectiles)
            if(p.isActive()){
            if(p.getProjectileType() == FIRE){
            g2d.translate(p.getPos().x, p.getPos().y);
            g2d.rotate(Math.toRadians(90));
            g2d.drawImage(proj_imgs[p.getProjectileType()], -16, -16, null);
            g2d.rotate(Math.toRadians(-90));
            g2d.translate(-p.getPos().x, -p.getPos().y);
            }else{
                 g2d.drawImage(proj_imgs[p.getProjectileType()],(int)p.getPos().x-16, (int)p.getPos().y-16, null);
            }
        }

        drawExplosion(g2d);

    }

    private void drawExplosion(Graphics2D g2d){
       for(Explosion e : explosions)
        if(e.getIndex() < 7)
            g2d.drawImage(explo_imgs[e.getIndex()], (int)e.getPos().x - 16, (int)e.getPos().y -16, null);// x,y - 16 de ho up into left
    }
    private int getProjType(Tower t){
        switch(t.getTowerType()){
            case ICE_TOWER:
                 return ICE;
            case FIRE_TOWER: 
                return FIRE;
            case ROCKET_TOWER: 
                return ROCKET;
        }
        return 0;
    }

    public class Explosion{

        private Point2D.Float pos;
        private int exploTick = 0, exploIndex = 0;
        public Explosion(Point2D.Float pos){
            this.pos = pos;
        }
        public void update(){
             
            exploTick++;
            if(exploTick >= 12){
                exploTick = 0;
                exploIndex++;
                
                }
            
        }

        public int getIndex(){
            return exploIndex;
        }
        public Point2D.Float getPos(){
            return pos;
        }
    }
}
