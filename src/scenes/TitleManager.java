package scenes;

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
}
