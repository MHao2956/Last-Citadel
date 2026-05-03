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

import objects.PathPoint;

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

            WriteToFile(newLevel, idArr, new PathPoint(0, 0), new PathPoint(0, 0));

        }
    }

    private static void WriteToFile(File f, int[] idArr, PathPoint start, PathPoint end){
        
        try {
            PrintWriter pw = new PrintWriter(f);
            for(Integer i : idArr)
                pw.println(i);
            pw.println(start.getxCord());
            pw.println(start.getyCord());
            pw.println(end.getxCord());
            pw.println(start.getyCord());

            pw.close();

        } catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public static void SaveLevel(String name, int[][] idArr, PathPoint start, PathPoint end){
        File levelFile = new File("res/" + name + ".txt");

        if(levelFile.exists()){
            WriteToFile(levelFile, Utilz.TwoDto1DintArr(idArr), start, end);
        } else {
            System.out.println("File" + name + "does not exists! ");
            return;
        }
    }

    private static ArrayList<Integer> ReadFromFile(File f){
        ArrayList<Integer> list = new ArrayList<>();

        try {
            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
                list.add(Integer.parseInt(sc.nextLine()));
            }
            sc.close();

        } catch(FileNotFoundException e){
            e.printStackTrace();
        }

        return list;
    }

    public static ArrayList<PathPoint> GetLevelPathPoints(String name){
         File lvlFile = new File("res/"+ name +".txt");
          if(lvlFile.exists()){
            ArrayList<Integer> list = ReadFromFile(lvlFile);
            ArrayList<PathPoint> points = new ArrayList<>();
            points.add(new PathPoint(list.get(400), list.get(401)));
            points.add(new PathPoint(list.get(402), list.get(403)));

            return points;
        } else {
            System.out.println("File" + name + "does not exists! ");
            return null;
        }
    }

    public static int[][] GetLevelData(String name){
        File levelFile = new File("res/" + name + ".txt");

        if(levelFile.exists()){
            ArrayList<Integer> list = ReadFromFile(levelFile);
            return Utilz.ArrayListTo2Dint(list, 20, 20);
        } else {
            System.out.println("File" + lvlFile + "does not exists! ");
            return null;
        }
        
    }
}
