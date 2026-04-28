package scenes;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import main.Game;

public class Menu extends GameScene implements SceneMethod{
    private BufferedImage img;
    private ArrayList<BufferedImage> sprite = new ArrayList<>();
    private Random random;
    public Menu(Game game) {
        super(game);
        random = new Random();
        importImg();
        loadSprite();
    }

    @Override
    public void render(Graphics g){
        for (int y = 0; y < 20; y++){
            for (int x = 0; x < 20; x++){
                g.drawImage(sprite.get(getRndNumber()), x * 32, y * 32, null);
            }
        }
    }

    private void importImg() {
        InputStream is = getClass().getResourceAsStream("/spriteatlas.png");

        try {
            img = ImageIO.read(is);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private void loadSprite() {
        for (int y = 0; y < 3; y++){
            for (int x = 0; x < 10; x++){
                sprite.add(img.getSubimage(x * 32, y * 32, 32, 32));

            }
        }
    }

    private int getRndNumber(){
        return random.nextInt(30);
    }



}
