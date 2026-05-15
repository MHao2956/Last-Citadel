package scenes;

import helpz.AudioPlayer;
import main.Game;
import ui.MyButton;

import java.awt.*;

import static main.GameStates.*;

public class GameWin extends GameScene implements SceneMethods{
    private MyButton bReplay, bMenu;
    private boolean wasOverMenu = false, wasOverReplay = false;
    private AudioPlayer audioPlayer;

    public GameWin(Game game) {
        super(game);
        this.audioPlayer = game.getAudioPlayer();
        initButtons();
    }
    private void initButtons() {
        int w = 150;
        int h = 50;
        int x = 640 / 2 - w / 2;

        bMenu = new MyButton("Menu", x, 350, w, h);
        bReplay = new MyButton("Replay", x, 450, w, h);
    }


    @Override
    public void render(Graphics g) {
        g.setColor(new Color(240, 240, 240));
        g.fillRect(0, 0, 640, 800);

        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 40));

        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth("Game Win!");
        g.drawString("Game Win!", 640 / 2 - textWidth / 2, 180);

        bMenu.draw(g);
        bReplay.draw(g);
    }

    @Override
    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            resetAll();
            SetGameState(MENU);
        } else if (bReplay.getBounds().contains(x, y)) {
            resetAll();
            SetGameState(PLAYING);
        }
    }

    private void resetAll() {
        game.getPlaying().resetEverything();
    }

    @Override
    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(bMenu.getBounds().contains(x, y));
        bReplay.setMouseOver(bReplay.getBounds().contains(x, y));

        if (bMenu.isMouseOver() && !wasOverMenu) audioPlayer.playSoundEffect("res/audio/hover.wav");
        if (bReplay.isMouseOver() && !wasOverReplay) audioPlayer.playSoundEffect("res/audio/hover.wav");

        wasOverMenu = bMenu.isMouseOver();
        wasOverReplay = bReplay.isMouseOver();
    }

    @Override
    public void mousePressed(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) bMenu.setMousePressed(true);
        else if (bReplay.getBounds().contains(x, y)) bReplay.setMousePressed(true);
    }

    @Override
    public void mouseReleased(int x, int y) {
        bMenu.resetBooleans();
        bReplay.resetBooleans();
    }

    @Override
    public void mouseDragged(int x, int y) {

    }
}
