package logic;

public interface ILogic {


    boolean createTeam(String teamName, String teamColor);

    void createCharacterInGame(String name, int pf, int ca, int speed, int initiative, String avatar, String teamName, String weaponName, int damageDice, int tpcModifier);

    void deleteCharactersInGame();

    void deleteTeams();

    String[] getTeamNames();

    void createMap(double size);

    Map getTempMap();

    void setGameMap();

    Map getGameMap();
}
