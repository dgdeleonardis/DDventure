package view;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.transform.Scale;
import javafx.util.Duration;
import logic.CharacterSprite;
import logic.DDventureLogic;

import java.security.Timestamp;

import static sun.misc.PostVMInitHook.run;

public class AnimationPane extends BorderPane {

    private Circle circle;
    private ImageView animationView;
    private Rectangle diceSquare;
    private Label diceNumberLabel;
    private HBox buttonsBox;
    private Button startButton;
    private Label resultLabel;
    private boolean flagBreakCAAnimation;

    class DiceRollAnimation extends AnimationTimer {

        private final int maxValue;
        private volatile boolean running;

        public DiceRollAnimation(int maxValue) {
            this.maxValue = maxValue;
        }

        @Override
        public void start() {
            super.start();
            running = true;
        }

        @Override
        public void stop() {
            super.stop();
            running = false;
        }

        @Override
        public void handle(long now) {
            int num = (int) (Math.random() * (maxValue + 1));
            diceNumberLabel.setText(Integer.toString(num));
        }
    };

    public AnimationPane(MapView map) {

        // center section
        circle = new Circle();
        circle.setRadius(140);
        circle.setStroke(Color.BLACK);
        circle.setFill(Color.TRANSPARENT);

        flagBreakCAAnimation = true;

        animationView = new ImageView();
        animationView.setVisible(false);

        diceSquare = new Rectangle(128, 128, Color.RED);
        diceSquare.setVisible(false);

        diceNumberLabel = new Label();
        diceNumberLabel.setFont(Font.font("Alagard", 32));
        diceNumberLabel.setVisible(false);

        resultLabel = new Label("Attacco schivato!");
        resultLabel.setFont(Font.font("Alagard",24));
        resultLabel.setAlignment(Pos.CENTER);
        resultLabel.setVisible(false);

        DiceRollAnimation breakCARoll = new DiceRollAnimation(20);
        DiceRollAnimation damageRoll = new DiceRollAnimation(DDventureLogic.getInstance().getTurnCharacterInGame().getWeapon().getDamageDice());

        StackPane stack = new StackPane(animationView, circle, diceSquare, diceNumberLabel, resultLabel);
        stack.setAlignment(Pos.CENTER);

        this.setCenter(stack);

        //bottom section
        Button startButton = new Button("Lancia dado");
        startButton.setFont(Font.font("Alagard", 16));
        Button stopButton = new Button("Ferma dado");
        stopButton.setFont(Font.font("Alagard", 16));
        buttonsBox = new HBox(startButton, stopButton);
        buttonsBox.setVisible(false);
        buttonsBox.setSpacing(30.0);
        buttonsBox.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(buttonsBox, Pos.CENTER);


        this.setBottom(buttonsBox);
        startButton.setOnAction(event -> {
            resultLabel.setVisible(false);
            openRollDiceAnimation();
            if(flagBreakCAAnimation) {
                breakCARoll.start();
            } else {
                damageRoll.start();
                flagBreakCAAnimation = true;
            }
            startButton.setVisible(true);
        });

        stopButton.setOnAction(event -> {
            closeRollDiceAnimation();
            if(breakCARoll.running) {
                breakCARoll.stop();
                animationView.setVisible(true);
                int result = Integer.parseInt(diceNumberLabel.getText());
                if(DDventureLogic.getInstance().isCABreak(result)) {
                    //
                    resultLabel.setText("Difesa rotta!");
                    buttonsBox.setVisible(true);
                    flagBreakCAAnimation = false;
                    //
                } else {
                    resultLabel.setText("Attacco schivato!");
                }
                resultLabel.setVisible(true);
            } else  if(damageRoll.running) {
                int result = Integer.parseInt(diceNumberLabel.getText());
                DDventureLogic.getInstance().damageControll(result);

                CharacterSprite sprite = DDventureLogic.getInstance().getTurnCharacterInGameSprite();
                SpriteAnimation spriteAnimation = new SpriteAnimation(animationView, new Duration(3000), sprite.getActions().get(0).getFrames());
                animationView.setImage(new Image(getClass().getResourceAsStream("image/sheet/" + sprite.getSheetFileName())));
                animationView.setScaleX(2.0);
                animationView.setScaleY(2.0);

                spriteAnimation.play();

                spriteAnimation.setOnFinished(animationEvent -> {
                    if(DDventureLogic.getInstance().isEnemyDead()) {
                        animationView.setVisible(false);
                        resultLabel.setText("Il nemico e' morto!");
                        resultLabel.setVisible(true);
                    }
                    map.drawMap(DDventureLogic.getInstance().getGame().getMap());
                    map.drawCharactersInGame();
                });
            }






        });
    }

    public void openRollDiceAnimation() {
        circle.setVisible(true);
        diceSquare.setVisible(true);
        diceNumberLabel.setVisible(true);
        buttonsBox.setVisible(true);

    }

    public void closeRollDiceAnimation() {
        diceSquare.setVisible(false);
        buttonsBox.setVisible(false);
        diceNumberLabel.setVisible(false);
    }

    public void clearView() {
        circle.setVisible(false);
        diceSquare.setVisible(false);
        diceNumberLabel.setText("");
        buttonsBox.setVisible(false);
        startButton.setDisable(false);
        resultLabel.setVisible(false);
    }
}
