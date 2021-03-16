package logic;

public class DDventureLogic implements ILogic{

    private static DDventureLogic instance = null;

    private DDventureLogic() {
    }

    public static ILogic getInstance() {
        if(instance == null)
            instance = new DDventureLogic();
        return instance;
    }
}
