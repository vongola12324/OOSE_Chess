import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by vongola12324 on 2015/12/16.
 */
public class MainViewController implements Initializable {
    private static int gameRound = 1;
    private ImageView[][] board = new ImageView[20][20];
    private Image black = new Image("image/black.jpg");
    private Image white = new Image("image/white.jpg");

    @FXML
    GridPane MainView_Board;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeBoard();
        setActionListener();
    }

    void initializeBoard() {
        for(int i = 0; i < 20; i++) {
            for(int i2 = 0; i2 < 20; i2++) {
                board[i][i2] = new ImageView("image/board.jpg");
                board[i][i2].setFitHeight(37);
                board[i][i2].setFitWidth(38);
                MainView_Board.add(board[i][i2], i, i2);
            }
        }
    }

    void setActionListener() {
        setActionOfBoardToBlackOrWhite();
    }

    void setActionOfBoardToBlackOrWhite() {
        for(int i = 0; i < 20; i++) {
            for(int i2 = 0; i2 < 20; i2++)
                board[i][i2].setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        ImageView image = (ImageView) event.getSource();
                        ImageToBlackOrWhite(image);
                        image.setOnMouseClicked(null);
                        gameRound++;
                    }
                });
        }
    }

    void ImageToBlackOrWhite(ImageView temp) {
        if(isNowBlackOrWhite() == 0) {
            temp.setImage(black);
        } else {
            temp.setImage(white);
        }
    }

    int isNowBlackOrWhite() {
        if(gameRound % 2 == 0) {
            return 0;   //black
        } else {
            return 1;   //white
        }
    }

}
