package logic;

public class Character {
    protected String name;
    protected Integer pf;
    protected Integer ca;
    protected Integer speed;
    protected Integer initiative;
    protected Team team;
    protected String avatar;
    protected Weapon weapon;

    public Character(String name, int pf, int ca, int speed, int initiative, String avatar) {
        this.name = name;
        this.pf = pf;
        this.ca = ca;
        this.speed = speed;
        this.initiative = initiative;
        this.avatar = avatar;
    }

    public String getName(){
        return this.name;
    }

    public Integer getPF(){
        return this.pf;
    }

    public Integer getCA(){
        return this.ca;
    }

    public Integer getSpeed(){
        return this.speed;
    }

    public Integer getInitiative(){
        return this.initiative;
    }

    public String getAvatar(){
        return this.avatar;
    }
}