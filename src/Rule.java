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
        int Row_len = ChessStatus.length, Col_len = ChessStatus[0].length, i, j, CheckLocX, CheckLocY;
        int[][] Mapping = new int[Row_len][Col_len], CanBeEat = new int[Row_len][Col_len], Stack = new int[Row_len][Col_len];
        int[][][] CheckPoint = new int[Row_len][Col_len][4];
        //CheckPoint Comment
        //Up 0 -> Left 1 -> Down 2 -> Right 3
        //NotThing 0 , Same Color 1, Diff Color 2, NeverGo 3

        boolean EatAble = false;

        ArrayIniter(ChessStatus, Mapping, CanBeEat, Stack, CheckPoint, Row_len, Col_len);//init array

        //Mapping
        for (i = 0; i < Row_len; i++) {
            for (j = 0; j < Col_len; j++) {
                CheckLocX = i;
                CheckLocY = j;
                if (Mapping[i][j] != 0) {
                    //==================================================================================//
                    //CheckPoint Begin//
                    if (i + 1 > Row_len) {//check if has wall
                        CheckPoint[i][j][1] = 2;
                    } else if (i - 1 < 0) {
                        CheckPoint[i][j][4] = 2;
                    } else if (j + 1 > Col_len) {
                        CheckPoint[i][j][0] = 2;
                    } else if (j - 1 < 0) {
                        CheckPoint[i][j][3] = 2;
                        /////////////////////////////////////////////////////////////////////////
                    } else if (Mapping[i][j] != Mapping[i][j + 1] && Mapping[i][j + 1] != 0) {//check if Diff Color
                        CheckPoint[i][j][0] = 2;
                    } else if (Mapping[i][j] != Mapping[i + 1][j] && Mapping[i + 1][j] != 0) {
                        CheckPoint[i][j][1] = 2;
                    } else if (Mapping[i][j] != Mapping[i][j - 1] && Mapping[i][j - 1] != 0) {
                        CheckPoint[i][j][2] = 2;
                    } else if (Mapping[i][j] != Mapping[i - 1][j] && Mapping[i - 1][j] != 0) {
                        CheckPoint[i][j][3] = 2;
                        //////////////////////////////////////////////////////////////////////////
                    } else if (Mapping[i][j] == Mapping[i][j + 1]) {//check if Same Color
                        CheckPoint[i][j][0] = 1;
                    } else if (Mapping[i][j] != Mapping[i + 1][j]) {
                        CheckPoint[i][j][1] = 1;
                    } else if (Mapping[i][j] != Mapping[i][j - 1]) {
                        CheckPoint[i][j][2] = 1;
                    } else if (Mapping[i][j] != Mapping[i - 1][j]) {
                        CheckPoint[i][j][3] = 1;
                        ///////////////////////////////////////////////////////////////////////////
                    } else if (Mapping[i][j + 1] == 0) {//check if Nothing here
                        CheckPoint[i][j][0] = 0;
                        EatAble = false;
                    } else if (Mapping[i + 1][j] == 0) {
                        CheckPoint[i][j][1] = 0;
                        EatAble = false;
                    } else if (Mapping[i][j - 1] == 0) {
                        CheckPoint[i][j][2] = 0;
                        EatAble = false;
                    } else if (Mapping[i - 1][j] == 0) {
                        CheckPoint[i][j][3] = 0;
                        EatAble = false;
                        ///////////////////////////////////////////////////////////////////////////
                    }
                    //CheckPoint End//
                    //==================================================================================//
                }
            }
        }

    }

    public static void ArrayIniter(Chess[][] ChessStatus, int[][] Mapping, int[][] CanBeEat, int[][] Stack, int[][][] CheckPoint, int Row_len, int Col_len) {
        for (int i = 0; i < Row_len; i++) {
            for (int j = 0; j < Col_len; j++) {
                if (ChessStatus[i][j] == null) {
                    Mapping[i][j] = Const.NO_CHESS;
                } else {
                    Mapping[i][j] = ChessStatus[i][j].color;
                }
                CanBeEat[i][j] = 0;
                Stack[i][j] = 0;
            }
        }
        for (int[][] elementI : CheckPoint) {
            for (int[] elementJ : elementI) {
                for (int elementK : elementJ) {
                    elementK = 3;
                }
            }
        }
    }
}

