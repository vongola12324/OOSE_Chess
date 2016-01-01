import java.util.ArrayList;

public class ChessBoard {
    Factory factory = new BWChessFactory();

    private Chess ChessStatus[][] = new BWChess[20][20];
    private short nowPlayer;

    // What game to play
    private Rule rule;

    // Record all chess
    private Record gameRecord;

    public ChessBoard(Rule rule) {
        initializeGame();
        // Clean Chess Board
        initializeChessBoard();
        this.rule = rule;
    }

    private void initializeGame() {
        // Black Chess First
        this.nowPlayer = Const.BLACK_CHESS;

        // Generate record
        // FIXME: getname from UI
        this.gameRecord = new Record("BLACK", "WHITE");
    }

    private void initializeChessBoard() {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                this.ChessStatus[i][j] = null;
            }
        }
    }

    public boolean clickDot(Location loc) {

        if (this.ChessStatus[loc.getX()][loc.getY()] != null) {
            // Make Chess
            this.ChessStatus[loc.getX()][loc.getY()] = ((BWChessFactory) factory).makeChess(this.nowPlayer, loc);

            // Generate Record
            this.gameRecord.addRecord(this.ChessStatus[loc.getX()][loc.getY()]);
            // DEBUG
            if (Const.DEBUG)
                System.out.println(this.gameRecord.lastRecord());
            return true;
        } else {
            return false;
        }
    }

    public short checkFinish() {
        return this.rule.check((BWChess[][]) ChessStatus);
    }

    public ArrayList<Location> checkEat() {
        return this.rule.eat((BWChess[][]) ChessStatus);
    }

    public short checkToLose() {
        return this.rule.toLose(nowPlayer);
    }

    public void changePlayer() {
        if (this.nowPlayer == Const.BLACK_CHESS) {
            this.nowPlayer = Const.WHITE_CHESS;
        } else {
            this.nowPlayer = Const.BLACK_CHESS;
        }
    }

    public short getNowPlayer() {
        return nowPlayer;
    }

    public void setRule(Rule rule) {
        if (Const.DEBUG) {
            if (rule instanceof GomokuRule)
                System.out.println("Now playing Gomoku");
            else
                System.out.println("Now playing Go");
        }

        this.rule = rule;
    }

    public Rule getRule() {
        return this.rule;
    }
}