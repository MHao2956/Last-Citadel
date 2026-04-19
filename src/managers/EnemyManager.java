package managers;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import helpz.LoadSave;
import enemies.Enemy;
import scenes.Playing;

public class EnemyManager {

    private BufferedImage[] enemyImgs;
    private Playing playing;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    

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
        e.move(0.5f, 0);
        }
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
