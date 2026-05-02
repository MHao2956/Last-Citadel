package helpz;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class LoadSave {

    public static String homePath = System.getProperty("user.home");
	public static String saveFolder = "TDTutorial";
	public static String levelFile = "level.txt";
	public static String filePath = homePath + File.separator + saveFolder + File.separator + levelFile;
	private static File lvlFile = new File(filePath);

	public static void CreateFolder() {
		File folder = new File(homePath + File.separator + saveFolder);
		if (!folder.exists())
			folder.mkdir();
	}
    public static BufferedImage getSpriteAtlas() {
    BufferedImage img = null;

    try {
        img = ImageIO.read(new File("res/spriteatlas.png"));
    } catch (IOException e) {
        e.printStackTrace();
    }

    return img;
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

    public static void SaveLevel(String name, int[][] idArr){
        File levelFile = new File("res/" + name + ".txt");

        if(levelFile.exists()){
            WriteToFile(levelFile, Utilz.TwoDto1DintArr(idArr));
        } else {
            System.out.println("File" + name + "does not exists! ");
            return;
        }
    }

    private static ArrayList<Integer> ReadFromFile(File file){
        ArrayList<Integer> list = new ArrayList<>();

        try {
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            list.add(Integer.parseInt(sc.nextLine()));
        }
        sc.close();

        } catch(FileNotFoundException e){
            e.printStackTrace();
        }

        return list;
    }

    public static int[][] GetLevelData(String name){
        File lvlFile = new File("res/"+ name +".txt");

        if(lvlFile.exists()){
            ArrayList<Integer> list = ReadFromFile(lvlFile);
            return Utilz.ArrayListTo2Dint(list, 20, 20);
        } else {
            System.out.println("File" + name + "does not exists! ");
            return null;
        }
        
    }
}
