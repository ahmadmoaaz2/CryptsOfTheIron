package ca.crypts;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.net.URL;
import java.sql.*;
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
    @FXML
    CheckBox rememberUser;
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
                        if (rememberUser.isSelected()) {
                            new Thread(() -> {
                                try {
                                    ResultSet results = statement.executeQuery("SELECT * from users where NAME='"+userName.getText()+"'");
                                    results.next();
                                    int foundID = results.getInt("ID");
                                    ResultSet rememberUserFromSettings = statement.executeQuery("SELECT rememberUser FROM settings");
                                    if (rememberUserFromSettings.next() && !rememberUserFromSettings.getBoolean("rememberUser")) {
                                        statement.execute("UPDATE settings set rememberUser=true, ID=" + foundID + " where rememberUser=false");
                                    } else if (rememberUserFromSettings.getBoolean("rememberUser")) {
                                        statement.execute("update settings set ID=" + foundID + " where rememberUser=true");
                                    }
                                } catch (SQLException e) {
                                    System.out.println(e.getMessage());
                                }
                            }).start();
                        } else {
                            statement.execute("UPDATE settings set rememberUser=false");
                        }
                        getGamesPage();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Error: Passwords Don't Match");
                        alert.setHeaderText(null);
                        alert.setContentText("Password and Confirm Password don't match. Please retype your password.");
                        alert.showAndWait();
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error: Invalid Username");
                alert.setHeaderText(null);
                alert.setContentText("Username must be between 5 and 29 characters");
                alert.showAndWait();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error: Username Taken");
            alert.setHeaderText(null);
            alert.setContentText("Username is already in use. Please pick a different Username");
            alert.showAndWait();
        }
        statement.close();
        connection.close();
    }
}
