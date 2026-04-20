package objects;

public class tower {
    
    private int x, y, id, towerType;


    public tower(int x, int y, int id, int towerType) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.towerType = towerType;
    }
    public int getX() { return x; }
    public int getY() { return y; }

}