class GomokuRule extends Rule {
    // Fixme: Change to short and return who win
    @Override
    public short check(final Chess[][] ChessStatus) {
        int Row_len = ChessStatus.length, Col_len = ChessStatus[0].length, i, j;
        short CheckResault = Const.NO_WIN;
        Chess CheckChess;
        for (i = 0; i < Row_len; i++) {
            for (j = 0; j < Col_len; j++) {
                CheckChess = ChessStatus[i][j];
                if (ChessStatus[i][j] != null) {
                    if (CheckResault == Const.NO_WIN) {
                        CheckResault = LeftLastCheck(ChessStatus, CheckChess);
                    }
                    if (CheckResault == Const.NO_WIN) {
                        CheckResault = DownLastCheck(ChessStatus, CheckChess);
                    }
                    if (CheckResault == Const.NO_WIN) {
                        CheckResault = LeftDownLastCheck(ChessStatus, CheckChess);
                    }
                    if (CheckResault == Const.NO_WIN) {
                        CheckResault = LeftUpLastCheck(ChessStatus, CheckChess);
                    }
                    if (CheckResault == Const.NO_WIN) {
                        CheckResault = TieCheck(ChessStatus, CheckChess);
                    }
                }
            }
        }
        if (CheckResault != Const.NO_WIN) {
            return CheckResault;
        }
        return Const.NO_WIN;
    }

    public short DownLastCheck(final Chess[][] ChessStatus, Chess CheckChess) {
        int Row_len = ChessStatus.length, LocX = CheckChess.getNowLocX(), LocY = CheckChess.getNowLocY();
        int CheckPoint = LocY;
        int CheckResault = 0;
        while (CheckPoint < Row_len && CheckPoint < LocY + 6 && ChessStatus[LocX][CheckPoint] != null) {
            if (CheckChess.color == ChessStatus[LocX][CheckPoint].color) {
                CheckResault++;
            }
            CheckPoint++;
        }
        if (CheckResault >= 5) {
            if (CheckChess.color == Const.WHITE_CHESS) {
                return Const.WHITE_WIN;
            } else {
                return Const.BLACK_WIN;
            }
        }
        return Const.NO_WIN;
    }

    public short LeftLastCheck(final Chess[][] ChessStatus, Chess CheckChess) {
        int Col_len = ChessStatus[0].length, LocX = CheckChess.getNowLocX(), LocY = CheckChess.getNowLocY();
        int CheckPoint = LocX;
        int CheckResault = 0;
        while (CheckPoint < Col_len && CheckPoint < LocX + 6 && ChessStatus[CheckPoint][LocY] != null) {
            if (CheckChess.color == ChessStatus[CheckPoint][LocY].color) {
                CheckResault++;
            }
            CheckPoint++;
        }
        if (CheckResault >= 5) {
            if (CheckChess.color == Const.WHITE_CHESS) {
                return Const.WHITE_WIN;
            } else {
                return Const.BLACK_WIN;
            }
        }
        return Const.NO_WIN;
    }

    public short LeftDownLastCheck(final Chess[][] ChessStatus, Chess CheckChess) {
        int Row_len = ChessStatus.length, Col_len = ChessStatus[0].length, LocX = CheckChess.getNowLocX(), LocY = CheckChess.getNowLocY();
        int CheckPointX = LocX, CheckPointY = LocY;
        int CheckResault = 0;
        while (CheckPointX < Row_len && CheckPointY < Col_len && CheckPointX < LocX + 6 && CheckPointY < LocY + 6 && ChessStatus[CheckPointX][CheckPointY] != null) {
            if (CheckChess.color == ChessStatus[CheckPointX][CheckPointY].color) {
                CheckResault++;
            }
            CheckPointX++;
            CheckPointY++;
        }
        if (CheckResault >= 5) {
            if (CheckChess.color == Const.WHITE_CHESS) {
                return Const.WHITE_WIN;
            } else {
                return Const.BLACK_WIN;
            }
        }
        return Const.NO_WIN;
    }

    public short LeftUpLastCheck(final Chess[][] ChessStatus, Chess CheckChess) {
        int Row_len = ChessStatus.length, Col_len = ChessStatus[0].length, LocX = CheckChess.getNowLocX(), LocY = CheckChess.getNowLocY();
        int CheckPointX = LocX, CheckPointY = LocY;
        int CheckResault = 0;
        while (CheckPointX < Row_len && CheckPointY > Col_len && CheckPointX < LocX + 6 && CheckPointY < LocY + 6 && ChessStatus[CheckPointX][CheckPointY] != null) {
            if (CheckChess.color == ChessStatus[CheckPointX][CheckPointY].color) {
                CheckResault++;
            }
            CheckPointX++;
            CheckPointY--;
        }
        if (CheckResault >= 5) {
            if (CheckChess.color == Const.WHITE_CHESS) {
                return Const.WHITE_WIN;
            } else {
                return Const.BLACK_WIN;
            }
        }
        return Const.NO_WIN;
    }

    public short TieCheck(final Chess[][] ChessStatus, Chess CheckChess) {
        int Row_len = ChessStatus.length, Col_len = ChessStatus[0].length, LocX = CheckChess.getNowLocX(), LocY = CheckChess.getNowLocY();
        if (Row_len == LocX && Col_len == LocY) {
            return Const.TIE;
        }
        return Const.NO_WIN;
    }
}
