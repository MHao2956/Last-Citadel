package enemies;

import static helpz.Constants.Enemies.ENEMY2;
import managers.EnemyManager;

public class ENEMY2 extends Enemy{
    public ENEMY2(float x, float y, int ID, EnemyManager enemyManager){
        super(x, y, ID, ENEMY2, enemyManager);
    }
}
