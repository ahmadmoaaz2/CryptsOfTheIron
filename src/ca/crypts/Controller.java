package ca.crypts;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class Controller implements Initializable {
    private Stage primaryStage;
    private String userID;
    @FXML
    public Pane container;

    public final void getLoginPage() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("login.fxml"));
        Parent root = fxmlLoader.load();
        this.primaryStage.setTitle("Crypts Of the Angry Irons - Login");
        this.primaryStage.getScene().setRoot(root);
        ((Controller) fxmlLoader.getController()).setPrimaryStage(this.primaryStage);
        ((Controller) fxmlLoader.getController()).setUserID(this.userID);
    }

    public final void getGamesPage() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("games.fxml"));
        Parent root = fxmlLoader.load();
        this.primaryStage.setTitle("Crypts Of the Angry Irons - Games");
        this.primaryStage.getScene().setRoot(root);
        ((Controller) fxmlLoader.getController()).setPrimaryStage(this.primaryStage);
        ((Controller) fxmlLoader.getController()).setUserID(this.userID);
    }

    public final void getSignInPage() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("signup.fxml"));
        Parent root = fxmlLoader.load();
        this.primaryStage.setTitle("Crypts Of the Angry Irons - Sign Up");
        this.primaryStage.getScene().setRoot(root);
        ((Controller) fxmlLoader.getController()).setPrimaryStage(this.primaryStage);
        ((Controller) fxmlLoader.getController()).setUserID(this.userID);
    }

    public final void getHomePage() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("homepage.fxml"));
        Parent root = fxmlLoader.load();
        this.primaryStage.setTitle("Crypts Of the Angry Irons");
        this.primaryStage.getScene().setRoot(root);
        ((Controller) fxmlLoader.getController()).setPrimaryStage(this.primaryStage);
        ((Controller) fxmlLoader.getController()).setUserID(this.userID);
    }

    public final void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public final void setUserID(String newID){
        this.userID = newID;
    }

    public String getUserID() {
        return userID;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BackgroundImage BI = new BackgroundImage(
                new Image("https://d27nqrvkk22y65.cloudfront.net/cover/image/39936/big_bf9a559ea9.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
        container.setBackground(new Background(BI));
    }
}
