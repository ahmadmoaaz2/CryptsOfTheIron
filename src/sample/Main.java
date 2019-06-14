package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Main extends Application {
    private Stage primaryStage;

    @Override
    public void init() throws Exception {
        super.init();
    }


    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("homepage.fxml"));
        Parent root = fxmlLoader.load();
        this.primaryStage.setTitle("Crypts of The Angry Irons");
        this.primaryStage.setScene(new Scene(root, 800, 400));
        this.primaryStage.setResizable(false);
        this.primaryStage.show();
        ((Controller) fxmlLoader.getController()).setPrimaryStage(this.primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

}