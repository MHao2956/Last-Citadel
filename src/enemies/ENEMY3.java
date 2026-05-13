package enemies;

import static helpz.Constants.Enemies.ENEMY3;
import managers.EnemyManager;

public class ENEMY3 extends Enemy{
    public ENEMY3(float x, float y, int ID, EnemyManager enemyManager){
        super(x, y, ID, ENEMY3, enemyManager);
    }
}
