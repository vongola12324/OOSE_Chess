
public class ChessBoard {
    ChessFactory factory = new ChessFactory();
    static short NO_CHESS = -1;
    static short BLACK_CHESS = 0;
    static short WHITE_CHESS = 1;

    private short boardStatus[][] = new short[19][19];
    private short nowPlayer;

    public ChessBoard(){
        // Clean Chess Board
        for(int i=0;i<19;i++){
            for(int j=0;j<19;j++){
                this.boardStatus[i][j] = NO_CHESS;
            }
        }

        // Black Chess First
        this.nowPlayer = BLACK_CHESS;
    }

    public void clickDot(Location loc) throws HasChessException {
        if (this.getStatus(loc) != NO_CHESS){
            throw new HasChessException(loc);
        }

        if (this.checkFinish()){

        } else {
            this.changePlayer();
        }

    }

    private short getStatus(Location loc){
        return this.boardStatus[loc.getX()][loc.getY()];
    }

    private boolean checkFinish(){
        // LOL
        // TODO: Finish Check
        return (int) (Math.random() * 10) > 5;
    }

    private void changePlayer(){
        if (this.nowPlayer == BLACK_CHESS){
            this.nowPlayer = WHITE_CHESS;
        } else {
            this.nowPlayer = BLACK_CHESS;
        }
    }
}

class HasChessException extends Exception {
    public HasChessException(Location loc){
        System.out.println("Error: There is already having a chess at " + loc + ".");
    }
}