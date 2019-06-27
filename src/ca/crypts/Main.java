package ca.crypts;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        createTables();
        this.primaryStage = primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("homepage.fxml"));
        Parent root = fxmlLoader.load();
        this.primaryStage.setTitle("Crypts of The Angry Irons");
        this.primaryStage.setScene(new Scene(root, 900, 460));
        this.primaryStage.setResizable(false);
        this.primaryStage.show();
        ((Controller) fxmlLoader.getController()).setPrimaryStage(this.primaryStage);
        Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + System.getProperty("user.dir") + "/appData.db");
        Statement statement = connection.createStatement();
        ResultSet rememberUserFromSettings = statement.executeQuery("SELECT * from settings");
        if(rememberUserFromSettings.next()) {
            if (rememberUserFromSettings.getBoolean("rememberUser")) {
                int usersID = rememberUserFromSettings.getInt("ID");
                ((Controller) fxmlLoader.getController()).setUserID(usersID);
            } else {
                ((Controller) fxmlLoader.getController()).setUserID(0);
            }
        } else {
            statement.execute("INSERT INTO settings (rememberUser) VALUES (false)");
            ((Controller) fxmlLoader.getController()).setUserID(0);
        }
        statement.close();
        connection.close();
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
                "   PASSWORD varchar(150) NOT NULL);"
        );
        statement.execute(
                "CREATE TABLE IF NOT EXISTS settings (" +
                        " rememberUser BOOLEAN NOT NULL," +
                        " ID INTEGER, FOREIGN KEY (ID) REFERENCES users(ID));"
        );
        statement.close();
        connection.close();
    }

    public static void dropTables() throws Exception{
        Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + System.getProperty("user.dir") + "/appData.db");
        Statement statement = connection.createStatement();
        statement.execute("DROP TABLE IF EXISTS users");
        statement.execute("DROP TABLE IF EXISTS settings");
        statement.close();
        connection.close();
    }
}