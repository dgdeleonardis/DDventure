package logic;

public class DDventureLogic implements ILogic{

    private static DDventureLogic instance = null;

    private Game game;

    private DDventureLogic() {
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
}
