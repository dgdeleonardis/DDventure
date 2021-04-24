package logic;

import java.util.ArrayList;

public class Team {

    private final String name;
    private final String color;
    private ArrayList<CharacterInGame> members;

    public Team(String name, String color) {
        this.name = name;
        this.color = color;
        members = new ArrayList<CharacterInGame>();
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public void addToTeam(CharacterInGame player) {
        members.add(player);
    }

    public CharacterInGame[] getMembers() {
        CharacterInGame[] arr = new CharacterInGame[members.size()];
        return members.toArray(arr);
    }

    public boolean isInGame() {
        int i = 0;
        while(i < members.size() && !members.get(i).isDead()) {
            i++;
        }
        if(i < members.size() || members.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
}
