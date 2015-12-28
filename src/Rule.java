abstract class Rule {
    public abstract short check(Chess[][] ChessStatus);

}

interface Eatable {
    public void eat(Chess[][] ChessStatus);
}

class GoRule extends Rule implements Eatable {

    @Override
    public short check(final Chess[][] ChessStatus) {
        // TODO: GO/FinishCheck
        return Const.NO_WIN;
    }

    @Override
    public void eat(Chess[][] ChessStatus) {
        // TODO: Eat Dot
    }
}

class GomokuRule extends Rule {
    // Fixme: Change to short and return who win
    @Override
    public short check(final Chess[][] ChessStatus) {
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
}
