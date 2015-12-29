import com.sun.rowset.internal.Row;

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
        int Row_len = ChessStatus.length, Col_len = ChessStatus[0].length;
        int WhiteColorCounter = 0, BlackColorCounter = 0;
        for (int i = 0; i < Row_len; i++) {
            for (int j = 0; j < Col_len; j++) {
                if (ChessStatus[i][j] != null) {
                    short nowColor = ChessStatus[i][j].getColor();
                    if (nowColor == Const.WHITE_CHESS) {
                        WhiteColorCounter++;
                    } else {
                        BlackColorCounter++;
                    }
                }
            }
        }
        if (WhiteColorCounter >= 177 || BlackColorCounter >= 183) {
            if (WhiteColorCounter >= 177 && BlackColorCounter < 183) {
                return Const.WHITE_WIN;
            } else if (BlackColorCounter >= 183 && WhiteColorCounter < 177) {
                return Const.BLACK_WIN;
            }
        } else if (WhiteColorCounter > BlackColorCounter - 6) {
            return Const.WHITE_WIN;
        } else if (BlackColorCounter > WhiteColorCounter + 6) {
            return Const.BLACK_WIN;
        } else {
            return Const.TIE;
        }
        return Const.NO_WIN;
    }

    @Override
    public void eat(Chess[][] ChessStatus) {
        // TODO: Eat Dot
        int Row_len = ChessStatus.length, Col_len = ChessStatus[0].length, i, j, CheckLocX, CheckLocY, Mark = 0;
        int[][] Mapping = new int[Row_len][Col_len], CanBeEat = new int[Row_len][Col_len], HasGone = new int[Row_len][Col_len];
        int[][][] CheckPoint = new int[Row_len][Col_len][4], Stack = new int[Row_len][Col_len][2], CanGo = new int[Row_len][Col_len][4];
        //CheckPoint Comment
        //Order: Up  -> Left  -> Down  -> Right

        boolean EatAble = false;

        ArrayIniter(ChessStatus, Mapping, CanBeEat, HasGone, Stack, CheckPoint, CanGo, Row_len, Col_len);//init array

        //Mapping
        for (i = 0; i < Row_len; i++) {
            for (j = 0; j < Col_len; j++) {
                CheckLocX = i;
                CheckLocY = j;
                if (Mapping[i][j] != Const.NO_CHESS) {

                    Stack[i][j][Const.LOCX] = Const.STARTPOS;
                    Stack[i][j][Const.LOCY] = Const.STARTPOS;//start positon init
                    HasGone[i][j] = Const.HASGONE;
                    while (Stack[i][j][Const.LOCX] == Const.NOITEM && Stack[i][j][Const.LOCY] == Const.NOITEM) {
                        Mark = Checker(CheckPoint, Mapping, CheckLocX, CheckLocY);//check
                        if (Mark == 1)//if check Nothing
                            break;
                        //if checked, go next, and stack position
                        //first, search where can go
                        for (int CheckCanGo = Const.UP; CheckCanGo < Const.LEFT; CheckCanGo++) {//check where can go
                            if (CheckCanGo == Const.UP) {
                                if (CheckPoint[CheckLocX][CheckLocY][CheckCanGo] == Const.SAME_COLOR && HasGone[CheckLocX][CheckLocY + 1] == Const.HAVENGONE)
                                    CanGo[CheckLocX][CheckLocY][CheckCanGo] = Const.CANGO;
                            }
                            if (CheckCanGo == Const.RIGHT) {
                                if (CheckPoint[CheckLocX][CheckLocY][CheckCanGo] == Const.SAME_COLOR && HasGone[CheckLocX + 1][CheckLocY] == Const.HAVENGONE)
                                    CanGo[CheckLocX][CheckLocY][CheckCanGo] = Const.CANGO;
                            }
                            if (CheckCanGo == Const.DOWN) {
                                if (CheckPoint[CheckLocX][CheckLocY][CheckCanGo] == Const.SAME_COLOR && HasGone[CheckLocX][CheckLocY - 1] == Const.HAVENGONE)
                                    CanGo[CheckLocX][CheckLocY][CheckCanGo] = Const.CANGO;
                            }
                            if (CheckCanGo == Const.LEFT) {
                                if (CheckPoint[CheckLocX][CheckLocY][CheckCanGo] == Const.SAME_COLOR && HasGone[CheckLocX - 1][CheckLocY] == Const.HAVENGONE)
                                    CanGo[CheckLocX][CheckLocY][CheckCanGo] = Const.CANGO;
                            }
                        }

                        for (int Way_to_go = Const.UP; Way_to_go < Const.LEFT; Way_to_go++) {//goto next point
                            if (CheckPoint[CheckLocX][CheckLocY][Way_to_go] == Const.SAME_COLOR) {//if same color
                                if (Way_to_go == Const.UP) {
                                    CheckLocY++;//move
                                    if (HasGone[CheckLocX][CheckLocY] == Const.HAVENGONE) {// if never gone
                                        HasGone[CheckLocX][CheckLocY] = Const.HASGONE;//now is gone
                                        CanGo[CheckLocX][CheckLocY - 1][Way_to_go] = Const.CANNOTGO;//now cannot go
                                        CanBeEat[CheckLocX][CheckLocY - 1] = Const.CANBEEAT;//Mark Eatable Buff
                                        Stack[CheckLocX][CheckLocY][0] = CheckLocX;//Stack LocX Record
                                        Stack[CheckLocX][CheckLocY][1] = CheckLocY - 1;//Stack LocY Record
                                        break;//don't do others
                                    } else
                                        CheckLocY--;//return move back
                                } else if (Way_to_go == Const.RIGHT) {
                                    CheckLocX++;
                                    if (HasGone[CheckLocX][CheckLocY] == Const.HAVENGONE) {
                                        HasGone[CheckLocX][CheckLocY] = Const.HASGONE;
                                        CanGo[CheckLocX - 1][CheckLocY][Way_to_go] = Const.CANNOTGO;
                                        CanBeEat[CheckLocX - 1][CheckLocY] = Const.CANBEEAT;
                                        Stack[CheckLocX][CheckLocY][0] = CheckLocX - 1;
                                        Stack[CheckLocX][CheckLocY][1] = CheckLocY;
                                        break;
                                    } else
                                        CheckLocX++;
                                } else if (Way_to_go == Const.DOWN) {
                                    CheckLocY--;
                                    if (HasGone[CheckLocX][CheckLocY] == Const.HAVENGONE) {
                                        HasGone[CheckLocX][CheckLocY] = Const.HASGONE;
                                        CanGo[CheckLocX][CheckLocY + 1][Way_to_go] = Const.CANNOTGO;
                                        CanBeEat[CheckLocX][CheckLocY + 1] = Const.CANBEEAT;
                                        Stack[CheckLocX][CheckLocY][0] = CheckLocX;
                                        Stack[CheckLocX][CheckLocY][1] = CheckLocY + 1;
                                        break;
                                    } else
                                        CheckLocY++;
                                } else {//Const.LEFT
                                    CheckLocX--;
                                    if (HasGone[CheckLocX][CheckLocY] == Const.HAVENGONE) {
                                        HasGone[CheckLocX][CheckLocY] = Const.HASGONE;
                                        CanGo[CheckLocX + 1][CheckLocY][Way_to_go] = Const.CANNOTGO;
                                        CanBeEat[CheckLocX + 1][CheckLocY] = Const.CANBEEAT;
                                        Stack[CheckLocX][CheckLocY][0] = CheckLocX + 1;
                                        Stack[CheckLocX][CheckLocY][1] = CheckLocY;
                                        break;
                                    } else
                                        CheckLocX--;
                                }
                            }
                        }//first step end

                        //Second, if no where to go, back to nearst position which  has CanGo == 1
                        if (CanGo[CheckLocX][CheckLocY][Const.UP] == Const.CANNOTGO && CanGo[CheckLocX][CheckLocY][Const.RIGHT] == Const.CANNOTGO
                                && CanGo[CheckLocX][CheckLocY][Const.DOWN] == Const.CANNOTGO && CanGo[CheckLocX][CheckLocY][Const.LEFT] == Const.CANNOTGO) {

                            while (CanGo[CheckLocX][CheckLocY][Const.UP] == Const.CANNOTGO || CanGo[CheckLocX][CheckLocY][Const.RIGHT] == Const.CANNOTGO
                                    || CanGo[CheckLocX][CheckLocY][Const.DOWN] == Const.CANNOTGO || CanGo[CheckLocX][CheckLocY][Const.LEFT] == Const.CANNOTGO) {
                                int StackLocX = Stack[CheckLocX][CheckLocY][Const.LOCX], StackLocY = Stack[CheckLocX][CheckLocY][Const.LOCY];
                                if (CheckLocX == i && CheckLocY == j) {
                                    if (CanGo[CheckLocX][CheckLocY][Const.UP] == Const.CANNOTGO && CanGo[CheckLocX][CheckLocY][Const.RIGHT] == Const.CANNOTGO
                                            && CanGo[CheckLocX][CheckLocY][Const.DOWN] == Const.CANNOTGO && CanGo[CheckLocX][CheckLocY][Const.LEFT] == Const.CANNOTGO) {
                                        Stack[CheckLocX][CheckLocY][Const.LOCY] = Const.NOITEM;
                                        Stack[CheckLocX][CheckLocY][Const.LOCY] = Const.NOITEM;
                                    } else {
                                        Stack[CheckLocX][CheckLocY][Const.LOCX] = Const.STARTPOS;
                                        Stack[CheckLocX][CheckLocY][Const.LOCY] = Const.STARTPOS;
                                    }
                                } else {
                                    Stack[CheckLocX][CheckLocY][Const.LOCX] = Const.NOITEM;
                                    Stack[CheckLocX][CheckLocY][Const.LOCY] = Const.NOITEM;
                                }
                                CheckLocX = StackLocX;
                                CheckLocY = StackLocY;
                            }
                        }
                    }//While loop end
                    if (Mark == 0) {//check where can be eat
                        EatAble = true;
                    } else {// nothing to  eat
                        ArrayIniter(ChessStatus, Mapping, CanBeEat, HasGone, Stack, CheckPoint, CanGo, Row_len, Col_len);//init array
                    }
                    if (EatAble) {//Eating
                        Eating(ChessStatus, CanBeEat, Row_len, Col_len);
                        ArrayIniter(ChessStatus, Mapping, CanBeEat, HasGone, Stack, CheckPoint, CanGo, Row_len, Col_len);//init array
                        EatAble = false;
                    }
                }//if has chess
            }//for j loop
        }// for i loop

    }

    public static void Eating(Chess[][] ChessStatus, int[][] CanBeEat, int Row_len, int Col_len) {
        for (int i = 0; i < Row_len; i++) {
            for (int j = 0; j < Col_len; j++) {
                if (CanBeEat[i][j] == 1) {
                    ChessStatus[i][j] = null;
                }
            }
        }
    }

    public static void ArrayIniter(Chess[][] ChessStatus, int[][] Mapping, int[][] CanBeEat, int[][] HasGone, int[][][] Stack, int[][][] CheckPoint, int[][][] CanGo, int Row_len, int Col_len) {
        for (int i = 0; i < Row_len; i++) {
            for (int j = 0; j < Col_len; j++) {
                if (ChessStatus[i][j] == null) {
                    Mapping[i][j] = Const.NO_CHESS;
                } else {
                    Mapping[i][j] = ChessStatus[i][j].getColor();
                }
                HasGone[i][j] = Const.HAVENGONE;
                CanBeEat[i][j] = Const.CANNOTEAT;
                Stack[i][j][Const.LOCX] = Const.NOITEM;
                Stack[i][j][Const.LOCY] = Const.NOITEM;
                CanGo[i][j][Const.UP] = Const.CANNOTGO;
                CanGo[i][j][Const.RIGHT] = Const.CANNOTGO;
                CanGo[i][j][Const.DOWN] = Const.CANNOTGO;
                CanGo[i][j][Const.LEFT] = Const.CANNOTGO;
                CheckPoint[i][j][Const.UP] = Const.INIT;
                CheckPoint[i][j][Const.RIGHT] = Const.INIT;
                CheckPoint[i][j][Const.DOWN] = Const.INIT;
                CheckPoint[i][j][Const.LEFT] = Const.INIT;
            }
        }
    }

    public static short Checker(int[][][] CheckPoint, int[][] Mapping, int i, int j) {
        int Row_len = Mapping.length, Col_len = Mapping[0].length;
        short Mark = 0;

        if (i + 1 > Row_len) {//check if has wall
            CheckPoint[i][j][Const.RIGHT] = Const.DIFF_COLOR;
        } else if (i - 1 < 0) {
            CheckPoint[i][j][Const.LEFT] = Const.DIFF_COLOR;
        } else if (j + 1 > Col_len) {
            CheckPoint[i][j][Const.UP] = Const.DIFF_COLOR;
        } else if (j - 1 < 0) {
            CheckPoint[i][j][Const.DOWN] = Const.DIFF_COLOR;
            /////////////////////////////////////////////////////////////////////////
        } else if (Mapping[i][j] != Mapping[i][j + 1] && Mapping[i][j + 1] != Const.NO_CHESS) {//check if Diff Color
            CheckPoint[i][j][Const.UP] = Const.DIFF_COLOR;
        } else if (Mapping[i][j] != Mapping[i + 1][j] && Mapping[i + 1][j] != Const.NO_CHESS) {
            CheckPoint[i][j][Const.RIGHT] = Const.DIFF_COLOR;
        } else if (Mapping[i][j] != Mapping[i][j - 1] && Mapping[i][j - 1] != Const.NO_CHESS) {
            CheckPoint[i][j][Const.DOWN] = Const.DIFF_COLOR;
        } else if (Mapping[i][j] != Mapping[i - 1][j] && Mapping[i - 1][j] != Const.NO_CHESS) {
            CheckPoint[i][j][Const.LEFT] = Const.DIFF_COLOR;
            //////////////////////////////////////////////////////////////////////////
        } else if (Mapping[i][j] == Mapping[i][j + 1]) {//check if Same Color
            CheckPoint[i][j][Const.UP] = Const.SAME_COLOR;
        } else if (Mapping[i][j] != Mapping[i + 1][j]) {
            CheckPoint[i][j][Const.RIGHT] = Const.SAME_COLOR;
        } else if (Mapping[i][j] != Mapping[i][j - 1]) {
            CheckPoint[i][j][Const.DOWN] = Const.SAME_COLOR;
        } else if (Mapping[i][j] != Mapping[i - 1][j]) {
            CheckPoint[i][j][Const.LEFT] = Const.SAME_COLOR;
            ///////////////////////////////////////////////////////////////////////////
        } else if (Mapping[i][j + 1] == Const.NO_CHESS) {//check if Nothing here
            CheckPoint[i][j][Const.UP] = Const.NOTHING;
            Mark = 1;
        } else if (Mapping[i + 1][j] == Const.NO_CHESS) {
            CheckPoint[i][j][Const.RIGHT] = Const.NOTHING;
            Mark = 1;
        } else if (Mapping[i][j - 1] == Const.NO_CHESS) {
            CheckPoint[i][j][Const.DOWN] = Const.NOTHING;
            Mark = 1;
        } else if (Mapping[i - 1][j] == Const.NO_CHESS) {
            CheckPoint[i][j][Const.LEFT] = Const.NOTHING;
            Mark = 1;
            ///////////////////////////////////////////////////////////////////////////
        }
        return Mark;
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
    public short toLose(final Chess[][] ChessStatus){
        int Row_len = ChessStatus.length, Col_len = ChessStatus[0].length;
        int WhiteCCounter = 0, BlackCCounter = 0;
        BlackCCounter --;
        for (int i = 0; i < Row_len; i++) {
            for (int j = 0; j < Col_len; j++) {
                if (ChessStatus[i][j] != null) {
                    short nowColor = ChessStatus[i][j].getColor();
                    if (nowColor == Const.WHITE_CHESS) {
                        WhiteCCounter++;
                    } else if(nowColor == Const.BLACK_CHESS){
                        BlackCCounter++;
                    }
                }
            }
        }
        if(WhiteCCounter > BlackCCounter){
            return Const.WHITE_WIN;
        }else if(BlackCCounter > WhiteCCounter){
            return Const.BLACK_WIN;
        }else{
            return Const.TIE;
        }

    }
    public short DownLastCheck(final Chess[][] ChessStatus, Chess CheckChess) {
        int Row_len = ChessStatus.length, LocX = CheckChess.getLoc().getX(), LocY = CheckChess.getLoc().getY();
        int CheckPoint = LocY;
        int CheckResault = 0;
        while (CheckPoint < Row_len && CheckPoint < LocY + 6 && ChessStatus[LocX][CheckPoint] != null) {
            if (CheckChess.getColor() == ChessStatus[LocX][CheckPoint].getColor()) {
                CheckResault++;
            }
            CheckPoint++;
        }
        if (CheckResault >= 5) {
            if (CheckChess.getColor() == Const.WHITE_CHESS) {
                return Const.WHITE_WIN;
            } else {
                return Const.BLACK_WIN;
            }
        }
        return Const.NO_WIN;
    }

    public short LeftLastCheck(final Chess[][] ChessStatus, Chess CheckChess) {
        int Col_len = ChessStatus[0].length, LocX = CheckChess.getLoc().getX(), LocY = CheckChess.getLoc().getY();
        int CheckPoint = LocX;
        int CheckResault = 0;
        while (CheckPoint < Col_len && CheckPoint < LocX + 6 && ChessStatus[CheckPoint][LocY] != null) {
            if (CheckChess.getColor() == ChessStatus[CheckPoint][LocY].getColor()) {
                CheckResault++;
            }
            CheckPoint++;
        }
        if (CheckResault >= 5) {
            if (CheckChess.getColor() == Const.WHITE_CHESS) {
                return Const.WHITE_WIN;
            } else {
                return Const.BLACK_WIN;
            }
        }
        return Const.NO_WIN;
    }

    public short LeftDownLastCheck(final Chess[][] ChessStatus, Chess CheckChess) {
        int Row_len = ChessStatus.length, Col_len = ChessStatus[0].length, LocX = CheckChess.getLoc().getX(), LocY = CheckChess.getLoc().getY();
        int CheckPointX = LocX, CheckPointY = LocY;
        int CheckResault = 0;
        while (CheckPointX < Row_len && CheckPointY < Col_len && CheckPointX < LocX + 6 && CheckPointY < LocY + 6 && ChessStatus[CheckPointX][CheckPointY] != null) {
            if (CheckChess.getColor() == ChessStatus[CheckPointX][CheckPointY].getColor()) {
                CheckResault++;
            }
            CheckPointX++;
            CheckPointY++;
        }
        if (CheckResault >= 5) {
            if (CheckChess.getColor() == Const.WHITE_CHESS) {
                return Const.WHITE_WIN;
            } else {
                return Const.BLACK_WIN;
            }
        }
        return Const.NO_WIN;
    }

    public short LeftUpLastCheck(final Chess[][] ChessStatus, Chess CheckChess) {
        int Row_len = ChessStatus.length, Col_len = ChessStatus[0].length, LocX = CheckChess.getLoc().getX(), LocY = CheckChess.getLoc().getY();
        int CheckPointX = LocX, CheckPointY = LocY;
        int CheckResault = 0;
        while (CheckPointX < Row_len && CheckPointY > 0 && CheckPointX < LocX + 6 && CheckPointY < LocY + 6 && ChessStatus[CheckPointX][CheckPointY] != null) {
            if (CheckChess.getColor() == ChessStatus[CheckPointX][CheckPointY].getColor()) {
                CheckResault++;
            }
            CheckPointX++;
            CheckPointY--;
        }
        if (CheckResault >= 5) {
            if (CheckChess.getColor() == Const.WHITE_CHESS) {
                return Const.WHITE_WIN;
            } else {
                return Const.BLACK_WIN;
            }
        }
        return Const.NO_WIN;
    }

    public short TieCheck(final Chess[][] ChessStatus, Chess CheckChess) {
        int Row_len = ChessStatus.length, Col_len = ChessStatus[0].length, LocX = CheckChess.getLoc().getX(), LocY = CheckChess.getLoc().getY();
        if (Row_len == LocX && Col_len == LocY) {
            return Const.TIE;
        }
        return Const.NO_WIN;
    }
}
