package scenes;

import java.awt.*;
import main.Game;

public class Playing extends GameScene implements SceneMethod{
    public Playing(Game game) {
        super(game);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(0,0,640,640);
    }
}
