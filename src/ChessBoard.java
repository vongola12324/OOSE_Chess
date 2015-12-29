import java.util.Observable;
import java.util.Observer;

public class ChessBoard {
    ChessFactory factory = new ChessFactory();

    private Chess ChessStatus[][] = new Chess[20][20];
    private short nowPlayer;
    private int step;
    private Rule rule;

    public ChessBoard(short Mode){
        // Clean Chess Board
        for(int i=0;i<20;i++){
            for(int j=0;j<20;j++){
                this.ChessStatus[i][j] = null;
            }
        }

        setRule(Mode);

        // First step = 0
        this.step = 0;

        // Black Chess First
        this.nowPlayer = Const.BLACK_CHESS;
    }


    private void setRule(short Mode){
        if(Mode == Const.GO_CHESS){
            this.rule = new GoRule();
        } else {
            this.rule = new GomokuRule();
        }
    }

    public short clickDot(Location loc) throws HasChessException {
        if (this.getStatus(loc) != Const.NO_CHESS){
            throw new HasChessException(loc);
        } else {
            // Make Chess
            this.ChessStatus[loc.getX()][loc.getY()] = factory.makeChess(this.nowPlayer, loc, this.step);

            // Check Finish
            short winner = this.checkFinish();
            if (winner == Const.NO_WIN) {
                this.changePlayer();
                return Const.NO_WIN;
            } else if(winner == Const.TIE) {
                System.out.println("TIE");
                return Const.TIE;
            } else {
                if(winner == Const.BLACK_WIN) {
                    step ++;
                    System.out.println("Black WIN");
                    return Const.BLACK_WIN;
                } else {
                    step ++;
                    System.out.println("White WIN");
                    return Const.WHITE_WIN;
                }
            }
        }

    }

    private short getStatus(Location loc){
        Chess target = this.ChessStatus[loc.getX()][loc.getY()];
        if (target == null){
            return Const.NO_CHESS;
        } else {
            return target.getColor();
        }
    }

    private short checkFinish(){
        return this.rule.check(ChessStatus);
    }

    private void changePlayer(){
        if (this.nowPlayer == Const.BLACK_CHESS){
            this.nowPlayer = Const.WHITE_CHESS;
        } else {
            this.nowPlayer = Const.BLACK_CHESS;
        }
    }

    public short getNowPlayer() {
        return nowPlayer;
    }
}

class HasChessException extends Exception {
    public HasChessException(Location loc){
        System.out.println("Error: There is already having a chess at " + loc + ".");
    }
}