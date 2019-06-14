package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Controller implements Initializable {
    @FXML
    public TextField userName;
    @FXML
    public PasswordField userPasswordHidden;
    @FXML
    public TextField userPasswordShown;
    @FXML
    public CheckBox shown;
    @FXML
    public Pane container;
    private boolean userAuthenticated = false;
    private String name;
    private String password;
    private boolean rememberUser = false;
    private ArrayList<HashMap<String, String>> data = new ArrayList<>();
    private Stage primaryStage;

    public void getLoginPage() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("login.fxml"));
        Parent root = fxmlLoader.load();
        this.primaryStage.setTitle("Crypts Of the Angry Irons - Login");
        this.primaryStage.getScene().setRoot(root);
        ((Controller) fxmlLoader.getController()).setPrimaryStage(this.primaryStage);

    }

    public void getGamesPage() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("games.fxml"));
        Parent root = fxmlLoader.load();
        this.primaryStage.setTitle("Crypts Of the Angry Irons - Games");
        this.primaryStage.getScene().setRoot(root);
        ((Controller) fxmlLoader.getController()).setPrimaryStage(this.primaryStage);
    }

    public void getHomePage() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("homepage.fxml"));
        Parent root = fxmlLoader.load();
        this.primaryStage.setTitle("Crypts Of the Angry Irons");
        this.primaryStage.getScene().setRoot(root);
        ((Controller) fxmlLoader.getController()).setPrimaryStage(this.primaryStage);
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
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
//        readFileData();
    }

    public void shutDown() {
        try {
            FileWriter localfile = new FileWriter("data.txt");
            String fileData = "";
            fileData = fileData.concat("52 65 6d 65 6d 62 65 72 4d 65 43 68 65 63 6b 65 64 =" + rememberUser + "\n");
            fileData = fileData.concat("75 73 65 72 4e 61 6d 65 =" + (rememberUser && userAuthenticated ? name : "null") + "\n");
            fileData = fileData.concat("75 73 65 72 50 61 73 73 77 6f 72 64 =" + (rememberUser && userAuthenticated ? password : "null") + "\n");
            for (HashMap<String, String> map : data) {
                String usersName = map.get("name");
                String userPassword = map.get("password");
                fileData = fileData.concat("?" + usersName + "|" + "!" + Encrypter.encrypt(userPassword) + "&" + "\n");
            }
            fileData = fileData.substring(0, fileData.length() - 1 - 1);
            System.out.println(fileData);
            localfile.write(fileData);
        } catch (IOException e) {
            System.out.println(e.toString());
            new Scanner(System.in).nextLine();
        }
    }

    public void authenticateUser() throws Exception {
        boolean matched = false;
        for (HashMap<String, String> map : data) {
            if (map.get("name").equals(userName) &&
                    map.get("password").equals(shown.isSelected() ? userPasswordShown.getText() : userPasswordHidden.getText()))
                matched = true;
        }
        if (matched) {
            this.name = userName.getText();
            this.password = shown.isSelected() ? userPasswordShown.getText() : userPasswordHidden.getText();
            this.userAuthenticated = true;
            getGamesPage();
        }
    }

    public void passwordVisibilityToggle() {
        boolean shown = this.shown.isSelected();
        if (shown) {
            userPasswordHidden.setVisible(false);
            userPasswordShown.setVisible(true);
            userPasswordShown.setText(userPasswordHidden.getText());
        } else {
            userPasswordShown.setVisible(false);
            userPasswordHidden.setVisible(true);
            userPasswordHidden.setText(userPasswordShown.getText());
        }
    }

    public void readFileData() {
        try {
            File file = new File(Controller.class.getResource("data.txt").getFile());
            Scanner scanner = new Scanner(file);
            ArrayList<String> fileData = new ArrayList<>();
            while (scanner.hasNextLine()) {
                fileData.add(scanner.nextLine());
            }
            for (String line : fileData) {
                if (line.contains("52 65 6d 65 6d 62 65 72 4d 65 43 68 65 63 6b 65 64 =")) {
                    rememberUser = Boolean.parseBoolean(line.substring(line.indexOf("=") + 1));
                    shown.setSelected(rememberUser);
                } else if (line.contains("75 73 65 72 4e 61 6d 65 =")) {
                    String usersName = line.substring(line.indexOf("=") + 1);
                    if (!usersName.equals("null")) {
                        userName.setText(usersName);
                    }
                } else if (line.contains("75 73 65 72 50 61 73 73 77 6f 72 64 =")) {
                    String usersPassword = line.substring(line.indexOf("=") + 1);
                    if (!usersPassword.equals("null")) {
                        userPasswordHidden.setText(Encrypter.decrypt(usersPassword));
                        userPasswordShown.setText(Encrypter.decrypt(usersPassword));
                    }
                } else {
                    String usersName = line.substring(line.indexOf("?") + 1, line.indexOf("|"));
                    String usersPassword = line.substring(line.indexOf("!") + 1, line.indexOf("&"));
                    HashMap<String, String> usersData = new HashMap<>();
                    usersData.put("name", usersName);
                    usersData.put("password", Encrypter.decrypt(usersPassword));
                    data.add(usersData);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.toString());
        }
    }

    public void rememberUser() {
        this.rememberUser = !rememberUser;
    }

    public void quit() throws Exception {
        getHomePage();
    }
}
