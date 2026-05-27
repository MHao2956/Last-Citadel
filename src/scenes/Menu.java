package scenes;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import helpz.AudioPlayer;
import helpz.LoadSave;
import main.Game;
import ui.MyButton;
import static main.GameStates.*;

public class Menu extends GameScene implements SceneMethods { 
    private MyButton bPlaying, bEdit, bSettings, bQuit;
    private BufferedImage bgImg;
    private boolean wasOverPlay, wasOverEdit, wasOverSettings, wasOverQuit;
    private AudioPlayer audioPlayer;

    public Menu(Game game){
        super(game);
        audioPlayer = game.getAudioPlayer();
        initButtons();

        //draw backgr img
        bgImg = LoadSave.getImage("bg.png");
    }

    private void initButtons(){
        int w = 150;
        int h = w/3;
        int x = 640/2 - w/2;
        int y = 300;
        int yOffset = 100;

        bPlaying = new MyButton("play.png", x, y, w, h);
        bEdit = new MyButton("edit.png", x, y + yOffset, w, h);
        bSettings = new MyButton("settings.png", x, y + yOffset*2, w, h);
        bQuit = new MyButton("quit.png", x, y + yOffset*3, w, h);
    }

    @Override
    public void render(Graphics g){
        g.drawImage(bgImg, 0, 0, 640, 800, null);
        drawButtons(g);
    }

    private void drawButtons(Graphics g){
        bPlaying.draw(g);
        bEdit.draw(g);
        bSettings.draw(g);
        bQuit.draw(g);
    }

    @Override
    public void mouseClicked(int x, int y){
        if(bPlaying.getBounds().contains(x, y))
            SetGameState(PLAYING);
        else if(bEdit.getBounds().contains(x, y))
            SetGameState(EDIT);
        else if (bSettings.getBounds().contains(x, y))
            SetGameState(SETTINGS);
        else if (bQuit.getBounds().contains(x, y)) {
            game.getAudioPlayer().stopMusic();
            System.exit(0);
        }
    }

    @Override 
    public void mouseMoved(int x, int y){

        boolean overPlay = bPlaying.getBounds().contains(x, y);
        boolean overEdit = bEdit.getBounds().contains(x, y);
        boolean overSettings = bSettings.getBounds().contains(x, y);
        boolean overQuit = bQuit.getBounds().contains(x, y);

        bPlaying.setMouseOver(overPlay);
        bEdit.setMouseOver(overEdit);
        bSettings.setMouseOver(overSettings);
        bQuit.setMouseOver(overQuit);

        if (overPlay && !wasOverPlay) {
            audioPlayer.playSoundEffect("res/audio/hover.wav");
        }

        if (overEdit && !wasOverEdit) {
            audioPlayer.playSoundEffect("res/audio/hover.wav");
        }

        if (overSettings && !wasOverSettings) {
            audioPlayer.playSoundEffect("res/audio/hover.wav");
        }

        if (overQuit && !wasOverQuit) {
            audioPlayer.playSoundEffect("res/audio/hover.wav");
        }

        wasOverPlay = overPlay;
        wasOverEdit = overEdit;
        wasOverSettings = overSettings;
        wasOverQuit = overQuit;
    }
    
    @Override
    public void mousePressed(int x, int y){
        if(bPlaying.getBounds().contains(x, y))
            bPlaying.setMousePressed(true);
        else if(bEdit.getBounds().contains(x, y))
            bEdit.setMousePressed(true);
        else if(bSettings.getBounds().contains(x, y))
            bSettings.setMousePressed(true);
        else if(bQuit.getBounds().contains(x, y))
            bQuit.setMousePressed(true);
    }

    @Override
    public void mouseReleased(int x, int y){
        ressetButton();
    }

    private void ressetButton(){
        bPlaying.resetBooleans();
        bSettings.resetBooleans();
        bQuit.resetBooleans();
        bEdit.resetBooleans();
    }

    @Override
    public void mouseDragged(int x, int y){
        
    }
}
