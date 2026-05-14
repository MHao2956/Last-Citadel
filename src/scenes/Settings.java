package scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import main.Game;
import ui.MyButton;

import static main.GameStates.*;

public class Settings extends GameScene implements SceneMethods {

    private MyButton bMenu; //  nút BACK

    // Hitbox Bật / Tắt
    private Rectangle boundsMusicOn, boundsMusicOff;
    private Rectangle boundsBtnSfxOn, boundsBtnSfxOff;
    private Rectangle boundsShootSfxOn, boundsShootSfxOff;

    // Hitbox Tăng / Giảm âm lượng
    private Rectangle boundsVolMinus, boundsVolPlus;

    private boolean isMusicOn = true;
    private boolean isBtnSfxOn = true;
    private boolean isShootSfxOn = true;
    private int volumeLevel = 5;

    public Settings(Game game){
        super(game);
        initButtons();
        if (game.getAudioPlayer() != null) {
            this.isMusicOn = !game.getAudioPlayer().isMuted();
            this.volumeLevel = game.getAudioPlayer().getVolumeLevel();
        }
    }

    private void initButtons() {
        bMenu = new MyButton("BACK", 260, 520, 120, 40);

        // Music
        boundsMusicOn = new Rectangle(280, 170, 60, 30);
        boundsMusicOff = new Rectangle(350, 170, 60, 30);

        //  Button SFX
        boundsBtnSfxOn = new Rectangle(280, 220, 60, 30);
        boundsBtnSfxOff = new Rectangle(350, 220, 60, 30);

        //Shoot SFX
        boundsShootSfxOn = new Rectangle(280, 270, 60, 30);
        boundsShootSfxOff = new Rectangle(350, 270, 60, 30);

        //nút [-] và [+]
        boundsVolMinus = new Rectangle(240, 360, 50, 30);
        boundsVolPlus = new Rectangle(370, 360, 50, 30);
    }

    @Override
    public void render(Graphics g){
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, 640, 640);

        //Bảng Menu Settings
        g.setColor(new Color(74, 54, 35));
        g.fillRect(120, 50, 400, 540);
        g.setColor(new Color(133, 94, 66));
        g.drawRect(120, 50, 400, 540);

        //Tiêu đề
        g.setFont(new Font("Serif", Font.BOLD, 26));
        g.setColor(new Color(245, 222, 179));
        g.drawString("SETTINGS", 250, 90);

        g.setFont(new Font("Serif", Font.PLAIN, 22));
        g.drawString("AUDIO", 285, 130);

        // ON / OFF
        g.setFont(new Font("Serif", Font.PLAIN, 20));
        g.drawString("Music", 160, 192);
        drawSelectButton(g, boundsMusicOn, "ON", isMusicOn);
        drawSelectButton(g, boundsMusicOff, "OFF", !isMusicOn);

        g.setColor(new Color(245, 222, 179));
        g.drawString("Button SFX", 160, 242);
        drawSelectButton(g, boundsBtnSfxOn, "ON", isBtnSfxOn);
        drawSelectButton(g, boundsBtnSfxOff, "OFF", !isBtnSfxOn);

        g.setColor(new Color(245, 222, 179));
        g.drawString("Shoot SFX", 160, 292);
        drawSelectButton(g, boundsShootSfxOn, "ON", isShootSfxOn);
        drawSelectButton(g, boundsShootSfxOff, "OFF", !isShootSfxOn);

        g.setColor(new Color(245, 222, 179));
        g.drawString("Volume", 290, 340);

        drawVolButton(g, boundsVolMinus, "[-]");
        drawVolButton(g, boundsVolPlus, "[+]");

        // hiển thị số Volume ở giữa
        g.setColor(new Color(40, 30, 20));
        g.fillRect(300, 360, 60, 30);
        g.setColor(new Color(133, 94, 66));
        g.drawRect(300, 360, 60, 30);

        // âm lượng (0 - 10)
        g.setColor(new Color(245, 222, 179));
        g.drawString(String.valueOf(volumeLevel), 325, 382);

        bMenu.draw(g);
    }

    // nút ON/OFF
    private void drawSelectButton(Graphics g, Rectangle bounds, String text, boolean isSelected) {
        if (isSelected) g.setColor(new Color(60, 120, 120));
        else g.setColor(new Color(40, 30, 20));
        g.fillRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, 10, 10);

        if (isSelected) g.setColor(Color.CYAN);             // Viền sáng
        else g.setColor(new Color(133, 94, 66));            // Viền tối
        g.drawRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, 10, 10);

        g.setColor(new Color(245, 222, 179));
        g.setFont(new Font("Serif", Font.PLAIN, 16));
        g.drawString(text, bounds.x + 15, bounds.y + 20);
    }

    // Tăng/Giảm âm lượng
    private void drawVolButton(Graphics g, Rectangle bounds, String text) {
        g.setColor(new Color(40, 30, 20));
        g.fillRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, 10, 10);

        g.setColor(Color.CYAN);
        g.drawRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, 10, 10);

        g.setColor(new Color(245, 222, 179));
        g.setFont(new Font("Serif", Font.BOLD, 18));
        g.drawString(text, bounds.x + 12, bounds.y + 22);
    }

    @Override
    public void mouseClicked(int x, int y){ }

    @Override
    public void mousePressed(int x, int y){
        if (bMenu.getBounds().contains(x, y))
            bMenu.setMousePressed(true);
    }

    @Override
    public void mouseReleased(int x, int y){
        if (bMenu.getBounds().contains(x, y)) {
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
        bMenu.setMouseOver(false);
        if (bMenu.getBounds().contains(x, y))
            bMenu.setMouseOver(true);
    }

    private void resetButtons() {
        bMenu.resetBooleans();
    }

    @Override
    public void mouseDragged(int x, int y){ }
}