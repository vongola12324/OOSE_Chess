import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {
    private myImageView[][] board = new myImageView[20][20];
    private myImageView nowLocationImage;
    private Image black = new Image("image/black.jpg");
    private Image white = new Image("image/white.jpg");

    private ChessBoard chessBoard;
    private final Alert alert = new Alert(Alert.AlertType.INFORMATION);

    @FXML
    GridPane MainView_Board;
    @FXML
    Button New_Game;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeBoard();
        setAllActionListener();
        initialAlert();
    }

    void initialAlert() {
        alert.setTitle("Win!");
        alert.setContentText("Click Yes to restart the game.");
    }

    void startAndInitial() {
        initializeBoard();
        setAllActionOfBoardToBlackOrWhite();
    }

    void initializeBoard() {
        chessBoard = new ChessBoard(Const.GOMOKU_CHESS);
        for (int i = 0; i < 20; i++) {
            for (int i2 = 0; i2 < 20; i2++) {
                board[i][i2] = new myImageView("image/board.jpg");
                board[i][i2].setFitHeight(37);
                board[i][i2].setFitWidth(38);
                board[i][i2].setLoc(i, i2);
                MainView_Board.add(board[i][i2], i, i2);
            }
        }
    }

    void setAllActionListener() {
        setAllActionOfBoardToBlackOrWhite();

        New_Game.setOnMouseClicked(event -> startAndInitial());
    }

    void setAllActionOfBoardToBlackOrWhite() {
        for (int i = 0; i < 20; i++) {
            for (int i2 = 0; i2 < 20; i2++) {
                setClickedListenerToBoardImage(i, i2);
            }
        }
    }

    void setClickedListenerToBoardImage(int i, int i2) {
        board[i][i2].setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                nowLocationImage = (myImageView) event.getSource();
                try {
                    updateImageToColor(chessBoard.getNowPlayer());
                    short result = chessBoard.clickDot(nowLocationImage.getLoc());
                    nowLocationImage.setOnMouseClicked(null);
                    ifWinThenShowAlert(result);

                } catch (HasChessException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    void updateImageToColor(short color) {
        if(color == Const.BLACK_CHESS)
            nowLocationImage.setImage(black);
        else
            nowLocationImage.setImage(white);
    }

    void ifWinThenShowAlert(short result) {
        if(result != Const.NO_WIN) {
            showAlert(result);
        }
    }

    void showAlert(short result) {
        if(result == Const.BLACK_WIN) {
            alert.setHeaderText("BLACK Win!");
        } else if(result == Const.WHITE_WIN) {
            alert.setHeaderText("WHITE Win!");
        } else {
            alert.setHeaderText("TIE!");
        }
        alert.showAndWait();
    }
}


class myImageView extends ImageView {
    private Location loc;

    myImageView(String imagePath) {
        super(imagePath);
    }

    void setLoc(Location loc) {
        this.loc = loc;
    }

    void setLoc(int x, int y) {
        loc = new Location(x, y);
    }

    Location getLoc() {
        return loc;
    }
}
