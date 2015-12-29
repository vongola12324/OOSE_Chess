public class ChessBoard {
    ChessFactory factory = new ChessFactory();

    private Chess ChessStatus[][] = new Chess[20][20];
    private short nowPlayer;
    private int step;
    private Rule rule;

    public ChessBoard(Rule rule) {
        initializeGame();
        // Clean Chess Board
        initializeChessBoard();
        this.rule = rule;
    }

    private void initializeGame() {
        // First step = 0
        this.step = 0;

        // Black Chess First
        this.nowPlayer = Const.BLACK_CHESS;
    }

    private void initializeChessBoard() {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                this.ChessStatus[i][j] = null;
            }
        }
    }

    public short clickDot(Location loc) {
        // Make Chess
        this.ChessStatus[loc.getX()][loc.getY()] = factory.makeChess(this.nowPlayer, loc, this.step);

        // Check Finish
        short winner = this.checkFinish();

        if (winner == Const.NO_WIN) {
            this.changePlayer();
            step++;
        }
        return winner;
    }

    private short checkFinish() {
        return this.rule.check(ChessStatus);
    }

    public short checkToLose() {
        return this.rule.toLose(ChessStatus);
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

    public void Surrender() {
        // TODO: Show Winner (UI)
        System.out.println("Winner: " + (this.nowPlayer == Const.BLACK_CHESS ? "BLACK" : "WHITE"));
    }
}