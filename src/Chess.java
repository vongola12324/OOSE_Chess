public abstract class Chess{
    private Location loc;

    public Chess(Location loc) {
        this.setLoc(loc);
    }

    public void setLoc(Location loc) {
        this.loc = new Location(loc);
    }

    @Deprecated
    public Location getLoc() {
        return new Location(this.loc);
    }

    public int getNowLocX() {
        return this.loc.getX();
    }

    public int getNowLocY() {
        return this.loc.getY();
    }

}

class BWChess extends Chess{
    private short color;
    private Integer step;

    public BWChess(short color, Location loc, int step) {
        super(loc);
        this.setColor(color);
        this.setLoc(loc);
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
        return this.make(color, loc, step);
    }

    @Override
    public Chess make(Object... obj) {
        return new BWChess((Short) obj[0], (Location) obj[1], (Integer) obj[2]);
    }
}