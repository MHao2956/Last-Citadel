package scenes;

import java.awt.Graphics;
import main.Game; //cho minh thang add code
import managers.TileManager;
import ui.BottomBar;
import ui.MyButton; 

public class Playing extends GameScene implements SceneMethods {

    private int[][] lvl; 
    private TileManager tileManager;
    private Tile selectedTile;
    private BottomBar bottomBar;
    private int mouseX, mouseY;
    private int lastTileX, lastTileY, lastTileId;
    private boolean drawSelect;

    public Playing(Game game) {
        super(game);

        lvl = LevelBuild.getLevelData();
        tileManager = new TileManager(); 
        bottomBar = new BottomBar(0, 640, 640, 100, this); //change setPanelSize Dimension(640, 640) -> Dimension (640, 740)
        
        //LoadSave.CreateFile();
        // LoadSave.WriteToFile();
        // LoadSave.ReadFromFile();

        createDefaultLevel();
    }

    private void createDefaultLevel(){
        int[] arr = new int[400];
        for(int i = 0; i < arr.length ; i++){
            arr[i] = 0;
        }

        LoadSave.CreateLevel("New level", arr);
    }

    @Override
    public void render(Graphics g){
        for(int y = 0; y < lvl.length; y++){
            for(int x = 0; x < lvl[y].length; x++){
                int id = lvl[y][x];
                g.drawImage(tileManager.getSprite(id), x*32, y*32, null);
            }
        }

        bottomBar.draw(g);
        drawSelectedTile(g);
    }

    private void drawSelectedTile(Graphics g){
        if(selectedTile != null && drawSelect){
            g.drawImage(selectedTile.getSprite(), mouseX, mouseY, 32, 32, null);
        }
    }

    public void setSlectedTile(Tile tile){
        this.selectedTile = tile;
        drawSelect = true;
    }

    public TileManager getTileManager(){
        return tileManager;
    }

    @Override
    public void mouseClicked(int x, int y){
        if(y >= 640){
            bottomBar.mouseClicked(x, y);
        } else {
            changeTile(mouseX, mouseY);
        }        
    }

    private void changeTile(int x, int y){

        if(selectedTile != null){
            int tileX = x/32;
            int tileY = y/32;

            if(lastTileX == tileX && lastTileY == tileY && 
                lastTileId == selectedTile.getId())
                return;

            lastTileX = tileX;
            lastTileY = tileY;
            lastTileId = selectedTile.getId();

            lvl[tileY][tileX] = selectedTile.getId();
        }
    }

    @Override
    public void mouseMoved(int x, int y){
        if(y >= 640){
            bottomBar.mouseMoved(x, y);
            drawSelect = false;
        } else {
            drawSelect = true;
            mouseX = (x/32)*32;
            mouseY = (y/32)*32;
            mouseX *= 32;
            mouseY *= 32;
        }
    }

    @Override
    public void mousePressed(int x, int y){
        if(y >= 640){
            bottomBar.mousePressed(x, y);
        }       
    }

    @Override
    public void mouseRelease(int x, int y){
        bottomBar.mouseRelease(x, y);
     
    }

    @Override
    public void mouseDragged(int x, int y){
        if(y >= 640){

        } else {
            changeTile(x, y);
        }
    }
}
