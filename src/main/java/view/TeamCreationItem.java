package view;

import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.HashMap;

public class TeamCreationItem extends HBox {

    private static final HashMap<String, Color> COLORS = new HashMap<String, Color>() {{
        put("Rosso", Color.RED);
        put("Blue", Color.BLUE);
        put("Verde", Color.GREEN);
        put("Rosa", Color.PINK);
        put("Arancione", Color.ORANGE);
        put("Oro", Color.GOLD);
    }};

    private TextField teamNameText;
    private ChoiceBox<String> teamColorChoiceBox;

    public TeamCreationItem() {
        super();
        teamNameText = new TextField("Schieramento N");
        teamColorChoiceBox = new ChoiceBox<String>();
        teamColorChoiceBox.getItems().addAll(COLORS.keySet());
        this.getChildren().addAll(this.teamNameText, this.teamColorChoiceBox);
        Font textFont = Font.loadFont(getClass().getResourceAsStream(DDventureView.FONT_FILE_NAME), 24);
        this.teamNameText.setFont(textFont);
        this.setSpacing(30);
        this.setAlignment(Pos.CENTER);
    }

    public String getTeamName() {
        return teamNameText.getText().toString();
    }

    public String getTeamColor() {
        return teamColorChoiceBox.getValue();
    }
}
