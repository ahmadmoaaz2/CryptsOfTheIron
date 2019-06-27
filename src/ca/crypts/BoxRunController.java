package ca.crypts;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BoxRunController extends Controller {
    @FXML
    public FlowPane container2;
    private int playerCount = 1;
    private Sprite player = new Sprite(100, 200, 10, 10, "player", Color.YELLOW);
    private Sprite enemy1 = new Sprite(700, 600, 10, 10, "npc", Color.RED);
    private Sprite enemy2 = new Sprite(100, 600, 10, 10, "npc2", Color.AQUAMARINE);
    private Sprite enemy3 = new Sprite(1100, 600, 10, 10, "npc3", Color.LIGHTGREEN);
    private Sprite player2 = new Sprite(1100, 200, 10, 10, "player2", Color.PURPLE);
    private double time = 0;
    private AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            time += 0.15;
            if (time > 0.6) {
                if(playerCount == 2) container.getChildren().add(new Sprite(player2.getTranslateX() + 2.5, player2.getTranslateY() + 2.5, 5, 5, "playerTrail", Color.PURPLE));
                container.getChildren().add(new Sprite(player.getTranslateX() + 2.5, player.getTranslateY() + 2.5, 5, 5, "playerTrail", Color.YELLOW));
                container.getChildren().add(new Sprite(enemy1.getTranslateX() + 2.5, enemy1.getTranslateY() + 2.5, 5, 5, "enemyTrail", Color.RED));
                container.getChildren().add(new Sprite(enemy2.getTranslateX() + 2.5, enemy2.getTranslateY() + 2.5, 5, 5, "enemy2Trail", Color.AQUAMARINE));
                container.getChildren().add(new Sprite(enemy3.getTranslateX() + 2.5, enemy3.getTranslateY() + 2.5, 5, 5, "enemy3Trail", Color.LIGHTGREEN));
                time = 0;
            }
            List<Sprite> entities = new ArrayList<>();
            container.getChildren()
                    .stream()
                    .filter(Node::isVisible)
                    .map(e -> (Sprite) e)
                    .filter(e -> e.getType().contains("Trail"))
                    .forEach(entities::add);
            Sprite[] checkDead;
            if (playerCount == 2)  checkDead = new Sprite[]{player, enemy1, enemy2, enemy3, player2};
            else checkDead = new Sprite[]{player, enemy1, enemy2, enemy3};
            for (Sprite trail : entities) {
                for (Sprite person : checkDead) {
                    switch (person.getDirection()) {
                        case "right":
                            Sprite testRect = new Sprite(person.getTranslateX() + 10, person.getTranslateY(), 1, 5, "test", Color.CORAL);
                            if (testRect.getBoundsInParent().intersects(trail.getBoundsInParent())) {
                                person.setAlive(false);
                            }
                            break;
                        case "left":
                            Sprite testRect2 = new Sprite(person.getTranslateX(), person.getTranslateY(), 1, 5, "test", Color.CORAL);
                            if (testRect2.getBoundsInParent().intersects(trail.getBoundsInParent())) {
                                person.setAlive(false);
                            }
                            break;
                        case "up":
                            Sprite testRect3 = new Sprite(person.getTranslateX(), person.getTranslateY(), 5, 1, "test", Color.CORAL);
                            if (testRect3.getBoundsInParent().intersects(trail.getBoundsInParent())) {
                                person.setAlive(false);
                            }
                            break;
                        case "down":
                            Sprite testRect4 = new Sprite(person.getTranslateX(), person.getTranslateY() + 10, 5, 1, "test", Color.CORAL);
                            if (testRect4.getBoundsInParent().intersects(trail.getBoundsInParent())) {
                                person.setAlive(false);
                            }
                            break;
                    }
                }
            }
            for(Sprite entity: checkDead) {
                if (entity.getBoundsInParent().getMinX() <= 0 || entity.getBoundsInParent().getMaxX() >= 1200
                        || entity.getBoundsInParent().getMinY() <= 0 || entity.getBoundsInParent().getMaxY() >= 700) {
                    entity.setAlive(false);
                }
            }
            if (player.getBoundsInParent().intersects(enemy1.getBoundsInParent())) {
                player.setAlive(false);
                if (playerCount == 2) enemy1.setAlive(false);
            }
            if (player.getBoundsInParent().intersects(enemy2.getBoundsInParent())) {
                player.setAlive(false);
                if (playerCount == 2) enemy2.setAlive(false);
            }
            if (player.getBoundsInParent().intersects(enemy3.getBoundsInParent())) {
                player.setAlive(false);
                if (playerCount == 2) enemy3.setAlive(false);
            }
            if (enemy2.getBoundsInParent().intersects(enemy1.getBoundsInParent())) {
                enemy2.setAlive(false);
                enemy1.setAlive(false);
            }
            if (enemy2.getBoundsInParent().intersects(enemy3.getBoundsInParent())) {
                enemy2.setAlive(false);
                enemy3.setAlive(false);
            }
            if (enemy3.getBoundsInParent().intersects(enemy1.getBoundsInParent())) {
                enemy3.setAlive(false);
                enemy1.setAlive(false);
            }
            if (playerCount == 2) {
                if (player2.getBoundsInParent().intersects(enemy1.getBoundsInParent())) {
                    player2.setAlive(false);
                    enemy1.setAlive(false);
                }
                if (player2.getBoundsInParent().intersects(enemy2.getBoundsInParent())) {
                    player2.setAlive(false);
                    enemy2.setAlive(false);
                }
                if (player2.getBoundsInParent().intersects(enemy3.getBoundsInParent())) {
                    player2.setAlive(false);
                    enemy3.setAlive(false);
                }
            }
            if (playerCount == 2) {
                try {
                    checkEnd(player, enemy1, enemy2, enemy3, player2);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else {
                try {
                    checkEnd(player, enemy1, enemy2, enemy3);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            entities.add(player);
            if (playerCount == 2) entities.add(player2);
            entities.add(enemy2);
            entities.add(enemy3);
            if (time > 0.45) {
                for (Sprite enemy: new Sprite[]{enemy1, enemy2, enemy3}) {
                    if (enemy.getType().equals("npc2")) {
                        entities.remove(enemy2);
                        entities.add(enemy1);
                    } else if (enemy.getType().equals("npc3")){
                        entities.remove(enemy3);
                        entities.add(enemy2);
                    }
                    for (Sprite entity : entities) {
                        Rectangle rightTest = new Rectangle(enemy.getTranslateX() + 10, enemy.getTranslateY(), 20, 20);
                        Rectangle topTest = new Rectangle(enemy.getTranslateX(), enemy.getTranslateY()-35, 20, 35);
                        Rectangle downTest = new Rectangle(enemy.getTranslateX(), enemy.getTranslateY() + 10, 20, 20);
                        Rectangle leftTest = new Rectangle(enemy.getTranslateX() - 35, enemy.getTranslateY(), 35, 20);
                        switch (enemy.getDirection()) {
                            case "right":
                                if (rightTest.intersects(entity.getBoundsInParent())
                                        && topTest.intersects(entity.getBoundsInParent())) {
                                    enemy.moveDown();
                                } else if (rightTest.intersects(entity.getBoundsInParent())
                                        && downTest.intersects(entity.getBoundsInParent())) {
                                    enemy.moveUp();
                                } else if (rightTest.intersects(entity.getBoundsInParent())) {
                                    String direction = Math.random() < 0.5 ? "up" : "down";
                                    if (direction.equals("up")) enemy.moveUp();
                                    else enemy.moveDown();
                                } else if (enemy.getBoundsInParent().getMaxX() >= 1180
                                        && enemy.getBoundsInParent().getMinY() <= 20) {
                                    enemy.moveDown();
                                } else if (enemy.getBoundsInParent().getMaxX() >= 1180 &&
                                        enemy.getBoundsInParent().getMaxY() >= 680) {
                                    enemy.moveUp();
                                } else if (enemy.getBoundsInParent().getMaxX() >= 1180
                                        && downTest.intersects(entity.getBoundsInParent())) {
                                    enemy.moveUp();
                                } else if (enemy.getBoundsInParent().getMaxX() >= 1180
                                        && topTest.intersects(entity.getBoundsInParent())) {
                                    enemy.moveDown();
                                } else if (enemy.getBoundsInParent().getMaxX() >= 1180) {
                                    String direction = Math.random() < 0.5 ? "up" : "down";
                                    if (direction.equals("up")) enemy.moveUp();
                                    else enemy.moveDown();
                                }
                                break;
                            case "left":
                                if (leftTest.intersects(entity.getBoundsInParent())
                                        && topTest.intersects(entity.getBoundsInParent())) {
                                    enemy.moveDown();
                                } else if (leftTest.intersects(entity.getBoundsInParent())
                                        && downTest.intersects(entity.getBoundsInParent())) {
                                    enemy.moveUp();
                                } else if (leftTest.intersects(entity.getBoundsInParent())) {
                                    String direction = Math.random() > 0.5 ? "up" : "down";
                                    if (direction.equals("up")) enemy.moveUp();
                                    else enemy.moveDown();
                                } else if (enemy.getBoundsInParent().getMinX() <= 20
                                        && enemy.getBoundsInParent().getMinY() <= 20) {
                                    enemy.moveDown();
                                } else if (enemy.getBoundsInParent().getMinX() <= 20 &&
                                        enemy.getBoundsInParent().getMaxY() >= 680) {
                                    enemy.moveUp();
                                } else if (enemy.getBoundsInParent().getMinX() <= 20
                                        && topTest.intersects(entity.getBoundsInParent())) {
                                    enemy.moveDown();
                                } else if (enemy.getBoundsInParent().getMinX() <= 20
                                        && downTest.intersects(entity.getBoundsInParent())) {
                                    enemy.moveUp();
                                } else if (enemy.getBoundsInParent().getMinX() <= 20) {
                                    String direction = Math.random() < 0.5 ? "up" : "down";
                                    if (direction.equals("up")) enemy.moveUp();
                                    else enemy.moveDown();
                                }
                                break;
                            case "up":
                                if (rightTest.intersects(entity.getBoundsInParent())
                                        && topTest.intersects(entity.getBoundsInParent())) {
                                    enemy.moveLeft();
                                } else if (leftTest.intersects(entity.getBoundsInParent())
                                        && topTest.intersects(entity.getBoundsInParent())) {
                                    enemy.moveRight();
                                } else if (topTest.intersects(entity.getBoundsInParent())) {
                                    String direction = Math.random() > 0.5 ? "left" : "right";
                                    if (direction.equals("left")) enemy.moveLeft();
                                    else enemy.moveRight();
                                } else if (enemy.getBoundsInParent().getMinX() <= 20
                                        && enemy.getBoundsInParent().getMinY() <= 20) {
                                    enemy.moveRight();
                                } else if (enemy.getBoundsInParent().getMaxX() >= 1180 &&
                                        enemy.getBoundsInParent().getMinY() <= 20) {
                                    enemy.moveLeft();
                                } else if (enemy.getBoundsInParent().getMinY() <= 20
                                        && downTest.intersects(entity.getBoundsInParent())) {
                                    enemy.moveUp();
                                } else if (enemy.getBoundsInParent().getMinY() <= 20
                                        && topTest.intersects(entity.getBoundsInParent())) {
                                    enemy.moveDown();
                                } else if (enemy.getBoundsInParent().getMinY() <= 20) {
                                    String direction = Math.random() > 0.5 ? "left" : "right";
                                    if (direction.equals("left")) enemy.moveLeft();
                                    else enemy.moveRight();
                                }
                                break;
                            case "down":
                                if (rightTest.intersects(entity.getBoundsInParent())
                                        && downTest.intersects(entity.getBoundsInParent())) {
                                    enemy.moveLeft();
                                } else if (leftTest.intersects(entity.getBoundsInParent())
                                        && downTest.intersects(entity.getBoundsInParent())) {
                                    enemy.moveRight();
                                } else if (downTest.intersects(entity.getBoundsInParent())) {
                                    String direction = Math.random() < 0.5 ? "left" : "right";
                                    if (direction.equals("right")) enemy.moveRight();
                                    else enemy.moveLeft();
                                } else if (enemy.getBoundsInParent().getMinX() <= 20
                                        && enemy.getBoundsInParent().getMaxY() >= 680) {
                                    enemy.moveRight();
                                } else if (enemy.getBoundsInParent().getMaxX() >= 880 &&
                                        enemy.getBoundsInParent().getMaxY() >= 680) {
                                    enemy.moveLeft();
                                } else if (enemy.getBoundsInParent().getMaxY() >= 680
                                        && downTest.intersects(entity.getBoundsInParent())) {
                                    enemy.moveUp();
                                } else if (enemy.getBoundsInParent().getMaxY() >= 680
                                        && topTest.intersects(entity.getBoundsInParent())) {
                                    enemy.moveDown();
                                } else if (enemy.getBoundsInParent().getMaxY() >= 680) {
                                    String direction = Math.random() > 0.5 ? "left" : "right";
                                    if (direction.equals("left")) enemy.moveLeft();
                                    else enemy.moveRight();
                                }
                                break;
                        }
                    }
                }
            }
            ArrayList<Sprite> movementEntities = new ArrayList<>();
            Arrays.stream(checkDead)
                    .filter(Sprite::isAlive)
                    .forEach(movementEntities::add);
            for (Sprite person : movementEntities) {
                switch (person.getDirection()) {
                    case "right":
                        person.moveRightHandle();
                        break;
                    case "left":
                        person.moveLeftHandle();
                        break;
                    case "up":
                        person.moveUpHandle();
                        break;
                    case "down":
                        person.moveDownHandle();
                        break;
                }
            }
        }
    };

    public void howToPlay() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("How To Play");
        alert.setHeaderText("How To Play Box Run");
        alert.setContentText("Player 1: Yellow. \nPress 'W' to Move Up. \nPress 'S' to Move Down." +
                "\nPress 'A' to Move Right. \nPress 'D' to Move Left \nPlayer 2: Purple: " +
                "\nPress 'UP' Arrow Key to Move Up \nPress 'DOWN' Arrow Key to Move Down " +
                "\nPress 'RIGHT' Arrow Key to Move Right \nPress 'LEFT' Arrow Key to Move Left" +
                "\nDefeat the other boxes by making them hit your trail");
        alert.showAndWait();
    }

    public void startGame() {
        getPrimaryStage().setWidth(1210);
        getPrimaryStage().setHeight(735);
        container2.setVisible(false);
        container.setBackground(null);
        container.getChildren().add(player);
        container.getChildren().add(enemy1);
        container.getChildren().add(enemy2);
        container.getChildren().add(enemy3);
        timer.start();
        getPrimaryStage().getScene().setOnKeyPressed(e -> {
            new Thread(() -> {
                try {
                    Thread.sleep(125);
                } catch (InterruptedException ee) {
                    System.out.println(ee.getMessage());
                }
                switch (e.getCode()) {
                    case A:
                        player.moveLeft();
                        break;
                    case D:
                        player.moveRight();
                        break;
                    case W:
                        player.moveUp();
                        break;
                    case S:
                        player.moveDown();
                        break;
                }
            }).start();
        });
    }

    public void startGame2() {
        getPrimaryStage().setWidth(1210);
        getPrimaryStage().setHeight(735);
        container2.setVisible(false);
        container.setBackground(null);
        playerCount = 2;
        container.getChildren().add(player);
        container.getChildren().add(enemy1);
        container.getChildren().add(enemy2);
        container.getChildren().add(enemy3);
        container.getChildren().add(player2);
        timer.start();
        getPrimaryStage().getScene().setOnKeyPressed(e -> {
            new Thread(() -> {
                try {
                    Thread.sleep(125);
                } catch (InterruptedException ee) {
                    System.out.println(ee.getMessage());
                }
                switch (e.getCode()) {
                    case A:
                        player.moveLeft();
                        break;
                    case D:
                        player.moveRight();
                        break;
                    case W:
                        player.moveUp();
                        break;
                    case S:
                        player.moveDown();
                        break;
                    case LEFT:
                        player2.moveLeft();
                        break;
                    case RIGHT:
                        player2.moveRight();
                        break;
                    case UP:
                        player2.moveUp();
                        break;
                    case DOWN:
                        player2.moveDown();
                        break;
                }
            }).start();
        });
    }

    public void checkEnd(Sprite player, Sprite enemy1, Sprite enemy2, Sprite enemy3) throws Exception {
        if (!player.isAlive()) {
            timer.stop();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Box Run: Game Over!");
            alert.setHeaderText("Sorry you died.");
            alert.setContentText("Game Over! Please play again.");
            alert.show();
            getBoxRun();
        }
        if (!enemy1.isAlive() && !enemy2.isAlive() && !enemy3.isAlive()) {
            timer.stop();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Box Run: Victory!");
            alert.setHeaderText("Enemy has died");
            alert.setContentText("You Win!");
            alert.show();
            getBoxRun();
        }
    }

    public void checkEnd(Sprite player, Sprite enemy1, Sprite enemy2, Sprite enemy3, Sprite player2) throws Exception {
        if (!player.isAlive() && !player2.isAlive()) {
            timer.stop();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Box Run: LOSS!");
            alert.setHeaderText("YOU FUCKING DIED BITCH");
            alert.setContentText("SHUT THE FUCK UP AND LEAVE THIS GAME!");
            alert.show();
            getBoxRun();
        }
        if (player.isAlive() && !player2.isAlive() && !enemy1.isAlive() && !enemy2.isAlive() && !enemy3.isAlive()) {
            timer.stop();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Box Run: Player 1 Victory!");
            alert.setHeaderText("YOU FUCKING DIED BITCH (Player 2)");
            alert.setContentText("SHUT THE FUCK UP AND LEAVE THIS GAME! (Player 2)");
            alert.show();
            getBoxRun();
        }
        if (!player.isAlive() && player2.isAlive() && !enemy1.isAlive() && !enemy2.isAlive() && !enemy3.isAlive()) {
            timer.stop();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Box Run: Player 2 Victory!");
            alert.setHeaderText("YOU FUCKING DIED BITCH (Player 1)");
            alert.setContentText("SHUT THE FUCK UP AND LEAVE THIS GAME! (Player1)");
            alert.show();
            getBoxRun();
        }
    }

    public static class Sprite extends Rectangle {
        private boolean alive = true;
        private final String type;
        private final static int velocity = 2;
        private String direction;

        public Sprite(double x, double y, double w, double h, String type, Color color) {
            super(w, h, color);
            this.type = type;
            setTranslateX(x);
            setTranslateY(y);
            if (type.equals("player")) {
                direction = "right";
            } else if (type.equals("player2")) {
                direction = "left";
            } else{
                direction = Math.random() > 0.5 ? Math.random() > 0.5 ? "left" : "right" : Math.random() < 0.5 ? "up" : "down";
            }
        }

        public void moveLeft() {
            if (!direction.equals("right")) direction = "left";
        }

        public void moveRight() {
            if (!direction.equals("left")) direction = "right";
        }

        public void moveUp() {
            if (!direction.equals("down")) direction = "up";
        }

        public void moveDown() {
            if (!direction.equals("up")) direction = "down";
        }

        public void moveDownHandle() {
            setTranslateY(getTranslateY() + velocity);
        }

        public void moveLeftHandle() {
            setTranslateX(getTranslateX() - velocity);
        }

        public void moveRightHandle() {
            setTranslateX(getTranslateX() + velocity);
        }

        public void moveUpHandle() {
            setTranslateY(getTranslateY() - velocity);
        }

        public String getDirection() {
            return this.direction;
        }

        public boolean isAlive() {
            return alive;
        }

        public void setAlive(boolean alive) {
            this.alive = alive;
        }

        public String getType() {
            return type;
        }
    }
}
