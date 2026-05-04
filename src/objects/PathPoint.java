package objects;

public class PathPoint {
    private int xCord, yCord;

    public PathPoint(int xCord, int yCord){
        this.xCord = xCord;
        this.yCord = yCord;
    }

    public void setxCord(int xCord){
        this.xCord = xCord;
    }
    public int getxCord(){
        return xCord;
    }

    public void setyCord(int yCord){
        this.yCord = yCord;
    }
    public int getyCord(){
        return yCord;
    }


}
