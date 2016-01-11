import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.swing.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Record {
    private String blackChess;
    private String whiteChess;
    private short winner;
    private short gameMode;
    private ArrayList<Chess> chessHistory;

    public Record(String blackChessPlayer, String whiteChessPlayer, short gameMode) {
        this.blackChess = blackChessPlayer;
        this.whiteChess = whiteChessPlayer;
        if (Const.DEBUG) {
            System.out.println("PlayerA(Black): " + this.blackChess);
            System.out.println("PlayerB(White): " + this.whiteChess);
        }
        this.chessHistory = new ArrayList<>();
        this.gameMode = gameMode;
    }

    private void restoreRecord(Record record){
        this.blackChess = record.blackChess;
        this.whiteChess = record.whiteChess;
        this.winner = record.winner;
        this.gameMode = record.gameMode;
        this.chessHistory = new ArrayList<>(record.chessHistory);
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

    public void saveRecord(short winner) {
        // TODO: Save to file or DB
        this.winner = winner;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();;
        String json = gson.toJson(this);
        //String json = "{\n\"BlackChess\":" + gson.toJson(blackChess) + ",\n\"WhiteChess\":" + gson.toJson(whiteChess) + ",\n\"Winner\":" + gson.toJson(winner) + ",\n\"ChessHistory:\n" + gson.toJson(chessHistory) + "\n}";
        try {
            FileWriter file = new FileWriter(new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()) + ".json");
            file.write(json);
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadRecord(String filename) {
        // TODO: Load from file or DB
        Gson gson = new Gson();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            this.restoreRecord(gson.fromJson(br, Record.class));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
