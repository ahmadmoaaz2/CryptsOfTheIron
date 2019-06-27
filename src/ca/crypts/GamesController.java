package ca.crypts;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.ResourceBundle;

public class GamesController extends Controller {
    @FXML
    public ImageView image1;
    @FXML
    public ImageView image2;
    @FXML
    public ImageView image3;
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        image1.setImage(new Image("https://cdn6.aptoide.com/imgs/3/3/5/33592029a3c1217304f84b00083263c9_icon.png?w=240"));
        image2.setImage(new Image("https://st2.depositphotos.com/3146743/10824/v/950/depositphotos_108245522-stock-illustration-yellow-background-created-from-yellow.jpg"));
        image3.setImage(new Image("https://cdn.discordapp.com/attachments/491108547606609945/592504757839724544/fqfqfq.png"));
    }
}
