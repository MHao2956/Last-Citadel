package managers;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import objects.Tower;
import scenes.Playing;
import helpz.LoadSave;
import static helpz.Constants.Towers.*;
import enemies.Enemy;

public class TowerManager {

    private Playing playing;
    private BufferedImage[] towerImgs;
    private ArrayList<Tower> towers = new ArrayList<>(); 
    private int towerAmount = 0;
    public TowerManager(Playing playing) {
        this.playing = playing;
        loadTowerImgs();
    }
    private void loadTowerImgs() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        towerImgs = new BufferedImage[3];
        for (int i = 0; i < 3; i++) {
            towerImgs[i] = atlas.getSubimage((4 + i) * 32, 32, 32, 32);
        }
    }
    public void addTower(Tower selectedTower,int xPosition,int yPosition){
        towers.add(new Tower(xPosition,yPosition,towerAmount++,selectedTower.getTowerType()));}
    public void removeTower(Tower displayedTower){
        for(int i=0;i<towers.size();i++){
            if(towers.get(i).getId() == displayedTower.getId()){
                towers.remove(i);
            }
        }
    }
    public void upgradeTower(Tower displayedTower){
        for(Tower t:towers){
            if(t.getId() == displayedTower.getId()){
                t.upgradeTower();
            }
        }
    }
    
    public void draw(Graphics g) {
        for (Tower t : towers) {
            g.drawImage(towerImgs[t.getTowerType()], t.getX(), t.getY(),null);
        }
    }
    public Tower getTowerAt(int x,int y){
        for(Tower t:towers)
            if(t.getX() ==x && t.getY() ==y)
                    return t;
        return null;
    }
    public BufferedImage[] getTowerImgs() {
        return towerImgs;
    }
    public void update() { 
         for(Tower t:towers){
             t.update();
            attackEnemies(t);
        }
       
    }
    private void attackEnemies(Tower t) {
            for(Enemy e:playing.getEnemyManager().getEnemies()){
                if(e.isAlive())  
                    if(isEnemiesInRange(t,e)){
                        if(t.isCooldownOver()){
                        playing.shootEnemy(t,e); 
                        t.resetCooldown();
                        }
                }
            }
        }
    
    private boolean isEnemiesInRange(Tower t, Enemy e) {
            int range =helpz.Utilz.GetHypotenuseDistance(t.getX(), t.getY(),e.getX(),e.getY());
                  return range <= t.getRange();
    }
}
