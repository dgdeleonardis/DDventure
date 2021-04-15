package view;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import logic.DDventureLogic;

public class TeamCreationMenuView extends BorderPane {

    private static final int TITLE_FONT_SIZE = 64;
    private static final int BUTTON_FONT_SIZE = 24;
    private static final int COMBO_BOX_FONT_SIZE = 16;
    private final static int GENERIC_SPACING = 40;
    private final static double CHOICE_BOX_MIN_WIDTH = 120;

    private static final String[] ITEM_NUMBER_OF_TEAM_BOX = {"2", "3", "4", "5", "6"};

    private class TeamCreationItemView extends HBox {

        private final TextField teamNameText;
        private final ChoiceBox<String> teamColorChoiceBox;

        public TeamCreationItemView() {
            super();

            teamNameText = new TextField("Nome schieramento");
            teamNameText.setFont(Font.font("Alagard", BUTTON_FONT_SIZE));

            teamColorChoiceBox = new ChoiceBox<>();
            teamColorChoiceBox.setMinWidth(CHOICE_BOX_MIN_WIDTH);
            teamColorChoiceBox.setStyle("-fx-font: " + COMBO_BOX_FONT_SIZE +" \"Alagard\"");
            teamColorChoiceBox.getItems().addAll(DDventureView.TEAM_COLORS.keySet());

            this.getChildren().addAll(teamNameText, teamColorChoiceBox);
            this.setSpacing(GENERIC_SPACING);
            this.setAlignment(Pos.CENTER);
        }
    }

    public TeamCreationMenuView() {
        super();
        setPrefSize(
                DDventureView.PRIMARY_STAGE_WIDTH - (DDventureView.GAME_PANE_SPACING * 2),
                DDventureView.PRIMARY_STAGE_HEIGHT - (DDventureView.GAME_PANE_SPACING * 2)
        );

        // top section
        Label titleLabel = new Label("Schieramenti");
        titleLabel.setFont(Font.font("Alagard", TITLE_FONT_SIZE));
        this.setTop(titleLabel);
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        BorderPane.setMargin(titleLabel, new Insets(GENERIC_SPACING));

        // left section
        Font buttomFont = Font.font("Alagard", BUTTON_FONT_SIZE);

        Label numberOfTeamLabel = new Label("Numero di schieramenti");
        numberOfTeamLabel.setFont(buttomFont);

        ChoiceBox<String> numberOfTeamChoiceBox = new ChoiceBox<>();
        numberOfTeamChoiceBox.getItems().addAll(ITEM_NUMBER_OF_TEAM_BOX);
        numberOfTeamChoiceBox.setStyle("-fx-font: " + COMBO_BOX_FONT_SIZE +" \"Alagard\"");

        HBox teamHBox = new HBox(numberOfTeamLabel, numberOfTeamChoiceBox);
        teamHBox.setSpacing(GENERIC_SPACING);

        Label errorLabel = new Label("Errore: due o piu' team presentano\nlo stesso nome o colore");
        errorLabel.setFont(buttomFont);
        errorLabel.setTextFill(Color.RED);
        errorLabel.setVisible(false);

        VBox leftBox = new VBox(teamHBox, errorLabel);
        leftBox.setSpacing(GENERIC_SPACING);
        leftBox.setAlignment(Pos.CENTER);
        this.setLeft(leftBox);
        BorderPane.setAlignment(leftBox, Pos.TOP_CENTER);
        BorderPane.setMargin(leftBox, new Insets(GENERIC_SPACING));

        // center section
        VBox teamCreationBox = new VBox();
        teamCreationBox.setAlignment(Pos.CENTER);
        teamCreationBox.setSpacing(GENERIC_SPACING);
        numberOfTeamChoiceBox.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    teamCreationBox.getChildren().clear();
                    int n = Integer.parseInt(newValue);
                    for (int i = 0; i < n; i++) {
                        teamCreationBox.getChildren().add(new TeamCreationItemView());
                    }
                });
        numberOfTeamChoiceBox.getSelectionModel().selectFirst();

        this.setCenter(teamCreationBox);
        BorderPane.setAlignment(numberOfTeamChoiceBox, Pos.CENTER);

        //set bottom section
        Button backToMenuButton = new Button("Indietro");
        backToMenuButton.setFont(buttomFont);
        backToMenuButton.setOnAction(event -> {
            DDventureLogic.getInstance().resetTeams();
            DDventureView.getInstance().createAnOpenMainMenuScene();
        });

        Button confirmButton = new Button("Avanti");
        confirmButton.setFont(buttomFont);
        confirmButton.setOnAction(event -> {
            ObservableList<Node> teamCreationItems = teamCreationBox.getChildren();
            boolean result = true;
            for (int i = 0; i < teamCreationItems.size() && result; i++) {
                TeamCreationItemView item = (TeamCreationItemView) teamCreationItems.get(i);
                String teamName = item.teamNameText.getText();
                String teamColorName = item.teamColorChoiceBox.getValue();

                result = DDventureLogic.getInstance().createTeam(teamName, teamColorName);
            }
            if (result) {
                DDventureView.getInstance().createAnOpenPlayerScene();
            } else {
                DDventureLogic.getInstance().resetTeams();
                errorLabel.setVisible(true);
            }
        });


        AnchorPane bottomPane = new AnchorPane(backToMenuButton, confirmButton);

        AnchorPane.setLeftAnchor(backToMenuButton, 10.0);
        AnchorPane.setBottomAnchor(backToMenuButton, 10.0);

        AnchorPane.setRightAnchor(confirmButton, 10.0);
        AnchorPane.setBottomAnchor(confirmButton, 10.0);

        this.setBottom(bottomPane);
    }
}
