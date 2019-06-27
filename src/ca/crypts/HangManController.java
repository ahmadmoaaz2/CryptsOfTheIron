package ca.crypts;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.net.URL;
import java.util.ResourceBundle;

public class HangManController extends Controller {
    @FXML
    public Label chances;
    @FXML
    public ImageView hangmanImage;
    @FXML
    public FlowPane containerForGameWord;
    private GameWord gameWord;
    private String currImage = "hangman1";
    private Image hangman1 = new Image("https://cdn.discordapp.com/attachments/491108547606609945/592208340806008842/First.png");
    private Image hangman2 = new Image("https://cdn.discordapp.com/attachments/491108547606609945/592211430413959191/123123.png");
    private Image hangman3 = new Image("https://cdn.discordapp.com/attachments/491108547606609945/592214872742166548/123123123.png");
    private Image hangman4 = new Image("https://cdn.discordapp.com/attachments/491108547606609945/592228827925577748/1.png");
    private Image hangman5 = new Image("https://cdn.discordapp.com/attachments/491108547606609945/592215930453622784/Second.png");
    private Image hangman6 = new Image("https://cdn.discordapp.com/attachments/491108547606609945/592222834055446539/t.png");
    private Image hangman7 = new Image("https://cdn.discordapp.com/attachments/491108547606609945/592225302826975233/w.png");
    private Image hangman8 = new Image("https://cdn.discordapp.com/attachments/491108547606609945/592226890698457118/5.png");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BackgroundImage BI = new BackgroundImage(
                new Image("https://media.discordapp.net/attachments/491108547606609945/592207086964703253/621153.png?width=994&height=560"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
        container.setBackground(new Background(BI));
        hangmanImage.setImage(hangman1);
        GameWord[] gameWords = new GameWord[]{
                new GameWord("Elephant", "Smart animal that has an incredible memory"),
                new GameWord("Mitochondria", "Powerhouse of the cell"),
                new GameWord("Hamburger", "A famous fast-food dish"),
                new GameWord("Awkward","The lull in the conversation was _____ "),
                new GameWord("Fishhook", "Found at the end of a fishing rod"),
                new GameWord("Numbskull", "A stupid or foolish person"),
                new GameWord("Oxygen", "A necessary element for all human life"),
                new GameWord("Rhythmic", "Marked by or moving in pronounced rhythm"),
                new GameWord("Zombie", "An undead human being"),
                new GameWord("Xylophone", "A musical instrument"),
                new GameWord("Haphazard", "Lacking any obvious principle of organization"),
                new GameWord("Bagpipes", "A musical instrument, mostly played in Scotland and Ireland"),
                new GameWord("Oxymoron", "A figure of speech in which apparently contradictory terms appear in conjunction"),
                new GameWord("Superficiality", "Lack of thoroughness, depth of character, or serious thought."),
                new GameWord("Buckaroo", "A cowboy"),
                new GameWord("Espionage", "The practice of spying or of using spies"),
                new GameWord("Witchcraft", "A practice magic, especially black magic"),
                new GameWord("Rickshaw", "A light two-wheeled hooded vehicle drawn by one or more people, used chiefly in Asian countries."),
                new GameWord("Pneumonia", "An infection of the lungs caused by bacteria, viruses, fungi, or parasites"),
                new GameWord("Jawbreaker", "A famous large, hard, spherical candy"),
                new GameWord("Grogginess", "A state of feeling dizzy and confused"),
                new GameWord("Bandwagon", "When you became a Raptors fan when GSW lost in NBA finals"),
                new GameWord("Embezzle", "Stealing money that belongs to others"),
                new GameWord("Zigzagging", "Moving in a winding motion"),
                new GameWord("Razzmatazz", "Another term for razzle-dazzle"),
                new GameWord("Schnapps", "A strong alcoholic drink resembling gin and often flavoured with fruit"),
                new GameWord("Whizzing", "Move quickly through the air with a whistling or whooshing sound"),
                new GameWord("Triphthong", "A union of three vowels pronounced in one syllable"),
                new GameWord("Snazzy", "Stylish or attractive"),
                new GameWord("Mnemonic", "A device such as a pattern of letters, ideas, or associations that assists in remembering"),
                new GameWord("Larynx", "Another name for the voice box"),
                new GameWord("Marquis", "A nobleman ranking above a count and below a duke"),
                new GameWord("Giaour", "A non-muslim, especially a Christian"),
                new GameWord("Iatrogenic", "Relating to illness caused by medical examination or treatment"),
                new GameWord("Quixotic", "Exceedingly idealistic, unrealistic and impractical"),
                new GameWord("Thriftless", "Spending money in an extravagant and wasteful way"),
                new GameWord("Pakistan", "What the educated persons of society call 'PakiLand'")
        };
        gameWord = gameWords[(int) (Math.random()*gameWords.length)];
        for(int i = 0; i < gameWord.getLength(); i++){
            Label label = new Label(" _ ");
            if(i==0){
                label.setText(" _ ");
            } else {
                label.setText("_ ");
            }
            label.setStyle("-fx-font-size: 48;");
            label.setTextFill(Paint.valueOf("white"));
            label.setId("letter"+i);
            containerForGameWord.getChildren().add(label);
        }
    }

    public void characterChosen (ActionEvent e) throws Exception {
        Button button = (Button) e.getSource();
        String letter = button.getText();
        if (gameWord.getWord().toLowerCase().contains(letter.toLowerCase())){
            int position = gameWord.getWord().toLowerCase().indexOf(letter.toLowerCase());
            while (position >= 0){
                ((Label) containerForGameWord.getChildren().get(position)).setText(
                        position == 0 ? letter : letter.toLowerCase()
                );
                position = gameWord.getWord().toLowerCase().indexOf(letter.toLowerCase(), position + 1);
            }
            boolean won = true;
            for(Node child: containerForGameWord.getChildren()){
                if(((Label) child).getText().contains("_")){
                    won = false;
                }
            }
            if (won) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Hangman: Winner!");
                alert.setHeaderText(null);
                alert.setContentText("Congrats! You Guessed the Word Correctly!");
                alert.showAndWait();
                getHangMan();
            }
            button.setStyle("-fx-background-color: #5cb85c");
        } else {
            chances.setText(
                    "" + (Integer.parseInt(chances.getText())-1)
            );
            switch (currImage){
                case "hangman1":
                    currImage = "hangman2";
                    hangmanImage.setImage(hangman2);
                    break;
                case "hangman2":
                    currImage = "hangman3";
                    hangmanImage.setImage(hangman3);
                    break;
                case "hangman3":
                    currImage = "hangman4";
                    hangmanImage.setImage(hangman4);
                    break;
                case "hangman4":
                    currImage = "hangman5";
                    hangmanImage.setImage(hangman5);
                    break;
                case "hangman5":
                    currImage = "hangman6";
                    hangmanImage.setImage(hangman6);
                    break;
                case "hangman6":
                    currImage = "hangman7";
                    hangmanImage.setImage(hangman7);
                    break;
                case "hangman7":
                    currImage = "hangman8";
                    hangmanImage.setImage(hangman8);
                    break;
                case "hangman8":
                    break;
            }
            if(Integer.parseInt(chances.getText()) == 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Hangman: Game Over");
                alert.setHeaderText("Sorry you Lost. Try again next time.");
                alert.setContentText("The word was " + gameWord.getWord());
                alert.showAndWait();
                getHangMan();
            }
            button.setStyle("-fx-background-color: red;");
        }
        button.setDisable(true);
    }
    public void getHint(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Hangman: Hint!");
        alert.setHeaderText("The Length of the word is: " + gameWord.getLength());
        alert.setContentText(this.gameWord.getHint()+".");
        alert.showAndWait();
    }
}

class GameWord {
    private String word;
    private String hint;
    public GameWord(String word, String hint) {
        this.word = word;
        this.hint = hint;
    }
    public int getLength(){
        return word.length();
    }
    public String getCharAt(int n){
        return Character.toString(word.charAt(n+1));
    }
    public String getWord() {
        return word;
    }
    public String getHint() {
        return hint;
    }
}
