package ca.crypts;

import javafx.stage.Stage;

public interface Controller {
    void getGamesPage() throws Exception;
    void getLoginPage() throws Exception;
    void getHomePage() throws Exception;
    void setPrimaryStage(Stage primaryStage);
}
