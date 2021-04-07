package logic;

import java.util.ArrayList;

public class Game {

    private Map map;
    private ArrayList<CharacterInGame> charactersInGame;
    private ArrayList<Team> teams;

    public Game() {
        teams = new ArrayList<>();
        charactersInGame = new ArrayList<>();
    }

    public Map getMap() {
        return map;
    }

    public ArrayList<CharacterInGame> getCharactersInGame() {
        return charactersInGame;
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public void setPersonaggiInPartita(ArrayList<CharacterInGame> personaggiInPartita) {
        this.charactersInGame = personaggiInPartita;
    }

    public void setTeams(ArrayList<Team> team) {
        this.teams = team;
    }
}
