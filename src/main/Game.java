package main;

import managers.TileManager;
import scenes.*;

import javax.swing.JFrame;

import helpz.LoadSave;

import helpz.AudioPlayer;


public class Game extends JFrame implements Runnable {

    private GameScreen gameScreen;
    private Thread gameThread;

    private final double FPS_SET = 120.0;
    private final double UPS_SET = 60.0;

    private Render render;
    private Menu menu;
    private Playing playing;
    private Settings settings;
    private Editing editing;

    private TileManager tileManager;
    private GameOver gameOver;

    private AudioPlayer audioPlayer;
    private GameStates previousGameState;

    public Game(){

        LoadSave.CreateFolder();

        createDefaultLevel();
        initClasses();


        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Last-Citadel");
        add(gameScreen);
        pack();
        setVisible(true);

        updateMusic();
    }

    

    private void createDefaultLevel(){
        int[] arr = new int[400];
        for(int i = 0; i < arr.length ; i++)
            arr[i] = 0;

        LoadSave.CreateLevel("new_level", arr);
    }

    private void initClasses(){
        audioPlayer = new AudioPlayer();

        tileManager = new TileManager(); 
        render = new Render(this);
        gameScreen = new GameScreen(this);
        menu = new Menu(this);
        playing = new Playing(this);
        settings = new Settings(this);
        editing = new Editing(this);
        gameOver = new GameOver(this);

        audioPlayer = new AudioPlayer();
    }

    private void start(){
        gameThread = new Thread(this){};

        gameThread.start();
    }

    private void updateGame() {


        switch (GameStates.gameState) {
		case EDIT:
			editing.update();
			break;
		case MENU:
			break;
		case PLAYING:
			playing.update();
			break;
		case SETTINGS:
			break;
		default:
			break;
		}
    }

    private void updateMusic() {

    if (previousGameState == GameStates.gameState) {
        return;
    }

    previousGameState = GameStates.gameState;

    switch (GameStates.gameState) {

        case MENU:
            audioPlayer.playMusic("res/audio/menu.wav");
            break;

        case PLAYING:
            audioPlayer.playMusic("res/audio/battle.wav");
            break;
        case GAME_OVER:
            audioPlayer.playMusic("res/audio/gameOver.wav");
            break;
        case EDIT:
             audioPlayer.playMusic("res/audio/menu.wav");
            break;
        case SETTINGS:
             audioPlayer.playMusic("res/audio/menu.wav");
            break;
        

        default:
            audioPlayer.stopMusic();
            break;
    }
}

    public static void main(String[] args){
        Game game = new Game();
        game.gameScreen.initInputs();
        game.start();
    }

    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;

        long lastFrame = System.nanoTime();
        long lastUpdate = System.nanoTime();
        long lastTimeCheck = System.currentTimeMillis();

        int frames = 0;
        int updates = 0;

        long now;

        while(true){
            //render
            now = System.nanoTime();
            if(now - lastFrame >= timePerFrame){
                repaint();
                lastFrame = now;
                frames++;
            }
            //updates
            if(now - lastUpdate >= timePerUpdate){
                updateGame();
                updateMusic();

                lastUpdate = now;
                updates++;
            }

            if (System.currentTimeMillis() - lastTimeCheck >= 1000){
                System.out.println("FPS: " +  frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
                lastTimeCheck = System.currentTimeMillis();
            }

        }
    }

    //getters and setters
    public Render getRender(){
        return render;
    }

    public Menu getMenu(){
        return menu;
    }

    public Playing getPlaying(){
        return playing;
    }

    public Settings getSettings(){
        return settings;
    }

    public Editing getEditor(){
        return editing;
    }

    public TileManager getTileManager(){
        return tileManager;
    }

    public GameOver getGameOver() {
        return gameOver;
    }

    public AudioPlayer getAudioPlayer() {
    return audioPlayer;
}

}