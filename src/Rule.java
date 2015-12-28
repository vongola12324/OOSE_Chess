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
        int if_fullChess = 0;

//        for (int i = 0; i < Row_len; i++) {
//            for (int j = i; j < Col_len; j++) {
//                if (ChessStatus[i][j] != null) {
//                    int CheckPoint = i + 1, CheckLocX = 1, CheckLocY = 1;
//                    boolean CheckResault = false;
//                    short nowColor = ChessStatus[i][j].getColor();
//
//                    //Scan Left 4 chess
//                    while (CheckPoint < Col_len && CheckPoint < j + 4) {
//                        if (ChessStatus[i][CheckPoint] != null &&
//                                ChessStatus[i][CheckPoint].getColor() != nowColor) {
//                            CheckResault = false;
//                            break;
//                        } else {
//                            CheckResault = true;
//                        }
//                        CheckPoint++;
//                    }
//                    if (CheckResault == true) {//for leftscan resault
//                        if (nowColor == Const.WHITE_CHESS)
//                            return Const.WHITE_WIN;
//                        else
//                            return Const.BLACK_WIN;
//                    }
//
//                    //Scan Down 4 chess
//                    CheckPoint = i + 1;
//                    while (CheckPoint + 1 < Row_len && CheckPoint < i + 4) {
//                        if (ChessStatus[CheckPoint][j] != null &&
//                                ChessStatus[CheckPoint][j].getColor() != nowColor) {
//                            CheckResault = false;
//                            break;
//                        } else {
//                            CheckResault = true;
//                        }
//                        CheckPoint++;
//                    }
//                    if (CheckResault == true) {//for downscan resault
//                        if (nowColor == Const.WHITE_CHESS)
//                            return Const.WHITE_WIN;
//                        else
//                            return Const.BLACK_WIN;
//                    }
//
//                    //Scan LeftUp 4 chess
//                    CheckLocX = 1;
//                    CheckLocY = 1;
//                    while (CheckLocX + i < Row_len && CheckLocY + j < CheckLocY) {
//                        if (ChessStatus[CheckLocX + i][CheckLocY + j] != null &&
//                                ChessStatus[CheckLocX + i][CheckLocY + j].getColor() != nowColor) {
//                            CheckResault = false;
//                            break;
//                        } else {
//                            CheckResault = true;
//                        }
//                        if (CheckLocX < 5) {
//                            CheckLocX++;
//                            CheckLocY++;
//                        }
//                    }
//                    if (CheckResault == true) {//for leftupScan resault
//                        if (nowColor == Const.WHITE_CHESS)
//                            return Const.WHITE_WIN;
//                        else
//                            return Const.BLACK_WIN;
//                    }
//                    CheckLocX = 1;
//                    CheckLocY = 1;
//
//                    //Scan LeftDown 4 chess
//                    while (CheckLocX + i < Row_len && CheckLocY + j < CheckLocY) {
//                        if (ChessStatus[CheckLocX + i][j - CheckLocY] != null &&
//                                ChessStatus[CheckLocX + i][j - CheckLocY].getColor() != nowColor) {
//                            CheckResault = false;
//                            break;
//                        } else {
//                            CheckResault = true;
//                        }
//                        if (CheckLocX < 5) {
//                            CheckLocX++;
//                            CheckLocY++;
//                        }
//                    }//
//                    if (CheckResault == true) {//for leftdownScan resault
//                        if (nowColor == Const.WHITE_CHESS)
//                            return Const.WHITE_WIN;
//                        else
//                            return Const.BLACK_WIN;
//                    } else {
//                        if (i + 1 == Row_len && j + 1 == Col_len) {//Check if Tie
//                            if (if_fullChess == 0) {
//                                return Const.TIE;
//                            }
//                        }
//                        return Const.NO_WIN;
//                    }
//                } else {
//                    if_fullChess = 1;//To Check if ChessBoard Is Full
//                }
//            }
//        }
        return Const.NO_WIN;
    }
}
