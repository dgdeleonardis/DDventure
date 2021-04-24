package view;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.transform.Scale;
import javafx.util.Duration;
import logic.DDventureLogic;


public class InitiativeView extends BorderPane {

    final static String ROLL_DICE_IMAGE = "image/r09.png";
    
    public InitiativeView() {
        super();
        setPrefSize(DDventureView.PRIMARY_STAGE_WIDTH - 30, DDventureView.PRIMARY_STAGE_HEIGHT - 30);
        // top section
        Label titleLabel = new Label("Calcolo dell'Iniziativa");
        titleLabel.setFont(Font.font("Alagard", 64));
        setTop(titleLabel);
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        BorderPane.setMargin(titleLabel, new Insets(30));

        // center section
        Button startCalculationButton = new Button("Calcola");
        startCalculationButton.setFont(Font.font("Alagard", 24));
        setCenter(startCalculationButton);
        BorderPane.setAlignment(startCalculationButton, Pos.CENTER);

        Button goToGameButton = new Button("Inizia la partita");
        goToGameButton.setFont(Font.font("Alagard", 24));
        goToGameButton.setVisible(false);
        goToGameButton.setOnAction(event -> {
            DDventureView.getInstance().createAnOpenGameScene();
        });
        this.setBottom(goToGameButton);

        startCalculationButton.setOnAction(event -> {
            DDventureLogic.getInstance().characterTurnOrder();
            // change center section
            Image rollingDiceImage = new Image(getClass().getResourceAsStream(ROLL_DICE_IMAGE), 3072, 256, true, false);
            ImageView rollingDiceView = new ImageView(rollingDiceImage);
            rollingDiceView.setViewport(new Rectangle2D(0, 0, 256, 256));

            DiceAnimation diceAnimation  = new DiceAnimation(12, rollingDiceView, new Duration(1000));
            diceAnimation.setOnFinished(animationEvent -> {
                VBox characterInGameList = new VBox();
                characterInGameList.setSpacing(30);
                characterInGameList.setAlignment(Pos.CENTER);
                DDventureLogic.getInstance().characterTurnOrder().forEach( c -> {
                    Label nameLabel = new Label(c.getName() + " - " + c.getInitiative());
                    nameLabel.setFont(Font.font("Alagard", 32));
                    characterInGameList.getChildren().add(nameLabel);
                });
                setCenter(characterInGameList);
                BorderPane.setAlignment(characterInGameList, Pos.CENTER);
                goToGameButton.setVisible(true);

            });
            Label loadingLabel = new Label("Calcolo in corso...");
            loadingLabel.setFont(Font.font("Alagard", 24));

            VBox loadingInitiativeBox = new VBox(rollingDiceView, loadingLabel);
            loadingInitiativeBox.setAlignment(Pos.CENTER);
            loadingInitiativeBox.setSpacing(30);
            setCenter(loadingInitiativeBox);
            BorderPane.setAlignment(loadingInitiativeBox, Pos.CENTER);
            diceAnimation.play();
        });
    }
}
