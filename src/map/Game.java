package map;

import javax.swing.JFrame;

public class Game extends JFrame{
    private GameScreen gameScreen;
    public Game(){
        setSize(640,640);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        gameScreen = new GameScreen();
        add(gameScreen);
    }

    public static void main (String[] args){
       Game game = new Game(); 
    }
}
