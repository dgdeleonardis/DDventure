package logic;


import java.io.File;

public class DDventureLogic implements ILogic{

    private static DDventureLogic instance = null;

    private Game game;
    private Map tempMap;

    private final static int DEFAULT_COLUMNS = 16;
    private final static int DEFAULT_ROWS = 9;

    private DDventureLogic() {
        // game's directory sets up
        File gameDirectory = new File(System.getProperty("user.home") + "/Documents/DDventure");
        if(!gameDirectory.exists()) {
            gameDirectory.mkdir();
            new File(gameDirectory.getAbsolutePath() + "/saved_game").mkdir();
            new File(gameDirectory.getAbsolutePath() + "/saved_map").mkdir();
            new File(gameDirectory.getAbsolutePath() + "/saved_character").mkdir();
        }
        game = new Game();
    }

    public static ILogic getInstance() {
        if(instance == null)
            instance = new DDventureLogic();
        return instance;
    }


    @Override
    public boolean createTeam(String teamName, String teamColor) {
        for(int i = 0; i < game.getTeams().size(); i++) {
            Team t = game.getTeams().get(i);
            if(teamName.equals(t.getName()) || teamColor.equals(t.getColor())) {
                return false;
            }
        }
        game.getTeams().add(new Team(teamName, teamColor));
        return true;
    }

    @Override
    public void createCharacterInGame(String name, int pf, int ca, int speed, int initiative, String avatar, String teamName, String weaponName, int damageDice, int tpcModifier) {
        Team characterTeam = null;
        for(int i = 0; i < game.getTeams().size(); i++) {
            Team t = game.getTeams().get(i);
            if(t.getName().equals(teamName)) {
                characterTeam = t;
            }
        }
        CharacterInGame characterInGame = new CharacterInGame(name, pf, ca, speed, initiative, avatar, characterTeam,
                new Weapon(weaponName, damageDice, tpcModifier));
        game.getCharactersInGame().add(characterInGame);
        characterTeam.addToTeam(characterInGame);
    }

    @Override
    public void deleteCharactersInGame() {
        game.getCharactersInGame().clear();
    }

    @Override
    public void deleteTeams() {
        game.getTeams().clear();
    }

    @Override
    public String[] getTeamNames() {
        int n = game.getTeams().size();
        String[] teamNames = new String[n];
        for(int i = 0; i < n; i++)
            teamNames[i] = game.getTeams().get(i).getName();
        return teamNames;
    }

    @Override
    public void createMap(double size) {
        int columns = (int) Math.round(DEFAULT_COLUMNS*size);
        int rows = (int) Math.round(DEFAULT_ROWS*size);
        Cell[][] tempMapCells = new Cell[columns][rows];
        for(int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                tempMapCells[i][j] = new Cell((int) Math.ceil(Math.random() * 3.0));
            }
        }
        tempMap = new Map(tempMapCells);
        //TODO: implementare controllo D.
    }

    @Override
    public Map getTempMap() {
        return tempMap;
    }

    @Override
    public void setGameMap() {
        game.setMap(tempMap);
    }

    @Override
    public Map getGameMap() {
        return game.getMap();
    }
}
