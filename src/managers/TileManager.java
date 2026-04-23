package managers;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import helpz.LoadSave;
import objects.Tile;

public class TileManager {
    public Tile GRASS, WATER, ROAD;
    public BufferedImage atlas;
    public ArrayList<Tile> tiles = new ArrayList<>();

    public TileManager(){
        loadAtlas();
        createTiles();
    }

    private void createTiles(){

        int id = 0;
        tiles.add(GRASS = new Tile(getSprite(8, 1), id++, "GRASS")); //lay cord cua grass(X, Y)
        tiles.add(WATER = new Tile(getSprite(0, 6), id++, "WATER")); //lay cord cua WATER(X, Y)
        tiles.add(ROAD = new Tile(getSprite(9, 0), id++, "ROAD")); //lay cord cua ROAD(X, Y)
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
