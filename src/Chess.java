
public class Chess {
    Location nowLoc;
    Integer color;
    Integer step;

    public Chess(Integer color){
        this.setColor(color);
    }

    public void setNowLoc(){

    }

    public Location getNowLoc(){
        return new Location(this.nowLoc);
    }

    public void setColor(Integer color){
        this.color = color;
    }

    public void setStep(){

    }

    public Integer getStep(){
        return this.step;
    }

}

class Location{
    private Integer x, y;

    public Location(Integer x, Integer y){
        this.x = new Integer(x);
        this.y = new Integer(y);
    }

    public Location(Location loc){
        this.x = loc.getX();
        this.y = loc.getY();
    }

    public Integer getX(){
        return this.x;
    }

    public Integer getY(){
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }


    @Override
    public String toString(){
        return "(" + this.getX() + ", " + this.getY() + ")";
    }
}

class ChessFactory{
    public Chess makeChess(Integer color){
        return new Chess(color);
    }
}