package view;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Callback;
import logic.DDventureLogic;

public class TeamCreationMenuView extends BorderPane {

    private static final int TITLE_FONT_SIZE = 64;
    private static final int BUTTON_FONT_SIZE = 24;
    private final static int GENERIC_SPACING = 30;

    private static final String[] ITEM_NUMBER_OF_TEAM_BOX = {"2", "3", "4", "5", "6"};

    private Label errorLabel;

    public TeamCreationMenuView() {
        super();
        setPrefSize(
                DDventureView.PRIMARY_STAGE_WIDTH - (DDventureView.GAME_PANE_SPACING*2),
                DDventureView.PRIMARY_STAGE_HEIGHT - (DDventureView.GAME_PANE_SPACING*2)
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

        ComboBox<String> numberOfTeamComboBox = new ComboBox<>();
        numberOfTeamComboBox.getItems().addAll(ITEM_NUMBER_OF_TEAM_BOX);
        numberOfTeamComboBox.setCellFactory(
                new Callback<ListView<String>, ListCell<String>>() {
                    @Override
                    public ListCell<String> call(ListView<String> param) {
                        return new ListCell<String>() {
                            @Override
                            protected void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                this.setFont(buttomFont);
                            }
                        };
                    }
                }
        );

        HBox teamHBox = new HBox(numberOfTeamLabel, numberOfTeamComboBox);
        teamHBox.setSpacing(GENERIC_SPACING);

        VBox leftBox = new VBox(teamHBox);
        leftBox.setSpacing(GENERIC_SPACING);
        leftBox.setAlignment(Pos.CENTER);
        this.setLeft(leftBox);
        BorderPane.setAlignment(leftBox, Pos.TOP_CENTER);
        BorderPane.setMargin(leftBox, new Insets(GENERIC_SPACING));

        // center section
        VBox teamCreationBox = new VBox();
        teamCreationBox.setAlignment(Pos.CENTER);
        teamCreationBox.setSpacing(GENERIC_SPACING);
        this.setCenter(teamCreationBox);
        BorderPane.setAlignment(numberOfTeamComboBox, Pos.CENTER);

        //set bottom section
        AnchorPane bottomPane = new AnchorPane();

        Button backToMenuButton = new Button("Indietro");
        backToMenuButton.setFont(buttomFont);
        backToMenuButton.setOnAction(event -> DDventureView.getInstance().createAnOpenMainMenuScene());

        Button confirmButton = new Button("Avanti");
        confirmButton.setFont(buttomFont);
        confirmButton.setDisable(true);
        confirmButton.setOnAction( event -> {
            ObservableList<Node> teamCreationItems = teamCreationBox.getChildren();
            boolean flag = true;
            for(int i = 0; i < teamCreationItems.size(); i++) {
                TeamCreationItemView item = (TeamCreationItemView) teamCreationItems.get(i);
                flag = DDventureLogic.getInstance().createTeam(item.getTeamName(), item.getTeamColor());
            }
            if(flag) {
                DDventureView.getInstance().createAnOpenPlayerScene();
            } else {
                if(errorLabel == null) {
                    errorLabel = new Label("Errore: due o piu' team presentano\nlo stesso nome o colore");
                    errorLabel.setFont(buttomFont);
                    errorLabel.setTextFill(Color.RED);
                    leftBox.getChildren().add(errorLabel);
                }
            }
        });

        bottomPane.getChildren().addAll(backToMenuButton, confirmButton);

        AnchorPane.setLeftAnchor(backToMenuButton, 10.0);
        AnchorPane.setBottomAnchor(backToMenuButton, 10.0);

        AnchorPane.setRightAnchor(confirmButton, 10.0);
        AnchorPane.setBottomAnchor(confirmButton, 10.0);

        this.setBottom(bottomPane);

        numberOfTeamComboBox.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    teamCreationBox.getChildren().clear();
                    int n = Integer.parseInt(newValue);
                    for (int i = 0; i < n; i++) {
                        teamCreationBox.getChildren().add(new TeamCreationItemView());
                    }
                    confirmButton.setDisable(false);
                });
    }
}
