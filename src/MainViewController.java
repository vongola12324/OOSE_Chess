import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {
    private myImageView[][] board = new myImageView[20][20];
    private Image black = new Image("image/black.jpg");
    private Image white = new Image("image/white.jpg");

    private ChessBoard chessBoard;
    private final Alert alert = new Alert(Alert.AlertType.INFORMATION);

    @FXML
    GridPane MainView_Board;
    @FXML
    Button New_Game;
    @FXML
    Button Surrender;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeBoardImage();
        setAllActionListener();
        initialAlert();
    }

    void initialAlert() {
        alert.setTitle("Win!");
        alert.setContentText("Click Yes to restart the game.");
    }

    void restartAndInitial() {
        initializeBoardImage();
        setAllActionOfBoardToBlackOrWhite();
    }

    void initializeBoardImage() {
        chessBoard = new ChessBoard(new GomokuRule());
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

        New_Game.setOnMouseClicked(event -> restartAndInitial());
        Surrender.setOnMouseClicked(event -> surrenderGame());
    }

    void newGame() {
        restartAndInitial();
    }

    void surrenderGame() {
        showAlert(chessBoard.checkFinish());
        restartAndInitial();
    }

    void setAllActionOfBoardToBlackOrWhite() {
        for (int i = 0; i < 20; i++) {
            for (int i2 = 0; i2 < 20; i2++) {
                setClickedListenerToBoardImage(i, i2);
            }
        }
    }

    void setClickedListenerToBoardImage(int i, int i2) {
        board[i][i2].setOnMouseClicked(event -> {
            checkAndUpdateUI((myImageView) event.getSource());
        });
    }

    void checkAndUpdateUI(myImageView targetImage) {
        short result = chessBoard.clickDot(targetImage.getLoc());
        updateUI(targetImage);
        if (result == Const.NO_WIN) {
            chessBoard.changePlayer();
            targetImage.setOnMouseClicked(null);
        } else if (result != Const.NO_WIN) {
            alertAndRestart(result);
        }
    }

    void updateUI(myImageView targetImage) {
        updateImageToColor(chessBoard.getNowPlayer(), targetImage);
    }

    void updateImageToColor(short color, myImageView targetImage) {
        if (color == Const.BLACK_CHESS)
            targetImage.setImage(black);
        else
            targetImage.setImage(white);
    }

    void alertAndRestart(short result) {
        showAlert(result);
        restartAndInitial();
    }

    void showAlert(short result) {
        changeAlertText(result);
        alert.showAndWait();
    }

    void changeAlertText(short result) {
        if (result == Const.BLACK_WIN) {
            alert.setHeaderText("BLACK Win!");
        } else if (result == Const.WHITE_WIN) {
            alert.setHeaderText("WHITE Win!");
        } else {
            alert.setHeaderText("TIE!");
        }
    }
}


class myImageView extends ImageView {
    private Location loc;

    myImageView(String imagePath) {
        super(imagePath);
    }

    public void setLoc(Location loc) {
        this.loc = loc;
    }

    public void setLoc(int x, int y) {
        loc = new Location(x, y);
    }

    public Location getLoc() {
        return loc;
    }
}
