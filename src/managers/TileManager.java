package managers;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class TitleManager {
    public Tile GRASS, WATER, ROAD;
    public BufferedImage atlas;
    public ArrayList<Tile> tiles = new ArrayList<>();

    public TitleManager(){
        loadAtlas();
        createTiles();
    }

    private void createTiles(){
        tiles.add(GRASS = new Tile(getSprite(x, y))); //lay cord cua grass(X, Y)
        tiles.add(WATER = new Tile(getSprite(x, y))); //lay cord cua WATER(X, Y)
        tiles.add(ROAD = new Tile(getSprite(x, y))); //lay cord cua ROAD(X, Y)
    }

    private void loadAtlas(){
        atlas = LoadSave.getSpriteAtlas();
    }

    public BufferedImage getSprite(int id){
        return tiles.get(id).getSprite();
    }

    private BufferedImage getSprite(int xCord, int yCord){
        return atlas.getSubimage(xCord*32,yCord*32,32,32);
    }
}
