package ui;

import static main.GameStates.MENU;
import static main.GameStates.SetGameState;

import java.awt.*;
import java.text.DecimalFormat;

import scenes.Playing;

public class ActionBar extends Bar{
    
    private Playing playing;
    private MyButton bMenu, bPause;

    private DecimalFormat formatter;

    public ActionBar(int x, int y, int width, int height, Playing playing){
        super(x, y, width, height);
        this.playing = playing;
        formatter = new DecimalFormat("0.0");

        initButtons();
    }
    
    private void initButtons(){
        bMenu = new MyButton("Menu", 2, 642, 100, 30);
        bPause = new MyButton("Pause", 2, 682, 100, 30);
    }

    private void drawButtons(Graphics g){
        bMenu.draw(g);
        bPause.draw(g);
    }

    public void draw(Graphics g){
        g.setColor(new Color(220, 123, 15));
        g.fillRect(x, y, width, height);

        drawButtons(g);
        
        //Wave info
        drawWaveInfo(g);

        //Game paused text
        if(playing.isGamePaused()){
            g.setColor(Color.black);
            g.drawString("Game is Paused!", 110, 790);
        }
    }

    private void drawWaveInfo(Graphics g) {
        drawWaveTimerInfo(g);
        drawEnemiesLeftInfo(g);
        drawWavesLeftInfo(g);
    }

    private void drawWavesLeftInfo(Graphics g) {
        int current = playing.getWaveManager().getWaveIndex();
        int size = playing.getWaveManager().getWaves().size();
        g.drawString("Wave " + (current + 1) + " / " + size, 425, 690);
    }

    private void drawEnemiesLeftInfo(Graphics g) {
        int remaining = playing.getEnemyManager().getAmountOfAliveEnemies();
        g.drawString("Enemies Left: " + remaining, 425, 720);
    }

    private void drawWaveTimerInfo(Graphics g){
        if(playing.getWaveManager().isWaveTimerStarted()){
            g.setFont(new Font("LucidaSans", Font.BOLD, 20));
            g.setColor(Color.black);
            float timeLeft = playing.getWaveManager().getTimeLeft();
            String formatedText = formatter.format(timeLeft);

            g.drawString("Time Left: " + formatedText, 425, 660);
        }

    }

    private void togglePause() {

        if(playing.isGamePaused())
            bPause.setText("Unpause");
        else
            bPause.setText("Pause");
        playing.setGamePaused(!playing.isGamePaused());
    }

    public void mouseClicked(int x, int y){
        if(bMenu.getBounds().contains(x, y))
            SetGameState(MENU);
        else if (bPause.getBounds().contains(x, y))
            togglePause();
    }



    public void mouseMoved(int x, int y){
        bMenu.setMouseOver(false);
        bPause.setMouseOver(false);
        if(bMenu.getBounds().contains(x, y))
            bMenu.setMouseOver(true);
        else if(bPause.getBounds().contains(x, y))
            bPause.setMouseOver(true);
    }

    public void mousePressed(int x, int y){
        if(bMenu.getBounds().contains(x, y))
            bMenu.setMousePressed(true);
        else if(bPause.getBounds().contains(x, y))
            bPause.setMousePressed(true);
    }

    public void mouseReleased(int x, int y){
        bMenu.resetBooleans();
        bPause.resetBooleans();
    }

}
