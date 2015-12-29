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
        int Row_len = ChessStatus.length, Col_len = ChessStatus[0].length, i, j, CheckLocX, CheckLocY, Mark = 0;
        int[][] Mapping = new int[Row_len][Col_len], CanBeEat = new int[Row_len][Col_len], HasGone = new int[Row_len][Col_len];
        int[][][] CheckPoint = new int[Row_len][Col_len][4], Stack = new int[Row_len][Col_len][2], CanGo = new int[Row_len][Col_len][4];
        //CheckPoint Comment
        //Up 0 -> Left 1 -> Down 2 -> Right 3
        //NotThing 0 , Same Color 1, Diff Color 2, NeverGo 3

        boolean EatAble = false;

        ArrayIniter(ChessStatus, Mapping, CanBeEat, HasGone, Stack, CheckPoint, CanGo, Row_len, Col_len);//init array

        //Mapping
        for (i = 0; i < Row_len; i++) {
            for (j = 0; j < Col_len; j++) {
                CheckLocX = i;
                CheckLocY = j;
                if (Mapping[i][j] != 0) {

                    Stack[i][j][0] = -2;
                    Stack[i][j][1] = -2;//-2 is start positon Stack[i][j][0] is LocX  Stack[i][j][1] is LocY
                    HasGone[i][j] = 1;
                    while (Stack[i][j][0] == -1 && Stack[i][j][1] == -1) {
                        Mark = Checker(CheckPoint, Mapping, CheckLocX, CheckLocY);//check
                        if (Mark == 1)//if check Nothing
                            break;
                        //if checked, go next, and stack position
                        //first, search where can go
                        for (int CheckCanGo = 0; CheckCanGo < 4; CheckCanGo++) {//check where can go
                            if (CheckCanGo == 0) {
                                if (CheckPoint[CheckLocX][CheckLocY][CheckCanGo] == 1 && HasGone[CheckLocX][CheckLocY + 1] == 0)
                                    CanGo[CheckLocX][CheckLocY][CheckCanGo] = 1;
                            }
                            if (CheckCanGo == 1) {
                                if (CheckPoint[CheckLocX][CheckLocY][CheckCanGo] == 1 && HasGone[CheckLocX + 1][CheckLocY] == 0)
                                    CanGo[CheckLocX][CheckLocY][CheckCanGo] = 1;
                            }
                            if (CheckCanGo == 2) {
                                if (CheckPoint[CheckLocX][CheckLocY][CheckCanGo] == 1 && HasGone[CheckLocX][CheckLocY - 1] == 0)
                                    CanGo[CheckLocX][CheckLocY][CheckCanGo] = 1;
                            }
                            if (CheckCanGo == 3) {
                                if (CheckPoint[CheckLocX][CheckLocY][CheckCanGo] == 1 && HasGone[CheckLocX - 1][CheckLocY] == 0)
                                    CanGo[CheckLocX][CheckLocY][CheckCanGo] = 1;
                            }
                        }

                        for (int Way_to_go = 0; Way_to_go < 4; Way_to_go++) {//goto next point
                            if (CheckPoint[CheckLocX][CheckLocY][Way_to_go] == 1) {//if same color
                                if (Way_to_go == 0) {
                                    CheckLocY++;//move
                                    if (HasGone[CheckLocX][CheckLocY] == 0) {// if never gone
                                        HasGone[CheckLocX][CheckLocY] = 1;//now is gone
                                        CanGo[CheckLocX][CheckLocY - 1][Way_to_go] = 0;//now cannot go
                                        CanBeEat[CheckLocX][CheckLocY - 1] = 1;//Mark Eatable Buff
                                        Stack[CheckLocX][CheckLocY][0] = CheckLocX;//Stack LocX Record
                                        Stack[CheckLocX][CheckLocY][1] = CheckLocY - 1;//Stack LocY Record
                                        break;//don't do others
                                    } else
                                        CheckLocY--;//return move back
                                } else if (Way_to_go == 1) {
                                    CheckLocX++;
                                    if (HasGone[CheckLocX][CheckLocY] == 0) {
                                        HasGone[CheckLocX][CheckLocY] = 1;
                                        CanGo[CheckLocX - 1][CheckLocY][Way_to_go] = 0;
                                        CanBeEat[CheckLocX - 1][CheckLocY] = 1;
                                        Stack[CheckLocX][CheckLocY][0] = CheckLocX - 1;
                                        Stack[CheckLocX][CheckLocY][1] = CheckLocY;
                                        break;
                                    } else
                                        CheckLocX++;
                                } else if (Way_to_go == 2) {
                                    CheckLocY--;
                                    if (HasGone[CheckLocX][CheckLocY] == 0) {
                                        HasGone[CheckLocX][CheckLocY] = 1;
                                        CanGo[CheckLocX][CheckLocY + 1][Way_to_go] = 0;
                                        CanBeEat[CheckLocX][CheckLocY + 1] = 1;
                                        Stack[CheckLocX][CheckLocY][0] = CheckLocX;
                                        Stack[CheckLocX][CheckLocY][1] = CheckLocY + 1;
                                        break;
                                    } else
                                        CheckLocY++;
                                } else {
                                    CheckLocX--;
                                    if (HasGone[CheckLocX][CheckLocY] == 0) {
                                        HasGone[CheckLocX][CheckLocY] = 1;
                                        CanGo[CheckLocX + 1][CheckLocY][Way_to_go] = 0;
                                        CanBeEat[CheckLocX + 1][CheckLocY] = 1;
                                        Stack[CheckLocX][CheckLocY][0] = CheckLocX + 1;
                                        Stack[CheckLocX][CheckLocY][1] = CheckLocY;
                                        break;
                                    } else
                                        CheckLocX--;
                                }
                            }
                        }//first step end

                        //Second, if no where to go, back to nearst position which  has CanGo == 1
                        if (CanGo[CheckLocX][CheckLocY][0] == 0 || CanGo[CheckLocX][CheckLocY][1] == 0
                                || CanGo[CheckLocX][CheckLocY][2] == 0 || CanGo[CheckLocX][CheckLocY][3] == 0) {

                            while (CanGo[CheckLocX][CheckLocY][0] == 0 || CanGo[CheckLocX][CheckLocY][1] == 0
                                    || CanGo[CheckLocX][CheckLocY][2] == 0 || CanGo[CheckLocX][CheckLocY][3] == 0) {
                                int StackLocX = Stack[CheckLocX][CheckLocY][0], StackLocY = Stack[CheckLocX][CheckLocY][1];
                                Stack[CheckLocX][CheckLocY][0] = -1;
                                Stack[CheckLocX][CheckLocY][1] = -1;
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
                    if (EatAble == true) {//Eating
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
                    Mapping[i][j] = ChessStatus[i][j].color;
                }
                HasGone[i][j] = 0;
                CanBeEat[i][j] = 0;
                Stack[i][j][0] = -1;
                Stack[i][j][1] = -1;
                CanGo[i][j][0] = 0;
                CanGo[i][j][1] = 0;
                CanGo[i][j][2] = 0;
                CanGo[i][j][3] = 0;
                CheckPoint[i][j][0] = 3;
                CheckPoint[i][j][1] = 3;
                CheckPoint[i][j][2] = 3;
                CheckPoint[i][j][3] = 3;
            }
        }
    }

    public static short Checker(int[][][] CheckPoint, int[][] Mapping, int i, int j) {
        int Row_len = Mapping.length, Col_len = Mapping[0].length;
        short Mark = 0;

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
            Mark = 1;
        } else if (Mapping[i + 1][j] == 0) {
            CheckPoint[i][j][1] = 0;
            Mark = 1;
        } else if (Mapping[i][j - 1] == 0) {
            CheckPoint[i][j][2] = 0;
            Mark = 1;
        } else if (Mapping[i - 1][j] == 0) {
            CheckPoint[i][j][3] = 0;
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
