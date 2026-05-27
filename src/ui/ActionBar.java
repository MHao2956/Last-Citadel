package ui;

import java.awt.*;
import java.text.DecimalFormat;

import helpz.Constants.Towers;
import objects.Tower;
import scenes.Playing;
import static helpz.Constants.Towers;
import static main.GameStates.*;

public class ActionBar extends Bar{
    
    private Playing playing;
    private MyButton bMenu, bPause;
    private DecimalFormat formatter;
    private int gold = 100;
    private boolean showTowerCost;
    private int towerCostType;
    private MyButton[] towerButtons ;
    private Tower selectedTower; ;
    private Tower displayedTower;

    private MyButton sellTower, upgradeTower;

    private int lives = 5;

    public ActionBar(int x, int y, int width, int height, Playing playing){
        super(x, y, width, height);
        this.playing = playing;
        formatter = new DecimalFormat("0.0");

        initButtons();
    }

    public void resetEverything() {
        lives = 5;
        towerCostType = 0;
        showTowerCost = false;
        gold = 100;
        selectedTower = null;
        displayedTower = null;
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
        sellTower = new MyButton("Sell", 440, 700, 50, 30);
        upgradeTower = new MyButton("Upgrade", 560, 700, 50, 30);
    }

    public void removeOneLives(){
        lives--;
        if(lives <= 0)
            SetGameState(GAME_OVER);
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

            //game paused text
            if(playing.isGamePaused()){
                g.setColor(Color.black);
                g.drawString("Game is Paused!", 110, 790);
            }

            drawDisplayedTower(g);

            drawWaveInfo(g);

            drawGoldAmount(g);

            if(showTowerCost)
                drawTowerCost(g);

            //Lives
            g.setColor(Color.black);
            g.drawString("Lives: " + lives, 110, 750);
     }
  

