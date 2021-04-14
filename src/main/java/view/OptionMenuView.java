package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class OptionMenuView extends BorderPane {

    private static final int TITLE_FONT_SIZE = 64;
    private static final int BUTTON_FONT_SIZE = 24;

    public OptionMenuView(Stage currentStage) {
        super();
        if(currentStage.equals(DDventureView.getInstance().getPrimaryStage())) {
            setPrefSize(DDventureView.PRIMARY_STAGE_WIDTH, DDventureView.PRIMARY_STAGE_HEIGHT);
        } else {
            setPrefSize(800, 600);
        }
        Font buttonFont = Font.font("Alagard", BUTTON_FONT_SIZE);

        // set top section
        Label titleLabel = new Label("Opzioni");
        titleLabel.setFont(Font.font("Alagard", TITLE_FONT_SIZE));
        titleLabel.setTextFill(Color.WHITE);
        setTop(titleLabel);

        BorderPane.setMargin(titleLabel, new Insets(30));
        BorderPane.setAlignment(titleLabel, Pos.CENTER);

        // set center section
        VBox centerBox = new VBox();
        centerBox.setSpacing(30);
        centerBox.setAlignment(Pos.CENTER);

        Button musicButton = new Button("Musica: " + (DDventureView.getInstance().getMusicManager().isPlaying() ? "On" : "Off"));
        musicButton.setFont(buttonFont);
        musicButton.setMinWidth(320);
        musicButton.setOnAction(event -> {
            if (DDventureView.getInstance().getMusicManager().isPlaying())
                DDventureView.getInstance().getMusicManager().stopMusic();
            else
                DDventureView.getInstance().getMusicManager().playMusic();
            musicButton.setText("Musica: " + (DDventureView.getInstance().getMusicManager().isPlaying() ? "On" : "Off"));
        });

        Button soundEffectsButton = new Button("Effetti sonori: Off");
        soundEffectsButton.setFont(buttonFont);
        soundEffectsButton.setMinWidth(320);

        centerBox.getChildren().addAll(musicButton, soundEffectsButton);
        setCenter(centerBox);

        // set bottom section
        AnchorPane bottomPane = new AnchorPane();

        Button guideButton = new Button("Guida");
        guideButton.setFont(buttonFont);
        guideButton.setOnAction(event -> DDventureView.getInstance().createAnOpenGuideScene(currentStage));

        Button backToMainMenuButton = new Button("Applica e torna al menu");
        backToMainMenuButton.setFont(buttonFont);
        backToMainMenuButton.setOnAction(event -> {
            if(currentStage.equals(DDventureView.getInstance().getPrimaryStage()))
                DDventureView.getInstance().createAnOpenMainMenuScene();
            else if(currentStage.equals(DDventureView.getInstance().getSecondaryStage()))
                DDventureView.getInstance().createAnOpenPauseMenu();
        });

        bottomPane.getChildren().addAll(guideButton, backToMainMenuButton);
        AnchorPane.setLeftAnchor(guideButton, 10.0);
        AnchorPane.setBottomAnchor(guideButton, 10.0);
        AnchorPane.setBottomAnchor(backToMainMenuButton, 10.0);
        AnchorPane.setRightAnchor(backToMainMenuButton, 10.0);

        this.setBottom(bottomPane);
    }
}
