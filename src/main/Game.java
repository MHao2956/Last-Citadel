package main;

import inputs.KeyboardListener;
import inputs.MyMouseListener;
import scenes.Menu;
import scenes.Playing;
import scenes.Settings;
import main.Game;

import javax.swing.JFrame;
import java.util.Set;

public class Game extends JFrame implements Runnable {

    public GameScreen gameScreen;
    private Thread gameThread;

    private final double FPS_SET = 120.0;
    private final double UPS_SET = 60.0;

    private MyMouseListener myMouseListener;
    private KeyboardListener keyboardListener;

    //Classes
    private Render render;
    private Menu menu;
    private Playing playing;
    private Settings settings;

    public Game(){

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initClasses();


        add(gameScreen);

        pack();
        setVisible(true);
    }

    private void initClasses() {
        render = new Render(this);
        gameScreen = new GameScreen(this);
        menu = new Menu(this);
        playing = new Playing(this);
        settings = new Settings(this);
    }

    private void initInput(){
        myMouseListener = new MyMouseListener();
        keyboardListener = new KeyboardListener();

        addMouseListener(myMouseListener);
        addMouseMotionListener(myMouseListener);
        addKeyListener(keyboardListener);

        requestFocus();
    }



    private void start(){
        gameThread = new Thread(this){};

        gameThread.start();
    }

    private void updateGame() {
        //System.out.println("Game Updated!");
    }

    public static void main (String[] args){
        Game game = new Game();
        game.initInput();
        game.start();
    }

    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;

        long lastFrame = System.nanoTime();
        long lastTimeCheck = System.currentTimeMillis();

        long lastUpdate = System.nanoTime();

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

    //Getter and Setter
    public Render getRender() {
        return render;
    }


    public Menu getMenu() {
        return menu;
    }


    public Playing getPlaying() {
        return playing;
    }


    public Settings getSettings() {
        return settings;
    }

}