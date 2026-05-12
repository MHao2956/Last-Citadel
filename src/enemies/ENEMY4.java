package enemies;

import static helpz.Constants.Enemies.ENEMY4;
import managers.EnemyManager;

public class ENEMY4 extends Enemy{
    public ENEMY4(float x, float y, int ID, EnemyManager enemyManager){
        super(x, y, ID, ENEMY4, enemyManager);
    }
}
