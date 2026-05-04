package scenes;

import java.awt.*;
import main.Game;
import managers.WaveManager;

public class Playing extends GameScene implements SceneMethod{

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

    public WaveManager getWaveManager() {
        return waveManager;
    }
}
