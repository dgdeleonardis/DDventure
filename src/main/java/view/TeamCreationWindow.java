package view;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import logic.DDventureLogic;

public class TeamCreationWindow extends BorderPane {

    // general attributes
    private static final int TITLE_FONT_SIZE = 64;
    private static final int BUTTON_FONT_SIZE = 24;
    private static final String[] ITEM_NUMBER_OF_TEAM_BOX = {"2", "3", "4", "5", "6"};

    // top section attributes
    private final Label titleLabel;

    // left section attributes
    private final VBox leftBox;
    private final HBox teamHBox;

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
        setPrefSize(
                DDventureView.PRIMARY_STAGE_WIDTH - (30*2),
                DDventureView.PRIMARY_STAGE_HEIGHT - (30*2));

        // set top section
        this.titleLabel = new Label("Schieramenti");
        this.titleLabel.setFont(Font.loadFont(this.getClass().getResourceAsStream(DDventureView.FONT_FILE_NAME), TITLE_FONT_SIZE));

        setTop(this.titleLabel);
        BorderPane.setAlignment(this.titleLabel, Pos.CENTER);
        BorderPane.setMargin(this.titleLabel, new Insets(30));

        // set of left section
        Font buttomFont = Font.loadFont(this.getClass().getResourceAsStream(DDventureView.FONT_FILE_NAME), BUTTON_FONT_SIZE);

        this.numberOfTeamLabel = new Label("Numero di schieramenti");
        this.numberOfTeamLabel.setFont(buttomFont);

        this.choiceNumberOfTeamBox = new ChoiceBox();
        this.choiceNumberOfTeamBox.getItems().addAll(ITEM_NUMBER_OF_TEAM_BOX);

        this.leftBox = new VBox();
        this.leftBox.setSpacing(100);

        this.leftBox.setAlignment(Pos.CENTER);
        this.teamHBox = new HBox(this.numberOfTeamLabel, this.choiceNumberOfTeamBox);
        this.teamHBox.setAlignment(Pos.CENTER);
        this.teamHBox.setSpacing(30);
        this.leftBox.getChildren().add(teamHBox);
        setLeft(leftBox);
        BorderPane.setAlignment(this.leftBox, Pos.TOP_CENTER);
        BorderPane.setMargin(this.leftBox, new Insets(30));

        // set center section
        this.teamCreationBox = new VBox();
        this.teamCreationBox.setAlignment(Pos.CENTER);
        this.teamCreationBox.setSpacing(30);

        setCenter(this.teamCreationBox);
        BorderPane.setAlignment(this.choiceNumberOfTeamBox, Pos.CENTER);

        //set bottom section
        this.bottomPane = new AnchorPane();

        this.backToMenuButton = new Button("Indietro");
        this.backToMenuButton.setFont(buttomFont);
        this.backToMenuButton.setOnAction(event -> DDventureView.getInstance().createAnOpenMainMenuScene());

        this.confirmButton = new Button("Avanti");
        this.confirmButton.setFont(buttomFont);
        this.confirmButton.setDisable(true);
        this.confirmButton.setOnAction( event -> {
            ObservableList<Node> teamCreationItems = teamCreationBox.getChildren();
            boolean flag = true;
            for(int i = 0; i < teamCreationItems.size() && flag; i++) {
                TeamCreationItem item = (TeamCreationItem) teamCreationItems.get(i);
                flag = DDventureLogic.getInstance().createTeam(item.getTeamName(), item.getTeamColor());
            }
            if(flag) {
                DDventureView.getInstance().createAnOpenPlayerScene();
            } else {
                Label errorLabel = new Label("Errore: due o piu' team presentano\nlo stesso nome o colore");
                errorLabel.setFont(buttomFont);
                errorLabel.setTextFill(Color.RED);
                leftBox.getChildren().add(errorLabel);
            }

                // TODO: mostra errore nella View

            /* TODO MODEL: deve prendere le informazioni relative ai TeamCreationItem (nome del team e colore) e salvarli
                l'elenco degli schieramenti deve essere allocato nell'oggetto Game
                metodo creaSchieramenti([Stringa del nome, stringa del colore])
            */
        });

        this.bottomPane.getChildren().addAll(this.backToMenuButton, this.confirmButton);
        AnchorPane.setLeftAnchor(this.backToMenuButton, 10.0);
        AnchorPane.setBottomAnchor(this.backToMenuButton, 10.0);
        AnchorPane.setRightAnchor(this.confirmButton, 10.0);
        AnchorPane.setBottomAnchor(this.confirmButton, 10.0);

        setBottom(this.bottomPane);

        this.choiceNumberOfTeamBox.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    teamCreationBox.getChildren().clear();
                    int n = Integer.parseInt(newValue.toString());
                    for (int i = 0; i < n; i++) {
                        teamCreationBox.getChildren().add(new TeamCreationItem());
                    }
                    if(this.confirmButton != null)
                        this.confirmButton.setDisable(false);
                });
    }
}
