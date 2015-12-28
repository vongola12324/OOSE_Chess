// TODO:FinishCheck
abstract class Rule {
    public abstract boolean check(Chess[][] ChessStatus);
    public abstract void eat(Chess[][] ChessStatus);
}

class GoRule extends Rule{

    @Override
    public boolean check(final Chess[][] ChessStatus) {
        return false;
    }

    @Override
    public void eat(Chess[][] ChessStatus){
        // TODO: Eat Dot
    }
}

class GomokuRule extends Rule{

    @Override
    public boolean check(final Chess[][] ChessStatus) {
        return false;
    }

    @Override
    @Deprecated
    public void eat(Chess[][] ChessStatus) {
        // Do nothing
    }
}
