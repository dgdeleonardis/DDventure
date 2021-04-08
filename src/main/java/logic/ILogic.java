package logic;

public interface ILogic {


    boolean createTeam(String teamName, String teamColor);

    void createCharacterInGame(String name, int pf, int ca, int speed, int initiative, String avatar, String teamName, String weaponName, int damageDice, int tpcModifier);

    void createCharacter(String name, int pf, int ca, int speed, int initiative, String avatar, String weaponName, int damageDice, int tpcModifier);

    void deleteCharactersInGame();

    void deleteTeams();

    String[] getTeamNames();

    void createMap(double size);

    Map getTempMap();

    Character getTempCharacter();

    void setGameMap();

    Map getGameMap();

    Game getGame();

    boolean saveCharacter(String fileName);

    boolean loadCharacter(String fileName);

    boolean saveMap(String fileName);

    boolean loadMap(String fileName);
}