    private void drawDisplayedTower(Graphics g){
        if(displayedTower!=null){
            g.setColor(Color.GRAY);
            g.fillRect(380, 650, 250, 80);
            g.drawImage(playing.getTowerManager().getTowerImgs()[displayedTower.getTowerType()],380,650,50,50,null);
            g.setColor(Color.BLACK);
            g.drawRect(380, 650, 50, 50);
            g.drawRect(380, 650, 250, 80);
            g.drawString("" + Towers.GetName(displayedTower.getTowerType()), 440, 665);
            g.drawString("ID: " + displayedTower.getId(), 440, 680);
            g.drawString("Tier: " + displayedTower.getTier(), 440, 695);
            drawDisplayedTowerBorder(g);
            drawDisplayedTowerRange(g);

            //Sell button
            sellTower.draw(g);
            drawButtonFeedback(g,sellTower);
            if (displayedTower.getTier() < 3 && gold >= getUpgradeAmount(displayedTower)){
                 upgradeTower.draw(g);
            drawButtonFeedback(g,upgradeTower);
            }
           
            if(sellTower.isMouseOver()){
                g.setColor(Color.RED);
                g.drawString("Sell for: " + getSellAmount(displayedTower) + " g", 440, 740);
            }else if(upgradeTower.isMouseOver() && gold >= getUpgradeAmount(displayedTower)){
                g.setColor(Color.GREEN);
                g.drawString("Upgrade for: " + getUpgradeAmount(displayedTower) + " g", 440, 740);
            }
               
        }
    }
    private int getUpgradeAmount(Tower displayedTower2) {
        return (int)(helpz.Constants.Towers.GetTowerCost(displayedTower.getTowerType()) * 0.3f);
    }
    private int getSellAmount(Tower displayedTower2) {
        int upgradeCost = (displayedTower.getTier()-1) * getUpgradeAmount(displayedTower);
        upgradeCost*=0.5f;
        return helpz.Constants.Towers.GetTowerCost(displayedTower.getTowerType()) / 2+ upgradeCost;
    }
    private void upgradeTowerClicked() {
        playing.upgradeTower(displayedTower);
        gold-=getUpgradeAmount(displayedTower);
    }
    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x, y))
            SetGameState(MENU);
        else if (bPause.getBounds().contains(x, y))
            togglePause();
        else {
            if(displayedTower!=null){
                if(sellTower.getBounds().contains(x, y)){
                    sellTowerClicked();
                    return;
                } else if(upgradeTower.getBounds().contains(x, y) && displayedTower.getTier() < 3 && gold >= getUpgradeAmount(displayedTower)){
                    upgradeTowerClicked();
                    return;
                }
        }
            for (MyButton b : towerButtons) {
                if (b.getBounds().contains(x, y)) {
                    if(!isGoldEnoughForTower(b.getId())){
                        return;
                    }
                    selectedTower = new Tower(0, 0, -1, b.getId());
                    playing.setSelectedTower(selectedTower);
                    return;
                }
            }
        }
    }

    private void sellTowerClicked() {
        playing.removeTower(displayedTower);
        gold+=helpz.Constants.Towers.GetTowerCost(displayedTower.getTowerType())/2;
        int upgradeCost = (displayedTower.getTier()-1) * getUpgradeAmount(displayedTower);
        upgradeCost*=0.5f;
        gold+=upgradeCost;
        displayedTower=null;
    }

        private boolean isGoldEnoughForTower(int towerType) {
            return gold >= helpz.Constants.Towers.GetTowerCost(towerType);
        }
    public void mouseMoved(int x, int y){
        bMenu.setMouseOver(false);
        bPause.setMouseOver(false);
        sellTower.setMouseOver(false);
        upgradeTower.setMouseOver(false);
        showTowerCost=false;
        for (MyButton b : towerButtons)
            b.setMouseOver(false);

        if(bMenu.getBounds().contains(x, y))
            bMenu.setMouseOver(true);
        else if(bPause.getBounds().contains(x, y))
            bPause.setMouseOver(true);
        else {
            if(displayedTower!=null){
                if(sellTower.getBounds().contains(x, y)){
                    sellTower.setMouseOver(true);
                    return;
                } else if(upgradeTower.getBounds().contains(x, y) && displayedTower.getTier() < 3){
                    upgradeTower.setMouseOver(true);
                    return;
                }
            }

            for (MyButton b : towerButtons)
                if(b.getBounds().contains(x, y)){
                    b.setMouseOver(true);
                    showTowerCost=true;
                    towerCostType=b.getId();
                return;
        }
    }
    }
    public void mousePressed(int x, int y){
        if(bMenu.getBounds().contains(x, y))
            bMenu.setMousePressed(true);
        else if(bPause.getBounds().contains(x, y))
            bPause.setMousePressed(true);

        else if(displayedTower!=null){
            if(sellTower.getBounds().contains(x, y)){
                sellTower.setMousePressed(true);
                return;
            } else if(upgradeTower.getBounds().contains(x, y) && displayedTower.getTier() < 3){
                upgradeTower.setMousePressed(true);
                return;
            }
        }

            for (MyButton b : towerButtons)
                if(b.getBounds().contains(x, y)){
                    b.setMousePressed(true);
                    return;

        }
    }

    public void mouseReleased(int x, int y){
        bMenu.resetBooleans();
        bPause.resetBooleans();
        for (MyButton b : towerButtons) {
            b.resetBooleans();
            sellTower.resetBooleans();
            upgradeTower.resetBooleans();
        }
    }
    public void payForTower(int towerType){
        gold -= Towers.GetTowerCost(towerType);
    }
    public void drawDisplayedTowerRange(Graphics g){
        g.setColor(Color.white);
        g.drawOval(displayedTower.getX() + 16 - ((int)displayedTower.getRange()*2)/2, 
                    displayedTower.getY() + 16 - ((int)displayedTower.getRange()*2)/2, 
                    (int)displayedTower.getRange()*2, (int)displayedTower.getRange()*2);
    }
    public void drawDisplayedTowerBorder(Graphics g){
        g.setColor(Color.CYAN);
        g.drawRect(displayedTower.getX(), displayedTower.getY(), 32, 32);
    }
    public void displayTower(Tower t){
        displayedTower=t;
    }
 
    private void drawTowerCost(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(280, 650, 95, 50);
        g.setColor(Color.BLACK);
        g.drawRect(280, 650, 95, 50);
        g.drawString(""+getTowerCostName(), 285, 675);
        g.drawString(""+getTowerCostCost(), 285, 690);
        
        if(isTowerCostMoreThanCurrentGold()){
            g.setColor(Color.RED);
            g.drawString("Not enough gold!", 285, 720);
        }
    }
    private boolean isTowerCostMoreThanCurrentGold() {
        return getTowerCostCost() > gold;
    }
    private String getTowerCostName() {
        return helpz.Constants.Towers.GetName(towerCostType);
    }
    private int getTowerCostCost() {
        return helpz.Constants.Towers.GetTowerCost(towerCostType);
    }
    private void drawGoldAmount(Graphics g) {
        g.drawString("Gold: " + gold, 110, 720);
    }
    private void drawWaveInfo(Graphics g) {
         g.setColor(Color.black);
        drawWaveTimerInfo(g);
        drawEnemiesLeftInfo(g);
        drawWavesLeftInfo(g);
    }

    private void drawWavesLeftInfo(Graphics g) {
        int current = playing.getWaveManager().getWaveIndex();
        int size = playing.getWaveManager().getWaves().size();
        g.drawString("Wave " + (current + 1) + " / " + size, 425, 760);
    }

    private void drawEnemiesLeftInfo(Graphics g) {
        int remaining = playing.getEnemyManager().getAmountOfAliveEnemies();
        g.drawString("Enemies Left: " + remaining, 425, 780);
    }

    private void drawWaveTimerInfo(Graphics g){
        if(playing.getWaveManager().isWaveTimerStarted()){
            g.setFont(new Font("LucidaSans", Font.BOLD, 20));
            float timeLeft = playing.getWaveManager().getTimeLeft();
            String formatedText = formatter.format(timeLeft);

            g.drawString("Time Left: " + formatedText, 425, 720);
        }
    }

    private void togglePause() {

        if(playing.isGamePaused())
            bPause.setText("Pause");
        else
            bPause.setText("UnPause");
        playing.setGamePaused(!playing.isGamePaused());
    }




    public void addGold(int getReward) {
        this.gold += getReward;
    }

    public int getLives() {
        return lives;
    }


}
