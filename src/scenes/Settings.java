package scenes;

import java.awt.*;
import main.Game;

public class Settings extends GameScene implements SceneMethod{
    public Settings(Game game) {
        super(game);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(0,0,640,640);
    }
}
