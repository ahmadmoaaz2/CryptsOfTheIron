package ca.crypts;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class TicTacToeController extends Controller {
    @FXML
    public ImageView topRight;
    @FXML
    public ImageView topCenter;
    @FXML
    public ImageView topLeft;
    @FXML
    public ImageView middleRight;
    @FXML
    public ImageView middleCenter;
    @FXML
    public ImageView middleLeft;
    @FXML
    public ImageView bottomRight;
    @FXML
    public ImageView bottomCenter;
    @FXML
    public ImageView bottomLeft;
    @FXML
    public Pane container2;
    @FXML
    public Pane container3;
    int playerTurn = 1;
    String imageX = "https://media.discordapp.net/attachments/491108547606609945/592499176831713280/ttcx.png";
    String imageO = "https://cdn.discordapp.com/attachments/491108547606609945/592499175212580866/ttco.png";
    ImageView mainImage = new ImageView(new Image("https://www.feelif.com/upld/CatalogueItem/_icon/37/tic-tac-toe-digital-game-for-blind.png"));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BackgroundImage BI = new BackgroundImage(
                new Image("https://media.discordapp.net/attachments/491108547606609945/592207086964703253/621153.png?width=994&height=560"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
        container.setBackground(new Background(BI));
        for (ImageView imageView :
                new ImageView[]{topCenter, topLeft, topRight, bottomCenter, bottomLeft, bottomRight, middleCenter, middleLeft, middleRight}) {
            imageView.setImage(new Image("https://media.discordapp.net/attachments/491108547606609945/592499170263171098/ttcb.png"));
        }
    }

    public void startGame() {
        container2.setVisible(false);
        container3.setVisible(true);
    }

    public void handleAction(MouseEvent e) throws Exception {
        String id = ((ImageView) e.getSource()).getId();
        ImageView[] imageViews = new ImageView[]{topRight, topLeft, topCenter, bottomRight, bottomLeft,
                bottomCenter, middleCenter, middleLeft, middleRight};
        Arrays.stream(imageViews)
                .filter(imageView -> imageView.getId().equals(id))
                .filter(imageView -> imageView.getImage().getUrl().equals("https://media.discordapp.net/attachments/491108547606609945/592499170263171098/ttcb.png"))
                .forEach(imageView -> {
                    if (playerTurn == 1) {
                        imageView.setImage(new Image(imageX));
                        playerTurn = 2;
                    } else {
                        imageView.setImage(new Image(imageO));
                        playerTurn = 1;
                    }
                });
        checkEnd();
    }

    public void checkEnd() throws Exception {
        boolean topRightPlayer1 = topRight.getImage().getUrl().equals(imageX);
        boolean topRightPlayer2 = topRight.getImage().getUrl().equals(imageO);
        boolean topCenterPlayer1 = topCenter.getImage().getUrl().equals(imageX);
        boolean topCenterPlayer2 = topCenter.getImage().getUrl().equals(imageO);
        boolean topLeftPlayer1 = topLeft.getImage().getUrl().equals(imageX);
        boolean topLeftPlayer2 = topLeft.getImage().getUrl().equals(imageO);
        boolean middleRightPlayer1 = middleRight.getImage().getUrl().equals(imageX);
        boolean middleRightPlayer2 = middleRight.getImage().getUrl().equals(imageO);
        boolean middleCenterPlayer1 = middleCenter.getImage().getUrl().equals(imageX);
        boolean middleCenterPlayer2 = middleCenter.getImage().getUrl().equals(imageO);
        boolean middleLeftPlayer1 = middleLeft.getImage().getUrl().equals(imageX);
        boolean middleLeftPlayer2 = middleLeft.getImage().getUrl().equals(imageO);
        boolean bottomRightPlayer1 = bottomRight.getImage().getUrl().equals(imageX);
        boolean bottomRightPlayer2 = bottomRight.getImage().getUrl().equals(imageO);
        boolean bottomCenterPlayer1 = bottomCenter.getImage().getUrl().equals(imageX);
        boolean bottomCenterPlayer2 = bottomCenter.getImage().getUrl().equals(imageO);
        boolean bottomLeftPlayer1 = bottomLeft.getImage().getUrl().equals(imageX);
        boolean bottomLeftPlayer2 = bottomLeft.getImage().getUrl().equals(imageO);
        if ((topRightPlayer1 && topCenterPlayer1 && topLeftPlayer1) ||
                (middleLeftPlayer1 && middleRightPlayer1 && middleCenterPlayer1) ||
                (bottomLeftPlayer1 && bottomRightPlayer1 && bottomCenterPlayer1) ||
                (topRightPlayer1 && middleRightPlayer1 && bottomRightPlayer1) ||
                (topCenterPlayer1 && middleCenterPlayer1 && bottomCenterPlayer1) ||
                (topLeftPlayer1 && middleLeftPlayer1 && bottomLeftPlayer1) ||
                (topLeftPlayer1 && middleCenterPlayer1 && bottomRightPlayer1) ||
                (topRightPlayer1 && middleCenterPlayer1 && bottomLeftPlayer1)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Tic Tac Toe: Victory Player 1");
            alert.setHeaderText("Congratulations Player 1!!!");
            alert.setContentText("You Have Asserted Your Dominance Over Player 2");
            alert.show();
            getTicTacToe();
        } else if ((topRightPlayer2 && topCenterPlayer2 && topLeftPlayer2) ||
                (middleLeftPlayer2 && middleRightPlayer2 && middleCenterPlayer2) ||
                (bottomLeftPlayer2 && bottomRightPlayer2 && bottomCenterPlayer2) ||
                (topRightPlayer2 && middleRightPlayer2 && bottomRightPlayer2) ||
                (topCenterPlayer2 && middleCenterPlayer2 && bottomCenterPlayer2) ||
                (topLeftPlayer2 && middleLeftPlayer2 && bottomLeftPlayer2) ||
                (topLeftPlayer2 && middleCenterPlayer2 && bottomRightPlayer2) ||
                (topRightPlayer2 && middleCenterPlayer2 && bottomLeftPlayer2)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Tic Tac Toe: Victory Player 2");
            alert.setHeaderText("Congratulations Player 2!!!");
            alert.setContentText("You Have Asserted Your Dominance Over Player 1");
            alert.show();
            getTicTacToe();
        }
    }
}
