package scenes;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
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
}
