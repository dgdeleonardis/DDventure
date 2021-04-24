package logic;

import javafx.geometry.Rectangle2D;

import java.util.ArrayList;

public class Action {
    private String name;
    private ArrayList<Rectangle2D> frames;

    public Action(String name, ArrayList<Rectangle2D> frames) {
        this.name = name;
        this.frames = frames;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Rectangle2D> getFrames() {
        return frames;
    }
}
