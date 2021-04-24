package logic;

import javafx.geometry.Rectangle2D;

import java.util.ArrayList;

public class CharacterSprite {

    private String name;
    private String sheetFileName;
    private String backgroundColor;
    private Rectangle2D stance;
    private ArrayList<Action> actions;

    public CharacterSprite(String name, String sheetFileName, String backgroundColor, Rectangle2D stance, ArrayList<Action> actions) {
        this.name = name;
        this.sheetFileName = sheetFileName;
        this.backgroundColor = backgroundColor;
        this.stance = stance;
        this.actions = actions;
    }

    public String getName() {
        return name;
    }

    public String getSheetFileName() {
        return sheetFileName;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public Rectangle2D getStance() {
        return stance;
    }

    public ArrayList<Action> getActions() {
        return actions;
    }
}
