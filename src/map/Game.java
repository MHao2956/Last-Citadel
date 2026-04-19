package map;

import javax.swing.JFrame;

public class Game extends JFrame{
    public Game(){
        setSize(640,640);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main (String[] args){
       System.out.println("Hi");
       Game game = new Game(); 
    }
}
