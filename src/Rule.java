// TODO:FinishCheck
abstract class Rule {
    public abstract boolean check(Chess[][] ChessStatus);

    public abstract void eat(Chess[][] ChessStatus);
}

class GoRule extends Rule {

    @Override
    public boolean check(final Chess[][] ChessStatus) {
        return false;
    }

    @Override
    public void eat(Chess[][] ChessStatus) {
        // TODO: Eat Dot
    }
}

class GomokuRule extends Rule {

    @Override
    public boolean check(final Chess[][] ChessStatus) {
        int Row_len = ChessStatus.length, Col_len = ChessStatus[0].length;

        for (int i = 0; i < Row_len; i++) {
            for (int j = i; j < Col_len; j++) {
                if (ChessStatus[i][j] != null) {
                    int CheckPoint = i, CheckLocX = 1, CheckLocY = 1;
                    boolean CheckResault = false;
                    short nowColor = ChessStatus[i][j].getColor();

                    while (CheckPoint + 1 < Col_len && CheckPoint < j + 4) {
                        if (ChessStatus[i][CheckPoint].getColor() != nowColor) {
                            CheckResault = false;
                            break;
                        } else {
                            CheckResault = true;
                        }
                        CheckPoint++;
                    }
                    if (CheckResault == true) {
                        return true;
                    }
                    CheckPoint = i;
                    while (CheckPoint + 1 < Row_len && CheckPoint < i + 4) {
                        if (ChessStatus[CheckPoint][j].getColor() != nowColor) {
                            CheckResault = false;
                            break;
                        } else {
                            CheckResault = true;
                        }
                        CheckPoint++;
                    }
                    if (CheckResault == true) {
                        return true;
                    }
                    CheckLocX = 1;
                    CheckLocY = 1;
                    while (CheckLocX + i < Row_len && CheckLocY + j < CheckLocY) {
                        if (ChessStatus[CheckLocX + i][CheckLocY + j].getColor() != nowColor) {
                            CheckResault = false;
                            break;
                        } else {
                            CheckResault = true;
                        }
                        if (CheckLocX < 5) {
                            CheckLocX++;
                            CheckLocY++;
                        }
                    }
                    if (CheckResault == true) {
                        return true;
                    }
                    CheckLocX = 1;
                    CheckLocY = 1;
                    while (CheckLocX + i < Row_len && CheckLocY + j < CheckLocY) {
                        if (ChessStatus[CheckLocX + i][j - CheckLocY].getColor() != nowColor) {
                            CheckResault = false;
                            break;
                        } else {
                            CheckResault = true;
                        }
                        if (CheckLocX < 5) {
                            CheckLocX++;
                            CheckLocY++;
                        }
                    }
                    if (CheckResault == true) {
                        return true;
                    }else {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    @Override
    @Deprecated
    public void eat(Chess[][] ChessStatus) {
        // Do nothing
    }
}
