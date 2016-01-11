import java.util.ArrayList;

abstract class Rule {
    public abstract short check(BWChess[][] ChessStatus);

    public short toLose(short nowPlayer) {
        if (nowPlayer == Const.BLACK_CHESS)
            return Const.BLACK_WIN;
        else
            return Const.WHITE_WIN;
    }

    public abstract ArrayList<Location> eat(BWChess[][] ChessStatus);
}

interface Eatable {
    public ArrayList eat(BWChess[][] ChessStatus);
}

class GoRule extends Rule implements Eatable {
    private short[][] Mapping;
    private int[] Block = new int[361];
    private int BlockLen = 0;
    private ArrayList<Location> UpdateLoc = new ArrayList<>();

    @Override
    public short check(BWChess[][] ChessStatus) {
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
        if (WhiteColorCounter >= 177 && BlackColorCounter < 183) {
            return Const.WHITE_WIN;
        } else if (BlackColorCounter >= 183 && WhiteColorCounter < 177) {
            return Const.BLACK_WIN;
        }
        return Const.NO_WIN;
    }


    @Override
    public ArrayList<Location> eat(BWChess[][] ChessStatus) {

        int Row_Len = ChessStatus.length, Col_Len = ChessStatus[0].length;
        ArrayIniter(ChessStatus);
        UpdateLoc = new ArrayList<Location>();

        for (int i = 0; i < Row_Len; i++) {
            for (int j = 0; j < Col_Len; j++) {
                if (Mapping[i][j] == Const.NO_CHESS) {
                    continue;
                } else {
                    Block = new int[361];
                    BlockLen = 1;
                    Block[0] = 100 * i + j;
                    Checker(i, j);

                    if (checkNoChess()) {

                        continue;
                    }

                    Eating(ChessStatus);
                    System.out.println("next");
                }
            }
        }
        return UpdateLoc;
    }

    public void Eating(BWChess[][] ChessStatus) {
        UpdateChessStatus();
        for (int t = 0; t < BlockLen; t++) {
            int i = Block[t] / 100;
            int j = Block[t] % 100;

            ChessStatus[i][j] = null;
            SetMap(i, j, Const.NO_CHESS);
        }
    }

    public void ArrayIniter(BWChess[][] ChessStatus) {
        int Row_Len = ChessStatus.length, Col_Len = ChessStatus[0].length;
        Mapping = new short[Row_Len][Col_Len];
        for (int i = 0; i < Row_Len; i++) {
            for (int j = 0; j < Col_Len; j++) {
                if (ChessStatus[i][j] != null) {
                    this.SetMap(i, j, ChessStatus[i][j].getColor());
                } else {
                    this.SetMap(i, j, Const.NO_CHESS);
                }
            }
        }
    }

    public void Checker(int LocX, int LocY) {

        int Rol_Len = Mapping.length, Col_Len = Mapping[0].length;
        if (LocY - 1 >= 0 && Mapping[LocX][LocY - 1] == Mapping[LocX][LocY] && isInBlock((LocX) * 100 + LocY - 1)) {
            Block[BlockLen] = (LocX) * 100 + LocY - 1;
            BlockLen++;
            Checker(LocX, LocY - 1);
        }
        if (LocX + 1 < Rol_Len && Mapping[LocX + 1][LocY] == Mapping[LocX][LocY] && isInBlock((LocX + 1) * 100 + LocY)) {
            Block[BlockLen] = (LocX + 1) * 100 + LocY;
            BlockLen++;
            Checker(LocX + 1, LocY);
        }
        if (LocY + 1 < Col_Len && Mapping[LocX][LocY + 1] == Mapping[LocX][LocY] && isInBlock((LocX) * 100 + LocY + 1)) {
            Block[BlockLen] = (LocX) * 100 + LocY + 1;
            BlockLen++;
            Checker(LocX, LocY + 1);
        }
        if (LocX - 1 >= 0 && Mapping[LocX - 1][LocY] == Mapping[LocX][LocY] && isInBlock((LocX - 1) * 100 + LocY)) {
            Block[BlockLen] = (LocX - 1) * 100 + LocY;
            BlockLen++;
            Checker(LocX - 1, LocY);
        }
    }

    public boolean checkNoChess() {
        int i, j;
        for (int t = 0; t < BlockLen; t++) {
            i = Block[t] / 100;
            j = Block[t] % 100;

            if (i - 1 >= 0 && Mapping[i - 1][j] == Const.NO_CHESS) return true;

            if (i + 1 < 19 && Mapping[i + 1][j] == Const.NO_CHESS) return true;

            if (j - 1 >= 0 && Mapping[i][j - 1] == Const.NO_CHESS) return true;

            if (j + 1 < 19 && Mapping[i][j + 1] == Const.NO_CHESS) return true;
        }
        return false;
    }

    public boolean isInBlock(int num) {
        for (int i = 0; i < BlockLen; i++) {
            if (Block[i] == num) return false;
        }
        return true;
    }

    public void UpdateChessStatus() {
        int Updatelenth = BlockLen;
        for (int i = 0; i < Updatelenth; i++) {
            UpdateLoc.add(new Location(Block[i] / 100, Block[i] % 100));
        }
    }

    public void SetMap(int LocX, int LocY, short Status) {
        Mapping[LocX][LocY] = Status;
    }

}

class GomokuRule extends Rule {
    // Fixme: Change to short and return who win
    @Override
    public short check(final BWChess[][] ChessStatus) {
        int Row_len = ChessStatus.length, Col_len = ChessStatus[0].length, i, j;
        short CheckResault = Const.NO_WIN;
        BWChess CheckChess;
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

    public short toLose(final short nowPlayer) {
        if (nowPlayer == Const.BLACK_CHESS) {
            return Const.WHITE_WIN;
        } else {
            return Const.BLACK_WIN;
        }

    }

    @Override
    public ArrayList<Location> eat(BWChess[][] ChessStatus) {
        return null;
    }

    public short DownLastCheck(final BWChess[][] ChessStatus, BWChess CheckChess) {
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

    public short LeftLastCheck(final BWChess[][] ChessStatus, BWChess CheckChess) {
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

    public short LeftDownLastCheck(final BWChess[][] ChessStatus, BWChess CheckChess) {
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

    public short LeftUpLastCheck(final BWChess[][] ChessStatus, BWChess CheckChess) {
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

    public short TieCheck(final BWChess[][] ChessStatus, BWChess CheckChess) {
        int Row_len = ChessStatus.length, Col_len = ChessStatus[0].length, LocX = CheckChess.getLoc().getX(), LocY = CheckChess.getLoc().getY();
        if (Row_len == LocX && Col_len == LocY) {
            return Const.TIE;
        }
        return Const.NO_WIN;
    }
}
