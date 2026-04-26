package managers;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import objects.Tower;
import scenes.Playing;
import helpz.LoadSave;
import static helpz.Constants.Towers.*;

public class TowerManager {

    private Playing playing;
    private BufferedImage[] towerImgs;
    private ArrayList<Tower> towers = new ArrayList<>(); 
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
    public void draw(Graphics g) {
        for (Tower t : towers) {
            g.drawImage(towerImgs[t.getTowerType()], t.getX(), t.getY(),null);
        }
    }
    public void update() {
    }
}