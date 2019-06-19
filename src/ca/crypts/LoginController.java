package ca.crypts;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


public class LoginController extends Controller implements Authenticates {
    @FXML
    public TextField userName;
    @FXML
    public PasswordField userPasswordHidden;
    @FXML
    public TextField userPasswordShown;
    @FXML
    public CheckBox shown;
    private boolean rememberUser = false;
    @FXML
    public Label mainText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        userPasswordHidden.visibleProperty().bind(shown.selectedProperty().not());
        userPasswordShown.visibleProperty().bind(shown.selectedProperty());
        userPasswordHidden.textProperty().bindBidirectional(userPasswordShown.textProperty());
        if (getUserID() != null) {
            try {
                Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + System.getProperty("user.dir") + "/appData.db");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * from users where ID=" + getUserID());
                resultSet.next();
                userName.setText(resultSet.getString("NAME"));
                userPasswordHidden.setText(Encrypter.decrypt(resultSet.getString("PASSWORD")));
                statement.close();
                connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void authenticateUser() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + System.getProperty("user.dir") + "/appData.db");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * from users");
        boolean matches = false;
        while (resultSet.next()) {
            if (resultSet.getString("NAME").equals(userName.getText()) &&
                    Encrypter.decrypt(resultSet.getString("PASSWORD")).equals(userPasswordHidden.getText())) {
                matches = true;
                break;
            }
        }
        if (matches) getGamesPage();
        else {
            mainText.setText("No Matching Users, Try Again");
            mainText.setTextFill(Paint.valueOf("red"));
        }
        statement.close();
        connection.close();
    }

    @Override
    public void rememberUser() {
        this.rememberUser = !rememberUser;
    }
}
