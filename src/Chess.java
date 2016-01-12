public abstract class Chess{
    private Location loc;

    public Chess(){

    };

    public Chess(Location loc) {
        this.setLoc(loc);
    }

    public Chess(Chess c) {
        this.setLoc(c.getLoc());
    }

    public void setLoc(Location loc) {
        this.loc = new Location(loc);
    }

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

    @Deprecated
    private Integer step;

    public BWChess(short color, Location loc) {
        super(loc);
        this.setColor(color);
        this.setLoc(loc);
    }

    public BWChess(Chess c) {
        super(c.getLoc());
        if (c instanceof BWChess) {
            this.color = ((BWChess) c).getColor();
        }
    }

    public void setColor(short color) {
        this.color = color;
    }

    public short getColor() {
        return this.color;
    }

    @Deprecated
    public void setStep(int step) {
        this.step = step;
    }

    @Deprecated
    public Integer getStep() {
        return this.step;
    }

    @Override
    public String toString() {
        return "BWChess: " + (this.getColor() == Const.BLACK_CHESS ? "Black Chess" : "White Chess") + " at " + this.getLoc();
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

class BWChessFactory extends Factory {
    public Chess makeChess(short color, Location loc) {
        return this.make(color, loc);
    }

    @Override
    public Chess make(Object... obj) {
        return new BWChess((Short) obj[0], (Location) obj[1]);
    }
}