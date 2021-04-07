package logic;

public class Weapon {

    private String name;
    private int damageDice;
    private int tpcModifier;

    public Weapon(String name, int damageDice, int tpcModifier){
                this.name = name;
                this.damageDice = damageDice;
                this.tpcModifier = tpcModifier;
    }

    public void setDamageDice(int damageDice) {
        this.damageDice = damageDice;
    }

    public void setTpcModifier(int tpcModifier) {
        this.tpcModifier = tpcModifier;
    }

    public String getName() {
        return name;
    }

    public int getDamageDice() {
        return damageDice;
    }

    public int getTpcModifier() {
        return tpcModifier;
    }
}
