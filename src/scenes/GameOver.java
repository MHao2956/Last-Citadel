package scenes;

import main.Game;
import ui.MyButton;
import static main.GameStates.*;

import java.awt.*;

import helpz.LoadSave;
import java.awt.image.BufferedImage;

import helpz.AudioPlayer;

public class GameOver extends GameScene implements SceneMethods {

    private MyButton bReplay, bMenu;
    private boolean wasOverMenu = false;
    private boolean wasOverReplay = false;

    private BufferedImage gameOverBg;
    private BufferedImage menuImg;
    private BufferedImage replayImg;

private float menuScale = 1f;
private float replayScale = 1f;

private AudioPlayer audioPlayer;

    public GameOver(Game game) {
        super(game);
        this.audioPlayer = game.getAudioPlayer();
        gameOverBg = LoadSave.getImage("gameover_bg.png");
        menuImg = LoadSave.getImage("btn_menu.png");
        replayImg = LoadSave.getImage("btn_replay.png");
        initButtons();

    }

 private void initButtons() {
    int w = 300;
    int h = 175;
    int x = 640 / 2 - w / 2;

    bMenu = new MyButton("Menu", x, 400, w, h);
    bReplay = new MyButton("Replay", x, 530, w, h);
}

@Override
public void render(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;

    if (gameOverBg != null) {
        g2d.drawImage(gameOverBg, 0, 0, 640, 800, null);
    }

    updateButtonScale();

    drawImageButton(g2d, menuImg, bMenu, menuScale);
    drawImageButton(g2d, replayImg, bReplay, replayScale);
}

private void updateButtonScale() {
    if (bMenu.isMousePressed())
        menuScale = approach(menuScale, 0.95f);
    else if (bMenu.isMouseOver())
        menuScale = approach(menuScale, 1.10f);
    else
        menuScale = approach(menuScale, 1f);

    if (bReplay.isMousePressed())
        replayScale = approach(replayScale, 0.95f);
    else if (bReplay.isMouseOver())
        replayScale = approach(replayScale, 1.10f);
    else
        replayScale = approach(replayScale, 1f);
}


//method vẽ ảnh nút
private float approach(float current, float target) {
    return current + (target - current) * 0.18f;
}

private void drawImageButton(Graphics2D g2d, BufferedImage img, MyButton b, float scale) {
    if (img == null)
        return;

    int newWidth = (int) (b.width * scale);
    int newHeight = (int) (b.height * scale);

    int newX = b.x - (newWidth - b.width) / 2;
    int newY = b.y - (newHeight - b.height) / 2;

    g2d.drawImage(img, newX, newY, newWidth, newHeight, null);
}

    private void replayGame() {
        //reset everything
        resetAll();
        //Change state to playing
        SetGameState(PLAYING);
    }

    private void resetAll(){
        game.getPlaying().resetEverything();
    }

    @Override
    public void mouseClicked(int x, int y) {
        if(bMenu.getBounds().contains(x, y)){
            SetGameState(MENU);
            resetAll();
        }
        else if(bReplay.getBounds().contains(x, y)){
            replayGame();
        }
    }



@Override
public void mouseMoved(int x, int y) {

    boolean overMenu = bMenu.getBounds().contains(x, y);
    boolean overReplay = bReplay.getBounds().contains(x, y);

    bMenu.setMouseOver(overMenu);
    bReplay.setMouseOver(overReplay);

    if (overMenu && !wasOverMenu) {
        audioPlayer.playSoundEffect("res/audio/hover.wav");
    }

    if (overReplay && !wasOverReplay) {
        audioPlayer.playSoundEffect("res/audio/hover.wav");
    }

    wasOverMenu = overMenu;
    wasOverReplay = overReplay;
}

    @Override
    public void mousePressed(int x, int y) {
        if(bMenu.getBounds().contains(x, y)){
            bMenu.setMousePressed(true);
        }
        else if(bReplay.getBounds().contains(x, y)){
            bReplay.setMousePressed(true);
        }
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
