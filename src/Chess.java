
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

    public Integer getColor(){
        MainViewController getColor = new MainViewController();
        return  getColor.isNowBlackOrWhite();//0 black,1 white  this is in MainViewController
    }

    public void setStep(){

    }

    public Integer getStep(){
        return this.step;
    }

}

class Location{
    Integer x, y;

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