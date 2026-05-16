package managers;

import enemies.Enemy;
import enemies.ENEMY1;
import enemies.ENEMY2;
import enemies.ENEMY3;
import enemies.ENEMY4;

import static helpz.Constants.Enemies.*;

public class EnemyFactory {

    public static Enemy createEnemy(int enemyType, int x, int y, EnemyManager enemyManager) {
        switch (enemyType) {
            case ENEMY1:
                return new ENEMY1(x, y, 0, enemyManager);

            case ENEMY2:
                return new ENEMY2(x, y, 0, enemyManager);

            case ENEMY3:
                return new ENEMY3(x, y, 0, enemyManager);

            case ENEMY4:
                return new ENEMY4(x, y, 0, enemyManager);

            default:
                return null;
        }
    }
}