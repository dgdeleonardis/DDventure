package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import javafx.scene.image.Image;
import javafx.scene.text.Font;

public class TeamCreationWindow extends AnchorPane {
    // resources file name
    private static final String BACKGROUND_IMAGE_FILE_NAME = "image/background-hell_gate.png";
    private static final String MAIN_PANE_BACKGROUND_IMAGE_FILE_NAME = "image/background-lether.jpg";
    private static final String FONT_FILE_NAME = "fonts/alagard.tff";

    // general attributes
    private static final int TITLE_FONT_SIZE = 64;
    private static final int BUTTON_FONT_SIZE = 24;
    private static final String[] ITEM_NUMBER_OF_TEAM_BOX = {"2", "3", "4", "5", "6"};

    private final BorderPane mainPane;

    // top section attributes
    private final Label titleLabel;

    // left section attributes
    private final HBox leftBox;

    private final Label numberOfTeamLabel;
    private final ChoiceBox choiceNumberOfTeamBox;

    // center section attributes
    private VBox teamCreationBox;

    // bottom section attributes
    private final AnchorPane bottomPane;

    private final Button backToMenuButton;
    private final Button confirmButton;

    public TeamCreationWindow() {
        super();
        // set background
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(this.getClass().getResourceAsStream(BACKGROUND_IMAGE_FILE_NAME)),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(1.0,
                        1.0,
                        true,
                        true,
                        false,
                        false));
        this.setBackground(new Background(backgroundImage));

        //set of main Pane
        this.mainPane = new BorderPane();

        BackgroundImage letherBackground = new BackgroundImage(
                new Image(this.getClass().getResourceAsStream(MAIN_PANE_BACKGROUND_IMAGE_FILE_NAME)),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(1.0,
                        1.0,
                        true,
                        true,
                        false,
                        false));
        this.mainPane.setBackground(new Background(letherBackground));

        this.getChildren().add(this.mainPane);

        AnchorPane.setTopAnchor(this.mainPane, 30.0);
        AnchorPane.setLeftAnchor(this.mainPane, 30.0);
        AnchorPane.setRightAnchor(this.mainPane, 30.0);
        AnchorPane.setBottomAnchor(this.mainPane, 30.0);

        // set top section
        this.titleLabel = new Label("Schieramenti");
        this.titleLabel.setFont(Font.loadFont(this.getClass().getResourceAsStream(FONT_FILE_NAME), TITLE_FONT_SIZE));

        this.mainPane.setTop(this.titleLabel);
        BorderPane.setAlignment(this.titleLabel, Pos.CENTER);
        BorderPane.setMargin(this.titleLabel, new Insets(30));

        // set of left section
        Font buttomFont = Font.loadFont(this.getClass().getResourceAsStream(FONT_FILE_NAME), BUTTON_FONT_SIZE);

        this.numberOfTeamLabel = new Label("Numero di schieramenti");
        this.numberOfTeamLabel.setFont(buttomFont);

        this.choiceNumberOfTeamBox = new ChoiceBox();
        this.choiceNumberOfTeamBox.getItems().addAll(ITEM_NUMBER_OF_TEAM_BOX);

        this.choiceNumberOfTeamBox.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    teamCreationBox.getChildren().clear();
                    int n = Integer.parseInt(newValue.toString());
                    for (int i = 0; i < n; i++) {
                        teamCreationBox.getChildren().add(new TeamCreationItem());
                    }
                });

        this.leftBox = new HBox(this.numberOfTeamLabel, this.choiceNumberOfTeamBox);
        this.leftBox.setAlignment(Pos.CENTER);
        this.leftBox.setSpacing(30);

        this.mainPane.setLeft(this.leftBox);
        BorderPane.setAlignment(this.leftBox, Pos.TOP_CENTER);
        BorderPane.setMargin(this.leftBox, new Insets(30));

        // set center section
        this.teamCreationBox = new VBox();
        this.teamCreationBox.setAlignment(Pos.CENTER);
        this.teamCreationBox.setSpacing(30);

        this.mainPane.setCenter(this.teamCreationBox);
        BorderPane.setAlignment(this.choiceNumberOfTeamBox, Pos.CENTER);

        //set bottom section
        this.bottomPane = new AnchorPane();

        this.backToMenuButton = new Button("Indietro");
        this.backToMenuButton.setFont(buttomFont);
        this.backToMenuButton.setOnAction(event -> DDventureView.getInstance().createAnOpenMainMenuScene());

        this.confirmButton = new Button("Avanti");
        this.confirmButton.setFont(buttomFont);

        this.bottomPane.getChildren().addAll(this.backToMenuButton, this.confirmButton);
        AnchorPane.setLeftAnchor(this.backToMenuButton, 10.0);
        AnchorPane.setBottomAnchor(this.backToMenuButton, 10.0);
        AnchorPane.setRightAnchor(this.confirmButton, 10.0);
        AnchorPane.setBottomAnchor(this.confirmButton, 10.0);

        this.mainPane.setBottom(this.bottomPane);
    }
}
