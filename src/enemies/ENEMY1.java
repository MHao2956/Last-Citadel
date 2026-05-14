package enemies;

import static helpz.Constants.Enemies.ENEMY1;
import managers.EnemyManager;

public class ENEMY1 extends Enemy{
    public ENEMY1(float x, float y, int ID, EnemyManager enemyManager){
        super(x, y, ID, ENEMY1, enemyManager);
    }
}
