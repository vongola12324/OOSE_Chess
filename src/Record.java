import java.util.ArrayList;

public class Record {
    private String blackChess;
    private String whiteChess;
    private ArrayList<Chess> chessHistory;

    public Record(String blackChessPlayer, String whiteChessPlayer) {
        this.blackChess = blackChessPlayer;
        this.whiteChess = whiteChessPlayer;
        if (Const.DEBUG) {
            System.out.println("PlayerA(Black): " + this.blackChess);
            System.out.println("PlayerB(White): " + this.whiteChess);
        }
        this.chessHistory = new ArrayList<>();
    }

    public void addRecord(Chess chess) {
        this.chessHistory.add(chess);
    }

    public void push(Chess chess) {
        this.addRecord(chess);
    }

    public Chess lastRecord() {
        return new BWChess(this.chessHistory.get(this.chessHistory.size() - 1));
    }

    public Chess top() {
        return this.lastRecord();
    }

    public void removeRecord() {
        this.chessHistory.remove(this.lastRecord());
    }

    public void pop() {
        this.removeRecord();
    }

    public void saveRecord() {
        // TODO: Save to file or DB
    }

    public void loadRecord() {
        // TODO: Load from file or DB
    }



}
