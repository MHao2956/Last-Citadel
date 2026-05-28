package objects;
import static helpz.Constants.Towers.*;
public class Tower{
    private int x, y, id, towerType,cdTick,damage;
    private float range, cooldown;
    private int tier;
    private final int MAX_TIER = 3;
    public Tower(int x, int y, int id, int towerType) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.towerType = towerType;
        tier=1;
        setDefaultDamage();
        setDefaultRange();
        setDefaultCooldown();
    }
    public void update(){
        cdTick++;
    }
    public void upgradeTower(){
            if(tier >= MAX_TIER)
        return;

        this.tier++;
        switch (towerType) {
            case ICE_TOWER :
                damage += 2;
                range += 20;
                cooldown -= 5;
                break;  
            case FIRE_TOWER : 
                damage += 2;
                range += 10;
                cooldown -= 2;
                break;
            case ROCKET_TOWER :
                damage += 5;
                range += 20;
                cooldown -= 5;
            break;
               }
    }
    public boolean isCooldownOver(){
        return cdTick >= cooldown;
    }
    public void resetCooldown(){
        cdTick = 0;
    }
    private void setDefaultDamage() {
      damage= helpz.Constants.Towers.GetStartDmg(towerType);
    }
    private void setDefaultRange() {
        range= helpz.Constants.Towers.GetDefaultRange(towerType);
    }
    private void setDefaultCooldown() {
        cooldown= helpz.Constants.Towers.GetDefaultCooldown(towerType);
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getTowerType() {
        return towerType;
    }
    public void setTowerType(int towerType) {
        this.towerType = towerType;
    }
    public int getDmg() {
        return damage;
    }
    public float getRange() {
        return range;
    }
    public float getCooldown() {
        return cooldown;
    }

    public int getTier() {
        return tier;
    }
    public boolean isMaxTier(){
    return tier >= MAX_TIER;
}

}