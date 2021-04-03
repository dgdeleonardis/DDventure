package logic;

import java.util.ArrayList;

public class Team {

    private final String name;
    private final String color;
    private ArrayList<CharacterInGame> members;
    private boolean isInGame;

    public Team(String name, String color) {
        this.name = name;
        this.color = color;
        members = new ArrayList<CharacterInGame>();
        isInGame = true;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public void setInGame(boolean inGame) {
        isInGame = inGame;
    }

    public void addToTeam(CharacterInGame player) {
        members.add(player);
    }
}
