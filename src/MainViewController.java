import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {
    private ImageView[][] board = new ImageView[20][20];
    private ChessBoard chessBoard = new ChessBoard();
    @FXML
    GridPane MainView_Board;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for(int i = 0; i < 20; i++) {
            for(int i2 = 0; i2 < 20; i2++) {
                board[i][i2] = new ImageView("image/board.jpg");
                board[i][i2].setFitHeight(37);
                board[i][i2].setFitWidth(38);
                MainView_Board.add(board[i][i2], i, i2);
            }
        }
    }

}
