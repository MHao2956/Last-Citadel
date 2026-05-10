package objects;
public class Tower{
    private int x, y, id, towerType,cdTick,damage;
    private float range, cooldown;
    public Tower(int x, int y, int id, int towerType) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.towerType = towerType;
        setDefaultDamage();
        setDefaultRange();
        setDefaultCooldown();
    }
    public void update(){
        cdTick++;
    }
    public boolean isCooldownOver(){
        return cdTick >= cooldown;
    }
    public void resetCooldown(){
        cdTick = 0;
    }
    private void setDefaultDamage() {
      damage= helpz.Constants.Towers.GetStartDamage(towerType);
    }
    private void setDefaultRange() {
        range= helpz.Constants.Towers.GetStartRange(towerType);
    }
    private void setDefaultCooldown() {
        cooldown= helpz.Constants.Towers.GetStartCooldown(towerType);
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
    public int getDamage() {
        return damage;
    }
    public float getRange() {
        return range;
    }
    public float getCooldown() {
        return cooldown;
    }

}