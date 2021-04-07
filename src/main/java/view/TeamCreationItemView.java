package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.StringConverter;

public class TeamCreationItemView extends HBox {



    private TextField teamNameText;
    private ChoiceBox<String> teamColorChoiceBox;

    public TeamCreationItemView() {
        super();
        Font textFont = Font.loadFont(getClass().getResourceAsStream(DDventureView.FONT_FILE_NAME), 24);

        teamNameText = new TextField("Nome schieramento");
        teamNameText.setFont(textFont);
        //FIXME: chiedere parere a Marta
        //this.teamNameText.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));

        teamColorChoiceBox = new ChoiceBox<String>();
        //FIXME: chiedere parere a Marta
        //teamColorChoiceBox.setMinWidth(240.0);
        //teamColorChoiceBox.setStyle("-fx-font: 24 \"Alagard\"");
        //teamColorChoiceBox.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        teamColorChoiceBox.getItems().addAll(DDventureView.TEAM_COLORS.keySet());

        this.getChildren().addAll(teamNameText, teamColorChoiceBox);

        this.setSpacing(30);
        this.setAlignment(Pos.CENTER);
    }

    public String getTeamName() {
        return teamNameText.getText();
    }

    public String getTeamColor() {
        return teamColorChoiceBox.getValue();
    }
}
