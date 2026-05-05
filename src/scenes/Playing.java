package scenes;

import java.awt.*;
import main.Game;
import managers.WaveManager;
public class Playing extends GameScene implements SceneMethods{
    private WaveManager waveManager;
    public Playing(Game game) {
        super(game);
        waveManager = new WaveManager(this);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(0,0,640,640);
    }

    @Override
    public void mouseClicked(int x, int y) {
        
    }

    @Override
    public void mouseMoved(int x, int y) {

    }

    @Override
    public void mousePressed(int x, int y) {

    }

    @Override
    public void mouseReleased(int x, int y) {

    }

    @Override
    public void mouseDragged(int x, int y) {

    }
    public WaveManager getWaveManager() {
        return waveManager;
    }

    public void update() {
    }
}
