package scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.Game;
import helpz.LoadSave;

import static main.GameStates.*;

public class Settings extends GameScene implements SceneMethods {

    // Hitbox Bật / Tắt
    private Rectangle boundsMusicOn, boundsMusicOff;
    private Rectangle boundsBtnSfxOn, boundsBtnSfxOff;
    private Rectangle boundsShootSfxOn, boundsShootSfxOff;
    private BufferedImage settingsBgImg;

    // Hitbox Tăng / Giảm âm lượng
    private Rectangle boundsVolMinus, boundsVolPlus, boundsBack;

    private boolean isMusicOn = true;
    private boolean isBtnSfxOn = true;
    private boolean isShootSfxOn = true;
    private int volumeLevel = 5;

    private int lastHoverButton = -1;


    public Settings(Game game){
        super(game);

        loadImgs();

        initButtons();
        if (game.getAudioPlayer() != null) {
            this.isMusicOn = !game.getAudioPlayer().isMuted();
            this.volumeLevel = game.getAudioPlayer().getVolumeLevel();
        }
    }

    private void loadImgs(){
        settingsBgImg = LoadSave.getImage("settingsMenu.png");
    }

    private void initButtons() {

        // Music
        boundsMusicOn  = new Rectangle(320, 270, 56, 30);
        boundsMusicOff = new Rectangle(400, 270, 56, 30);

        // Button SFX
        boundsBtnSfxOn  = new Rectangle(320, 324, 56, 30);
        boundsBtnSfxOff = new Rectangle(400, 324, 56, 30);

        // Shoot SFX
        boundsShootSfxOn  = new Rectangle(320, 385, 56, 30);
        boundsShootSfxOff = new Rectangle(400, 385, 56, 30);

        // nút [-] và [+]
        boundsVolMinus = new Rectangle(227, 471, 56, 30);
        boundsVolPlus  = new Rectangle(385, 471, 56, 30);

        // Back
        boundsBack = new Rectangle(255, 735, 140, 45);
    }

    @Override
    public void render(Graphics g){

        if (settingsBgImg != null) {
            g.drawImage(settingsBgImg, 0, 0, 640, 800, null);
        }

        drawSelectButton(g, boundsMusicOn, "ON", isMusicOn);
        drawSelectButton(g, boundsMusicOff, "OFF", !isMusicOn);

        drawSelectButton(g, boundsBtnSfxOn, "ON", isBtnSfxOn);
        drawSelectButton(g, boundsBtnSfxOff, "OFF", !isBtnSfxOn);

        drawSelectButton(g, boundsShootSfxOn, "ON", isShootSfxOn);
        drawSelectButton(g, boundsShootSfxOff, "OFF", !isShootSfxOn);

        // hiển thị số Volume ở giữa
        g.setColor(new Color(245, 222, 179));
        g.setFont(new Font("Serif", Font.BOLD, 22));

        String volText = String.valueOf(volumeLevel);
        g.drawString(volText, 326, 492);
    }

    // nút ON/OFF
    private void drawSelectButton(Graphics g, Rectangle bounds, String text, boolean isSelected) {
        if (isSelected) g.setColor(new Color(60, 120, 120));
        else g.setColor(new Color(40, 30, 20));
        g.fillRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, 8, 8);

        if (isSelected) g.setColor(Color.CYAN);             // Viền sáng
        else g.setColor(new Color(133, 94, 66));            // Viền tối
        g.drawRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, 8, 8);

        g.setColor(new Color(245, 222, 179));
        g.setFont(new Font("Serif", Font.PLAIN, 14));

        java.awt.FontMetrics fm = g.getFontMetrics();
        int textX = bounds.x + (bounds.width - fm.stringWidth(text)) / 2;
        int textY = bounds.y + ((bounds.height - fm.getHeight()) / 2) + fm.getAscent();

        g.drawString(text, textX, textY);
    }

    @Override
    public void mouseClicked(int x, int y){ }

    @Override
    public void mousePressed(int x, int y){

    }

    @Override
    public void mouseReleased(int x, int y){
        if (boundsBack.contains(x, y)) {
            SetGameState(MENU);
        }

        // Bật/Tắt Music
        if (boundsMusicOn.contains(x, y) && !isMusicOn) {
            isMusicOn = true;
            getGame().getAudioPlayer().unmute();
        } else if (boundsMusicOff.contains(x, y) && isMusicOn) {
            isMusicOn = false;
            getGame().getAudioPlayer().mute();
        }

        // Bật/Tắt Button SFX
        if (boundsBtnSfxOn.contains(x, y) && !isBtnSfxOn) {
            isBtnSfxOn = true;
            getGame().getAudioPlayer().setButtonSfxOn(true);
        } else if (boundsBtnSfxOff.contains(x, y) && isBtnSfxOn) {
            isBtnSfxOn = false;
            getGame().getAudioPlayer().setButtonSfxOn(false);
        }

        // Bật/Tắt Shoot SFX
        if (boundsShootSfxOn.contains(x, y) && !isShootSfxOn) {
            isShootSfxOn = true;
            getGame().getAudioPlayer().setShootSfxOn(true);
        } else if (boundsShootSfxOff.contains(x, y) && isShootSfxOn) {
            isShootSfxOn = false;
            getGame().getAudioPlayer().setShootSfxOn(false);
        }

        // Tăng/Giảm Volume
        if (boundsVolMinus.contains(x, y)) {
            decreaseVolume();
        } else if (boundsVolPlus.contains(x, y)) {
            increaseVolume();
        }

        resetButtons();
    }

    private void increaseVolume() {
        if (volumeLevel < 10) {
            volumeLevel++;
            updateAudioVolume();
        }
    }

    private void decreaseVolume() {
        if (volumeLevel > 0) {
            volumeLevel--;
            updateAudioVolume();
        }
    }

    private void updateAudioVolume() {
        getGame().getAudioPlayer().setVolume(volumeLevel);

        // giảm về 0, tự động tắt Music
        if (volumeLevel == 0 && isMusicOn) {
            isMusicOn = false; // Music sang OFF
            getGame().getAudioPlayer().mute();
        }
    }

  @Override
    public void mouseMoved(int x, int y){

    int hoverButton = -1;

    if (boundsBack.contains(x, y)) {
        hoverButton = 0;
    }
    else if (boundsMusicOn.contains(x, y)) hoverButton = 1;
    else if (boundsMusicOff.contains(x, y)) hoverButton = 2;
    else if (boundsBtnSfxOn.contains(x, y)) hoverButton = 3;
    else if (boundsBtnSfxOff.contains(x, y)) hoverButton = 4;
    else if (boundsShootSfxOn.contains(x, y)) hoverButton = 5;
    else if (boundsShootSfxOff.contains(x, y)) hoverButton = 6;
    else if (boundsVolMinus.contains(x, y)) hoverButton = 7;
    else if (boundsVolPlus.contains(x, y)) hoverButton = 8;

    if (hoverButton != -1 && hoverButton != lastHoverButton) {
        getGame().getAudioPlayer().playButtonSfx("res/audio/hover.wav");
    }

    lastHoverButton = hoverButton;
}

    private void resetButtons() {
        
    }

    @Override
    public void mouseDragged(int x, int y){ }
}