package scenes;
import main.Game;

import java.awt.*;

public abstract class GameScene {
    private final Game game;
    public GameScene(Game game){
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public abstract void render(Graphics g);
}
