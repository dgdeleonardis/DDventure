package logic;

import java.util.ArrayList;

public class Game {

    private Cell[][] map;
    private ArrayList<CharacterInGame> charactersInGame;
    private ArrayList<Team> teams;

    public Game() {
        teams = new ArrayList<Team>();
    }

    public Cell[][] getMap() {
        return map;
    }

    public ArrayList<CharacterInGame> getPersonaggiInPartita() {
        return charactersInGame;
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public void setMap(Cell[][] map) {
        this.map = map;
    }

    public void setPersonaggiInPartita(ArrayList<CharacterInGame> personaggiInPartita) {
        this.charactersInGame = personaggiInPartita;
    }

    public void setTeams(ArrayList<Team> team) {
        this.teams = team;
    }
}
