package enemies;

import static helpz.Constants.Enemies.ENEMY2;

public class ENEMY2 extends Enemy{
    public ENEMY2(float x, float y, int ID){
        super(x, y, ID, ENEMY2);
        setStartHealth();
    }
}
