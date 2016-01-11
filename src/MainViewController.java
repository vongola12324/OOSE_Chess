import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.StringJoiner;

public class MainViewController implements Initializable {
    private myImageView[][] board = new myImageView[20][20];
    private final Image black = new Image("image/black.jpg");
    private final Image white = new Image("image/white.jpg");
    private final Image nochess = new Image("image/board.jpg");
    private final ToggleGroup ruleGroup = new ToggleGroup();

    private boolean gameStatus = false;
    private String playerA;
    private String PlayerB;
    private Rule gameRule = new GomokuRule();

    private ChessBoard chessBoard;
    private final Alert alert = new Alert(Alert.AlertType.INFORMATION);

    @FXML
    GridPane MainView_Board;
    @FXML
    Button New_Game;
    @FXML
    Button Surrender;
    @FXML
    RadioButton Rule_Five;
    @FXML
    RadioButton Rule_Weichi;
    @FXML
    TextField Player1_name;
    @FXML
    TextField Player2_name;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeBoardImage();
        initializeRadioButton();
        setAllActionListener();
        initialAlert();
    }

    void initialAlert() {
        alert.setTitle("Win!");
        alert.setContentText("Click Yes to restart the game.");
    }

    void initializeRadioButton() {
        Rule_Five.setToggleGroup(ruleGroup);
        Rule_Weichi.setToggleGroup(ruleGroup);
    }

    void restartAndInitial() {
        initializeBoardImage();
        setAllActionOfBoardToBlackOrWhite();
        this.gameStatus = false;
        this.Player1_name.setEditable(true);
        this.Player2_name.setEditable(true);
    }

    void initializeBoardImage() {
        chessBoard = new ChessBoard(this.gameRule);
        for (int i = 0; i < 20; i++) {
            for (int i2 = 0; i2 < 20; i2++) {
                board[i][i2] = new myImageView("image/board.jpg");
                board[i][i2].setFitHeight(Const.IMAGE_HEIGHT);
                board[i][i2].setFitWidth(Const.IMAGE_WIDTH);
                board[i][i2].setLoc(i, i2);
                MainView_Board.add(board[i][i2], i, i2);
            }
        }
    }

    void setAllActionListener() {
        setAllActionOfBoardToBlackOrWhite();

        New_Game.setOnMouseClicked(event -> newGame());
        Surrender.setOnMouseClicked(event -> surrenderGame());
        ruleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            changeGameMode();
        });
    }

    void newGame() {
        restartAndInitial();
    }

    void surrenderGame() {
        showAlert(chessBoard.checkToLose());
        Record gameRecord = chessBoard.getGameRecord();
        gameRecord.saveRecord(chessBoard.checkToLose());
        restartAndInitial();
    }

    void changeGameMode() {
        if (ruleGroup.getSelectedToggle() == Rule_Five) {
            this.gameRule = new GomokuRule();
            restartAndInitial();
        } else if (ruleGroup.getSelectedToggle() == Rule_Weichi) {
            this.gameRule = new GoRule();
            restartAndInitial();
        }

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
        if (Const.DEBUG) {
            System.out.println("Click at " + targetImage.getLoc());
        }
        if (!this.gameStatus){
            this.gameStatus = true;
            chessBoard.initRecord((Player1_name.getText().equals("") ? "NoName" : Player1_name.getText()), (Player2_name.getText().equals("") ? "NoName" : Player2_name.getText()));
            Player1_name.setEditable(false);
            Player2_name.setEditable(false);
        }

        if (chessBoard.clickDot(targetImage.getLoc())) {
            ArrayList<Location> arrayList = chessBoard.checkEat();
            short result = chessBoard.checkFinish();
            updateUI(targetImage);
            if (arrayList != null && arrayList.size() > 0){
                for(Location l:arrayList){
                System.out.println("Wanted: " + l );
                  updateImageToColor(Const.NO_CHESS, board[l.getX()][l.getY()]);
                }
            }
            if (result == Const.NO_WIN) {
                chessBoard.changePlayer();
//                targetImage.setOnMouseClicked(null);
            } else if (result != Const.NO_WIN) {
                alertAndRestart(result);
            }
        }
    }

    void updateUI(myImageView targetImage) {
        updateImageToColor(chessBoard.getNowPlayer(), targetImage);
    }

    void updateImageToColor(short color, myImageView targetImage) {
        if (color == Const.NO_CHESS)
            targetImage.setImage(nochess);
         else if (color == Const.BLACK_CHESS)
            targetImage.setImage(black);
        else
            targetImage.setImage(white);
    }

    void alertAndRestart(short result) {
        showAlert(result);
        //restartAndInitial();
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

    public void setLoc(int x, int y) {
        loc = new Location(x, y);
    }

    public Location getLoc() {
        return loc;
    }
}
