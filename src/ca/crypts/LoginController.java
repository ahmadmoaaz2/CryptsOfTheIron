package ca.crypts;

import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    @FXML
    public Label mainText;
    @FXML
    public CheckBox rememberUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        userPasswordHidden.visibleProperty().bind(shown.selectedProperty().not());
        userPasswordShown.visibleProperty().bind(shown.selectedProperty());
        userPasswordHidden.textProperty().bindBidirectional(userPasswordShown.textProperty());
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + System.getProperty("user.dir") + "/appData.db");
            Statement statement = connection.createStatement();
            ResultSet rememberUserFromSettings = statement.executeQuery("SELECT * from settings");
            if (rememberUserFromSettings.getBoolean("rememberUser")) {
                int usersID = rememberUserFromSettings.getInt("ID");
                ResultSet userDetails = statement.executeQuery("SELECT * from users where ID="+usersID);
                userName.setText(userDetails.getString("NAME"));
                userPasswordHidden.setText(Encrypter.decrypt(userDetails.getString("PASSWORD")));
                rememberUser.setSelected(true);
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void authenticateUser() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:/" + System.getProperty("user.dir") + "/appData.db");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * from users");
        boolean matches = false;
        MutableInt foundID = new MutableInt();
        foundID.setValue(0);
        while (resultSet.next()) {
            if (resultSet.getString("NAME").equals(userName.getText()) &&
                    Encrypter.decrypt(resultSet.getString("PASSWORD")).equals(userPasswordHidden.getText())) {
                foundID.setValue(resultSet.getInt("ID"));
                matches = true;
                break;
            }
        }
        if (matches) {
            if (rememberUser.isSelected()) {
                new Thread(() -> {
                    try {
                        ResultSet rememberUserFromSettings = statement.executeQuery("SELECT rememberUser FROM settings");
                        if (rememberUserFromSettings.next() && !rememberUserFromSettings.getBoolean("rememberUser")) {
                            statement.execute("UPDATE settings set rememberUser=true, ID=" + foundID.getValue() + " where rememberUser=false");
                        } else if (rememberUserFromSettings.getBoolean("rememberUser")) {
                            statement.execute("update settings set ID=" + foundID.getValue() + " where rememberUser=true");
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
            alert.setTitle("Error: No Matching Users");
            alert.setHeaderText(null);
            alert.setContentText("Account not Found. Please Try Again or Make an Account if you don't have one.");
            alert.showAndWait();
        }
        statement.close();
        connection.close();
    }
}

class MutableInt {
    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}