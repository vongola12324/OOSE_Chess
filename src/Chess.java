
public class Chess {
    private Location nowLoc;
    private short color;
    private Integer step;

    public Chess(short color, Location loc, int step) {
        this.setColor(color);
        this.setNowLoc(loc);
    }

    public void setNowLoc(Location loc) {
        this.nowLoc = new Location(loc);
    }

    public Location getNowLoc() {
        return new Location(this.nowLoc);
    }

    public int getNowLocX() {
        return this.nowLoc.getX();
    }

    public int getNowLocY() {
        return this.nowLoc.getY();
    }

    public void setColor(short color) {
        this.color = color;
    }

    public short getColor() {
        return this.color;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public Integer getStep() {
        return this.step;
    }

}

class Location {
    private Integer x, y;

    public Location(Integer x, Integer y) {
        this.x = new Integer(x);
        this.y = new Integer(y);
    }

    public Location(Location loc) {
        this.x = loc.getX();
        this.y = loc.getY();
    }

    public Integer getX() {
        return this.x;
    }

    public Integer getY() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }


    @Override
    public String toString() {
        return "(" + this.getX() + ", " + this.getY() + ")";
    }
}

class ChessFactory {
    public Chess makeChess(short color, Location loc, int step) {
        return new Chess(color, loc, step);
    }

}