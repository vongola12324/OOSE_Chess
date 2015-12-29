
public class Chess {
    private Location loc;
    private short color;
    private Integer step;

    public Chess(short color, Location loc, int step) {
        this.setColor(color);
        this.setLoc(loc);
    }

    public void setLoc(Location loc) {
        this.loc = new Location(loc);
    }

    public Location getLoc() {
        return new Location(this.loc);
    }


    @Deprecated
    public int getNowLocX() {
        return this.loc.getX();
    }

    @Deprecated
    public int getNowLocY() {
        return this.loc.getY();
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
        this.x = x;
        this.y = y;
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

// Abstract Factory
abstract class Factory {
    abstract Object make(Object... obj);
}

class ChessFactory extends Factory {
    public Chess makeChess(short color, Location loc, int step) {
        this.make(color, loc, step);
    }

    @Override
    public Chess make(Object... obj) {
        return new Chess((Short) obj[0], (Location) obj[1], (Integer) obj[2]);
    }
}