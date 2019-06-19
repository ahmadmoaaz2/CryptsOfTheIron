package ca.crypts;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class SignupController extends Controller implements Authenticates {
    @FXML
    Label mainText;
    @FXML
    TextField userName;
    @FXML
    PasswordField userPasswordHidden;
    @FXML
    TextField userPasswordShown;
    @FXML
    CheckBox shown;
    @FXML
    PasswordField userPasswordHiddenConfirm;
    @FXML
    TextField userPasswordShownConfirm;
    boolean rememberUser = false;
    String name = null;
    String password = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        userPasswordHidden.visibleProperty().bind(this.shown.selectedProperty().not());
        userPasswordHiddenConfirm.visibleProperty().bind(this.shown.selectedProperty().not());
        userPasswordShown.visibleProperty().bind(this.shown.selectedProperty());
        userPasswordShownConfirm.visibleProperty().bind(this.shown.selectedProperty());
        userPasswordHidden.textProperty().bindBidirectional(userPasswordShown.textProperty());
        userPasswordHiddenConfirm.textProperty().bindBidirectional(userPasswordShownConfirm.textProperty());
    }

    @Override
    public void rememberUser(){
        this.rememberUser = !rememberUser;
    }

    @Override
    public void authenticateUser() throws Exception {

        Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + System.getProperty("user.dir") + "/appData.db");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * from users");
        boolean matches = false;
        while (resultSet.next()) {
            if (resultSet.getString("NAME").equals(userName.getText())) {
                matches = true;
                break;
            }
        }
        if (!matches) {
            if (userName.getText().length() > 4 && userName.getText().length() < 30) {
                if(userPasswordShown.getText().length() >= 8) {
                    if (userPasswordShown.getText().equals(userPasswordShownConfirm.getText())) {
                        statement.execute("INSERT INTO users (name, password) values ('" +
                                userName.getText() + "', '" + Encrypter.encrypt(userPasswordShown.getText()) + "')");
                        getGamesPage();
                    } else {
                        mainText.setText("Passwords Don't Match");
                        mainText.setTextFill(Paint.valueOf("red"));
                    }
                }
            } else {
                mainText.setText("Username must be < 5 and > 29");
                mainText.setTextFill(Paint.valueOf("red"));
            }
        }
        else {
            mainText.setText("Username is Taken");
            mainText.setTextFill(Paint.valueOf("red"));
        }
        statement.close();
        connection.close();
    }
}
