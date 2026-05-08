package enemies;

import static helpz.Constants.Enemies.ENEMY1;

public class ENEMY1 extends Enemy{
    public ENEMY1(float x, float y, int ID){
        super(x, y, ID, ENEMY1);
        setStartHealth();
    }
}
