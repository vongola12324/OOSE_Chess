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
        // Set rule
        this.rule = rule;
        // Init game
        initializeGame();
        // Clean Chess Board
        initializeChessBoard();
    }

    private void initializeGame() {
        // Black Chess First
        this.nowPlayer = Const.BLACK_CHESS;
        this.initRecord();
    }

    private void initializeChessBoard() {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                this.ChessStatus[i][j] = null;
            }
        }
    }

    public void initRecord(){
        this.gameRecord = new Record(rule.getRuleCode());
    }

    public boolean clickDot(Location loc) {

        if (this.ChessStatus[loc.getX()][loc.getY()] == null) {
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
        short result = this.rule.check((BWChess[][]) ChessStatus);
        if (result != Const.NO_WIN)
            gameRecord.saveRecord(result);
        return result;
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

    public Rule getRule() {
        return this.rule;
    }

    public Record getGameRecord(){
        return this.gameRecord;
    }

    public void reseumFromRecord(Record record){
        // Clean Board
        this.ChessStatus = new BWChess[20][20];
        this.gameRecord = null;

        // Load Record
        this.gameRecord = record;
        ArrayList<BWChess> chessHistory = this.gameRecord.getChessHistory();
        for (BWChess chess:chessHistory){
            Location chessLoc = chess.getLoc();
            this.ChessStatus[chessLoc.getY()][chessLoc.getX()] = chess;
        }
    }

    public void setRecordPlayer(String playerA, String playerB){
        this.gameRecord.setPlayer(playerA, playerB);
    }
}