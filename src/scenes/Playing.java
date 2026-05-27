package scenes;

import java.awt.*;
import java.util.ArrayList;

import helpz.LoadSave;
import main.Game;
import managers.EnemyManager;
import managers.TowerManager;
import managers.WaveManager;
import objects.PathPoint;
import ui.ActionBar;
import objects.Tower;
import enemies.Enemy;

import java.awt.event.KeyEvent;
import static helpz.Constants.Tiles.GRASS_TILE;
import managers.ProjectileManager;
import static main.GameStates.*;

public class Playing extends GameScene implements SceneMethods {

    private int[][] lvl; 
    private ActionBar actionBar;
    private int mouseX, mouseY;
    private EnemyManager enemyManager;
    private TowerManager towerManager;
    private ProjectileManager projManager;
    private WaveManager waveManager;
    private PathPoint start, end;
    private Tower selectedTower;
    private int goldTick;
    private boolean gamePaused;

    private boolean countdownActive = true; 
    private int countdownTick = 0;
    private int countdownSeconds = 3;

    public Playing(Game game) {
        super(game);
        loadDefaultLevel();

        actionBar = new ActionBar(0, 640, 640, 160, this);
        enemyManager = new EnemyManager(this, start, end);
        towerManager = new TowerManager(this);
        projManager = new ProjectileManager(this);
        waveManager = new WaveManager(this);
    }

    private void loadDefaultLevel(){

        lvl = LoadSave.GetLevelData("new_level");
         ArrayList<PathPoint> points = LoadSave.GetLevelPathPoints("new_level");

        start = points.get(0);
        end = points.get(1);
    }

    public void setLevel(int[][] lvl, PathPoint start, PathPoint end){
        this.lvl = lvl;
        this.start = start;
        this.end = end;
        enemyManager = new EnemyManager(this, start, end);
    }
    public void update(){

        if(!gamePaused){
            if (countdownActive) {
                updateCountdown();
                return;
            }
            
            updateTick();
            waveManager.update();
            goldTick++;
            if(goldTick % (60*3)==0){
                actionBar.addGold(3);
            }
            if(isAllEnemiesDead()){
                if(isThereMoreWaves()){
                    waveManager.startWaveTimer();
                    //check timer
                    if(isWaveTimerOver()){
                        waveManager.increaseWaveIndex();
                        enemyManager.getEnemies().clear();
                        waveManager.resetEnemyIndex();
                    }
                }
                else{
                    SetGameState(GAME_WIN);
                }
            }
            if(isTimeForNewEnemy()){
                spawnEnemy();
            }

            enemyManager.update();
            towerManager.update();
            projManager.update();
        }
    }

    private void updateCountdown() {
        countdownTick++;
        if (countdownTick >= 60) {
            countdownTick = 0;
            countdownSeconds--;

            if (countdownSeconds <= 0) {
                countdownActive = false;
            }
        }
    }

    private boolean isWaveTimerOver() {
        return waveManager.isWaveTimeOver();
    }

    private boolean isThereMoreWaves() {
        return waveManager.isThereMoreWaves();
    }

    private boolean isAllEnemiesDead() {

        if(waveManager.isThereMoreEnemiesInWave()){
            return false;
        }

        for(Enemy e : enemyManager.getEnemies())
            if(e.isAlive())
                return false;

        return true;
    }

    private void spawnEnemy() {
        enemyManager.spawnEnemy(waveManager.getNextEnemy());
    }

    private boolean isTimeForNewEnemy() {
        if(waveManager.isTimeForNewEnemy()){
            if(waveManager.isThereMoreEnemiesInWave()){
                return true;
            }
        }

        return false;

    }

    public void setSelectedTower(Tower selectedTower) {
        this.selectedTower = selectedTower;
    }

    @Override
    public void render(Graphics g){

        drawLevel(g);
        actionBar.draw(g);
        enemyManager.draw(g);
        towerManager.draw(g);
        projManager.draw(g);

        drawSelectedTower(g);
        drawHighlight(g);

        if (countdownActive) {
            drawCountdown(g);
        }
    }

