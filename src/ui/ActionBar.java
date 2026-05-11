package ui;

import static main.GameStates.MENU;
import static main.GameStates.SetGameState;

import java.awt.*;
import java.text.DecimalFormat;
import java.awt.Color;
import java.awt.Graphics;

import objects.Tower;
import scenes.Playing;
import static helpz.Constants.Towers;

public class ActionBar extends Bar{
    
    private Playing playing;
    private MyButton bMenu, bPause;
    private DecimalFormat formatter;
    private int gold = 500;
    private MyButton[] towerButtons ;
    private Tower selectedTower; ;
    private Tower displayedTower;

    private MyButton sellTower, upgradeTower;

    public ActionBar(int x, int y, int width, int height, Playing playing){
        super(x, y, width, height);
        this.playing = playing;
        formatter = new DecimalFormat("0.0");

        initButtons();
    }
    
    private void initButtons(){
        bMenu = new MyButton("Menu", 2, 642, 100, 30);
        bPause = new MyButton("Pause", 2, 682, 100, 30);
        towerButtons = new MyButton[3];
        int w = 50;
        int h = 50;
        int xStart = 110;
        int yStart = 650;
        int xOffset = (int)(w * 1.1f);
        for(int i = 0; i < towerButtons.length; i++){
            towerButtons[i] = new MyButton("" ,xStart + i * xOffset, yStart, w, h,i);
        }

        //sellTower, upgradeTower
        sellTower = new MyButton("Sell", 490, 675, 50, 30);
        upgradeTower = new MyButton("Upgrade", 570, 675, 50, 30);
    }

    private void drawButtons(Graphics g){
        bMenu.draw(g);
        bPause.draw(g);
        for (MyButton b : towerButtons) {
            g.setColor(Color.GRAY);
            g.fillRect(b.x, b.y, b.width, b.height);
            g.drawImage(playing.getTowerManager().getTowerImgs()[b.getId()], b.x, b.y, b.width, b.height, null);
            drawButtonFeedback(g, b);
        }
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

        drawDisplayedTower(g);
    }

    private void drawDisplayedTower(Graphics g){
        if(displayedTower!=null){
            g.setColor(Color.GRAY);
            g.fillRect(350, 650, 200, 100);
            g.drawImage(playing.getTowerManager().getTowerImgs()[displayedTower.getTowerType()],350,650,50,50,null);
            g.setColor(Color.BLACK);
            g.drawRect(350, 650, 50, 50);
            g.drawRect(350, 650, 200, 100);
            g.drawString("" + Towers.GetName(displayedTower.getTowerType()), 410, 675);
            g.drawString("ID: " + displayedTower.getId(), 410, 690);
            drawDisplayedTowerBorder(g);
            drawDisplayedTowerRange(g);

            //Sell button
            sellTower.draw(g);
            upgradeTower.draw(g);
        }
    }

    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x, y))
            SetGameState(MENU);
        else if (bPause.getBounds().contains(x, y))
            togglePause();
        else {
            for (MyButton b : towerButtons) {
                if (b.getBounds().contains(x, y)) {
                    selectedTower = new Tower(0, 0, -1, b.getId());
                    playing.setSelectedTower(selectedTower);
                    return;
                }
            }
        }
    }

    public void mouseMoved(int x, int y){
        bMenu.setMouseOver(false);
        bPause.setMouseOver(false);
        sellTower.setMouseOver(false);
        upgradeTower.setMouseOver(false);

        for (MyButton b : towerButtons)
            b.setMouseOver(false);
        if(bMenu.getBounds().contains(x, y))
            bMenu.setMouseOver(true);
        else if(bPause.getBounds().contains(x, y))
            bPause.setMouseOver(true);
        else { for (MyButton b : towerButtons)
            if(b.getBounds().contains(x, y))
                b.setMouseOver(true);
            return;
        }
    }

    public void mousePressed(int x, int y){
        if(bMenu.getBounds().contains(x, y))
            bMenu.setMousePressed(true);
        else if(bPause.getBounds().contains(x, y))
            bPause.setMousePressed(true);
        else { for (MyButton b : towerButtons)
            if(b.getBounds().contains(x, y)){
                b.setMousePressed(true);
                return;
            }
        }
    }

    public void mouseReleased(int x, int y){
        bMenu.resetBooleans();
        bPause.resetBooleans();
        for (MyButton b : towerButtons) {
            b.resetBooleans();
        }
    }
    public void drawDisplayedTowerRange(Graphics g){
        g.setColor(Color.white);
        g.drawOval(displayedTower.getX() + 16 - ((int)displayedTower.getRange()*2)/2, displayedTower.getY() + 16 - ((int)displayedTower.getRange()*2)/2, (int)displayedTower.getRange()*2, (int)displayedTower.getRange()*2);
    }
    public void drawDisplayedTowerBorder(Graphics g){
        g.setColor(Color.CYAN);
        g.drawRect(displayedTower.getX(), displayedTower.getY(), 32, 32);
    }
    public void displayTower(Tower t){
        displayedTower=t;
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




    public void addGold(int getReward) {
        this.gold += getReward;
    }
}
