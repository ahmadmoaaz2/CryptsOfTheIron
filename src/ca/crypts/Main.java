package ca.crypts;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Main extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("homepage.fxml"));
        Parent root = fxmlLoader.load();
        this.primaryStage.setTitle("Crypts of The Angry Irons");
        this.primaryStage.setScene(new Scene(root, 900, 460));
        this.primaryStage.setResizable(false);
        this.primaryStage.show();
        ((LoginController) fxmlLoader.getController()).setPrimaryStage(this.primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void createTables() throws Exception{
        Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + System.getProperty("user.dir") + "/appData.db");
        Statement statement = connection.createStatement();
        statement.execute("" +
                "CREATE TABLE IF NOT EXISTS users (" +
                "   ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "   NAME varchar(60) NOT NULL," +
                "   PASSWORD varchar(150) NOT NULL)"
        );
        statement.close();
        connection.close();
    }

    public static void dropTables() throws Exception{
        Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + System.getProperty("user.dir") + "/appData.db");
        Statement statement = connection.createStatement();
        statement.execute("DROP TABLE IF EXISTS users");
        statement.close();
        connection.close();
    }
}