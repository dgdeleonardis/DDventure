package logic;

public class Character {
    private String name;
    protected Integer pf;
    protected Integer ca;
    protected Integer speed;
    protected Integer initiativeModifier;
    protected String avatar;
    protected Weapon weapon;

    public Character(String name, int pf, int ca, int speed, int initiativeModifier, String avatar, Weapon weapon) {
        this.name = name;
        this.pf = pf;
        this.ca = ca;
        this.speed = speed;
        this.initiativeModifier = initiativeModifier;
        this.avatar = avatar;
        this.weapon = weapon;
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

    public void setInitiative(int initiativeModifier) {
        this.initiativeModifier = initiativeModifier;
    }

    public int getInitiativeModifier() {
        return initiativeModifier;
    }

    public String getAvatar(){
        return this.avatar;
    }

    public Weapon getWeapon() {
        return weapon;
    }
}