    private void drawCountdown(Graphics g) {
        g.setColor(new Color(0, 0, 0, 100));
        g.fillRect(0, 0, 640, 640);

        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 100));

        String text = String.valueOf(countdownSeconds);

        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();

        g.drawString(text, 640 / 2 - textWidth / 2, 640 / 2 + textHeight / 3);
    }

    private void drawHighlight(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawRect(mouseX, mouseY, 32, 32);

    }

    private void drawSelectedTower(Graphics g) {
        if (selectedTower != null)
           g.drawImage(towerManager.getTowerImgs()[selectedTower.getTowerType()], mouseX, mouseY, 32, 32, null);
    }

    private void drawWaveInfos(Graphics g) {

    }

    private void drawLevel(Graphics g){

        for (int y = 0; y < lvl.length; y++) {
            for (int x = 0; x < lvl[y].length; x++) {
                int id = lvl[y][x];
                if (isAnimation(id)) {
                    g.drawImage(getSprite(id, animationIndex), x * 32, y * 32, null);
                } else
                    g.drawImage(getSprite(id), x * 32, y * 32, null);
            }
        }
    }


    public int getTileType(int x, int y){
        int xCord = x / 32;
        int yCord = y / 32;

        if(xCord < 0 || xCord > 19)
            return 0;
        if(yCord < 0 || yCord > 19)
            return 0;


        int id = lvl[y / 32][x / 32];
        return game.getTileManager().getTile(id).getTileType();
    }


    @Override
    public void mouseClicked(int x, int y){
        if(y >= 640)
            actionBar.mouseClicked(x, y);
        else {
            // Above 640y
            if(selectedTower != null){
                if(isTileGrass(mouseX, mouseY)){
                    if(getTowerAt(mouseX, mouseY) == null){
                        towerManager.addTower(selectedTower, mouseX, mouseY);
                        removeGold(selectedTower.getTowerType());
                    selectedTower = null;
                }
                }
            } else {
                // Not trying to place a tower
                // Checking if a tower exists at x,y
                Tower t = getTowerAt(mouseX, mouseY);
                actionBar.displayTower(t);
            }
        }
    }
    private void removeGold(int towerType) {
        actionBar.payForTower(towerType);

    }

  public void upgradeTower(Tower displayedTower) {
        towerManager.upgradeTower(displayedTower);
  }

   public void removeTower(Tower displayedTower) {
        towerManager.removeTower(displayedTower);
   }

    private Tower getTowerAt(int x, int y) {
        return towerManager.getTowerAt(x, y);
    }

    private boolean isTileGrass(int x,int y) {
        int id = lvl[y / 32][x / 32];
        int tileType = game.getTileManager().getTile(id).getTileType();
        return tileType == GRASS_TILE;
    }

    public void shootEnemy(Tower t, Enemy e) {
        projManager.newProjectile(t, e);
    }

    public void setGamePaused(boolean gamePaused) {
        this.gamePaused = gamePaused;
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            selectedTower = null;
        }
    }

    @Override
    public void mouseMoved(int x, int y){
        if(y >= 640)
            actionBar.mouseMoved(x, y);
        else {
            mouseX = (x/32)*32;
            mouseY = (y/32)*32;
        }
    }

    @Override
    public void mousePressed(int x, int y){
        if(y >= 640){
            actionBar.mousePressed(x, y);
        }
    }
    @Override
    public void mouseReleased(int x, int y){
        actionBar.mouseReleased(x, y);

    }

    @Override
    public void mouseDragged(int x, int y){

    }
    public void rewardPlayer(int reward){
        actionBar.addGold(helpz.Constants.Enemies.GetReward(reward));
    }
    public boolean isGamePaused(){
        return gamePaused;
    }

    public WaveManager getWaveManager() {
        return waveManager;
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    public TowerManager getTowerManager() {
        return towerManager;
    }


    public void removeOneLife() {
        actionBar.removeOneLives();
    }

    public void resetEverything() {
        actionBar.resetEverything();

        //managers
        enemyManager.reset();
        towerManager.reset();
        projManager.reset();
        waveManager.reset();

        mouseX = 0;
        mouseY = 0;

        selectedTower = null;
        goldTick = 0;
        gamePaused = false;

        countdownActive = true;
        countdownSeconds = 3;
        countdownTick = 0;
    }

}
