import org.json.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class Record {
    private String blackChess;
    private String whiteChess;
    private ArrayList<Chess> chessHistory;

    public Record(String blackChessPlayer, String whiteChessPlayer) {
        this.blackChess = blackChessPlayer;
        this.whiteChess = whiteChessPlayer;
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
