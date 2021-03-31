package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class OptionMenu extends BorderPane {
    // main pane attributes
    private static final int TITLE_FONT_SIZE = 64;
    private static final int BUTTON_FONT_SIZE = 24;
    private final Stage currentStage;

    // top section attributes
    private final Label titleLabel;

    //center section attributes
    private final VBox centerBox;
    private final Button musicButton;
    private final Button soundEffectsButton;

    // bottom section attributes
    private final AnchorPane bottomPane;
    private final Button guideButton;
    private final Button backToMainMenuButton;

    public OptionMenu(Stage currentStage) {
        super();
        this.currentStage = currentStage;
        if(currentStage.equals(DDventureView.getInstance().getPrimaryStage())) {
            setPrefSize(DDventureView.PRIMARY_STAGE_WIDTH, DDventureView.PRIMARY_STAGE_HEIGHT);
        } else {
            setPrefSize(800, 600);
        }

        // set top section
        this.titleLabel = new Label("Opzioni");

        this.titleLabel.setFont(Font.loadFont(this.getClass().getResourceAsStream(DDventureView.FONT_FILE_NAME), TITLE_FONT_SIZE));

        this.titleLabel.setTextFill(Color.WHITE);

        this.setTop(this.titleLabel);
        BorderPane.setMargin(this.titleLabel, new Insets(30));
        BorderPane.setAlignment(this.titleLabel, Pos.CENTER);

        // set center section
        this.centerBox = new VBox();

        this.centerBox.setSpacing(30);
        this.centerBox.setAlignment(Pos.CENTER);

        Font buttonFont = Font.loadFont(getClass().getResourceAsStream(DDventureView.FONT_FILE_NAME), BUTTON_FONT_SIZE);

        this.musicButton = new Button("Musica: " + (DDventureView.getInstance().getMusicManager().isPlaying() ? "On" : "Off"));
        this.musicButton.setFont(buttonFont);
        this.musicButton.setMinWidth(320);

        this.musicButton.setOnAction(event -> {
            if (DDventureView.getInstance().getMusicManager().isPlaying())
                DDventureView.getInstance().getMusicManager().stopMusic();
            else
                DDventureView.getInstance().getMusicManager().playMusic();
            this.musicButton.setText("Musica: " + (DDventureView.getInstance().getMusicManager().isPlaying() ? "On" : "Off"));
        });

        this.soundEffectsButton = new Button("Effetti sonori: Off");
        this.soundEffectsButton.setFont(buttonFont);
        this.soundEffectsButton.setMinWidth(320);

        this.centerBox.getChildren().addAll(this.musicButton, this.soundEffectsButton);
        this.setCenter(this.centerBox);

        // set bottom section
        this.bottomPane = new AnchorPane();

        this.guideButton = new Button("Guida");
        this.guideButton.setFont(buttonFont);
        this.guideButton.setOnAction(event -> DDventureView.getInstance().createAnOpenGuideScene(currentStage));

        this.backToMainMenuButton = new Button("Applica e torna al menu");
        this.backToMainMenuButton.setFont(buttonFont);
        this.backToMainMenuButton.setOnAction(event -> {
            if(this.currentStage.equals(DDventureView.getInstance().getPrimaryStage()))
                DDventureView.getInstance().createAnOpenMainMenuScene();
            else if(this.currentStage.equals(DDventureView.getInstance().getSecondaryStage()))
                DDventureView.getInstance().createAnOpenPauseMenu();
        });

        this.bottomPane.getChildren().addAll(this.guideButton, this.backToMainMenuButton);
        AnchorPane.setLeftAnchor(this.guideButton, 10.0);
        AnchorPane.setBottomAnchor(this.guideButton, 10.0);
        AnchorPane.setBottomAnchor(this.backToMainMenuButton, 10.0);
        AnchorPane.setRightAnchor(this.backToMainMenuButton, 10.0);

        this.setBottom(this.bottomPane);
    }
}
