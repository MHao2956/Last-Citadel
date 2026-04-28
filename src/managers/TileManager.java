package managers;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import helpz.ImgFix;
import helpz.LoadSave;
import objects.Tile;

public class TileManager {
    public Tile GRASS, WATER, ROAD, BR_WATER_CORNER;
    public BufferedImage atlas;
    public ArrayList<Tile> tiles = new ArrayList<>();

    public TileManager(){
        loadAtlas();
        createTiles();
    }

    private void createTiles(){

        int id = 0;
        tiles.add(GRASS = new Tile(getSprite(9, 0), id++, "GRASS")); //lay cord cua grass(X, Y)
        tiles.add(WATER = new Tile(getSprite(0, 0), id++, "WATER")); //lay cord cua WATER(X, Y)
        tiles.add(ROAD = new Tile(getSprite(8, 0), id++, "ROAD")); //lay cord cua ROAD(X, Y)
        tiles.add(BR_WATER_CORNER = new Tile(ImgFix.buildImg(getImgs(0, 0, 5, 0)), id++, "BR_WATER_CORNER"));
    }

    private BufferedImage[] getImgs(int firstX, int firstY, int secondX, int secondY){
        return new BufferedImage[]{getSprite(firstX, firstY), getSprite(secondX, secondY)};
    }

    private void loadAtlas(){
        atlas = LoadSave.getSpriteAtlas();
    }

    public Tile getTile(int id){
        return tiles.get(id);
    }

    public BufferedImage getSprite(int id){
        return tiles.get(id).getSprite();
    }

    private BufferedImage getSprite(int xCord, int yCord){
        return atlas.getSubimage(xCord*32,yCord*32,32,32);
    }
}
