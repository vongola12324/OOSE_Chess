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
    private ArrayList<BWChess> chessHistory;

    public Record(short gameMode) {
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
        this.chessHistory.add((BWChess)chess);
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

    public ArrayList<BWChess> getChessHistory() {
        return this.chessHistory;
    }

    public short getGameMode(){
        return this.gameMode;
    }

    public void setPlayer(String blackChessPlayer, String whiteChessPlayer){
        this.blackChess = blackChessPlayer;
        this.whiteChess = whiteChessPlayer;
    }

    public String getBlackChess(){
        return this.blackChess;
    }

    public String getWhiteChess(){
        return this.whiteChess;
    }

    public void saveRecord(short winner) {
        // TODO: Save to file or DB
        this.winner = winner;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();;
        String json = gson.toJson(this);
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
            Object obj = gson.fromJson(br, Record.class);
            restoreRecord((Record)obj);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
