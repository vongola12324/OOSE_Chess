// TODO:FinishCheck
abstract class Rule {
    public abstract boolean check();
}

class GoRule extends Rule{

    @Override
    public boolean check() {
        return false;
    }
}

class GomokuRule extends Rule{

    @Override
    public boolean check() {
        return false;
    }
}
