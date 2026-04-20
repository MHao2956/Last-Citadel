package main;

import javax.swing.JFrame;

public class Game extends JFrame {
    private GameScreen GS;
    public Game(){
        setSize(400,400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        GS = new GameScreen();
        add(GS);
        setVisible(true);
    }
    public static void main (String[] args){
        Game g = new Game();
    }
}