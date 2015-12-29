import org.json.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class Record {
    private JSONObject j;
    private JSONArray a;
    private Map m;

    public void saveRecord(String filename, String Winner, String Loser, short WinChess, Chess[][] ChessStatus){
        j = new JSONObject();
        // Info
        j.put("Winner", Winner);
        j.put("Loser", Loser);
        j.put("WinChess", WinChess);

        // Chess
        a = new JSONArray();
        JSONArray ChessArray = new JSONArray();
        JSONObject Chess = new JSONObject();
        for(int i=0;i<ChessStatus.length;i++){
            for(int j=0;j<ChessStatus.length;j++){
                if (ChessStatus[i][j] != null){
                    Chess.put("Color", ChessStatus[i][j].getColor());
                    JSONObject ChessLoc = new JSONObject();
                    ChessLoc.put("x", ChessStatus[i][j].getLoc().getX());
                    ChessLoc.put("y", ChessStatus[i][j].getLoc().getY());
                    Chess.put("Location", ChessLoc);
                    Chess.put("Step", ChessStatus[i][j].getStep());
                    ChessArray.put(Chess);
                }
            }
            if (ChessArray.length() != 0){
                a.put(ChessArray);
            }
        }
        j.put("ChessStatus", a);

        // Save to file
        JSONWriter writer = null;
        try {
            writer = new JSONWriter(new FileWriter(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (writer!=null){
            writer.value(j);
            writer.endObject();
        }
    }

    public Object readRecord(String filename){
        return new Object();
    }
}
