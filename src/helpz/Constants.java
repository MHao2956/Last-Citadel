package helpz;


public class Constants {

    public static class Projectile{
        public static final int FIRE = 0;
        public static final int ICE = 1;
        public static final int ROCKET = 2;
        public static float GetSpeed(int type){
            switch(type){
                case FIRE:
                    return 8f;
                case ROCKET:
                    return 4f;
                case ICE:
                    return 6f;
            }
            return 0f;
        }
    }
    public static class Diretion{
        public static final int LEFT = 0;
        public static final int UP= 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }


    public static class Enemies{
        public static final int ENEMY1 = 0;
        public static final int ENEMY2 = 1;
        public static final int ENEMY3 = 2;
        public static final int ENEMY4 = 3;
        public static float GetSpeed(int enemyType) {
    switch (enemyType) {
        case ENEMY1:
            return 0.6f;
        case ENEMY2:
            return 0.7f;
        case ENEMY3:
            return 0.4f;
        case ENEMY4:
            return 0.5f;
    }
    return 0;
}
        public static int GetStartHealth(int enemyType){
            switch(enemyType){
                case ENEMY1:
                    return 85;
                case ENEMY2:
                    return 100;
                case ENEMY3:
                    return 125;
                case ENEMY4:
                    return 400;
            }
            return 0;
        }
    }

    public static class Tiles{
        public static final int WATER_TILE = 0;
        public static final int GRASS_TILE = 1;
        public static final int ROAD_TILE = 2;

    }

    public static class Towers{

        public static final int ROCKET_TOWER =0;
        public static final int FIRE_TOWER =1;
        public static final int ICE_TOWER =2;

        public static String GetName(int towerType){
            switch(towerType){
                case ICE_TOWER:
                    return "ICE Tower";
                case FIRE_TOWER:
                    return "FIRE Tower";
                case ROCKET_TOWER:
                    return "ROCKET Tower";
            }
            return "";
        }  
        public static int GetStartDmg(int towerType){
            switch(towerType){
                case ICE_TOWER:
                    return 5;
                case FIRE_TOWER:
                    return 10;
                case ROCKET_TOWER:
                    return 20;
            }
            return 0;
        }
        public static float GetDefaultRange(int towerType){
            switch(towerType){
                case ICE_TOWER:
                    return 100;
                case FIRE_TOWER:
                    return 120;
                case ROCKET_TOWER:
                    return 75;
            }
            return 0;
        }
        public static float GetDefaultCooldown(int towerType){
            switch(towerType){
                case ICE_TOWER:
                    return 50;
                case FIRE_TOWER:
                    return 25;
                case ROCKET_TOWER:
                    return 70;
            }
            return 0;
        }
    }
}

