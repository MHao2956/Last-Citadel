package scenes;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class LoadSave {
    public static BufferedImage getSpriteAtlas(){
        
        BufferedImage img = null;
        InputStream is = LoadSave.class.getClassLoader().getResourceAsStream("spriteatlas.png"); //"spriteatlas.png" la ten file source materials

        try{
            img = ImageIO.read(is);
        } catch (IOException e){
            e.printStackTrace();
        }

        return img;
    }

    //txt file
    public static void CreateFile(){
        File txtFile = new File("res/testTextFile.txt");
        try {
            txtFile.createNewFile();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void CreateLevel(String name, int[] idArr){
        File newLevel = new File("res/" + name + ".txt");
        if(newLevel.exists()){
            System.out.println("File: " + name + " already exists!");
            return;
        } else {
            try{
                newLevel.createNewFile();
            } catch(IOException e){
                e.printStackTrace();
            }
            WriteToFile(newLevel, idArr);

        }
    }

    private static void WriteToFile(File f, int[] idArr){
        
        try {
            PrintWriter pw = new PrintWriter(f);
            for(Integer i : idArr)
                pw.println(i);

            pw.close();

        } catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public static void ReadFromFile(){
        File txtFile = new File("res/text.txt");
        try {
        Scanner sc = new Scanner(txtFile);
        while (sc.hasNextLine()) {
            System.out.println(sc.nextLine());
        }
        sc.close();

        } catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }


}
