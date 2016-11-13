package simulation;

public class Human {
    private String name;
    private int bornId;
    private int birthDay;
    private final Country bornCountry;
    private Country currentCountry;

    public Human(String name, int bornId, int birthDay,
            Country bornCountry) {
        this.name = name;
        this.bornId = bornId;
        this.birthDay = birthDay;
        bornCountry.enter(this);
        this.bornCountry = bornCountry;
        this.currentCountry = bornCountry;
    }

    public void move(Country dest) {
        this.currentCountry.exit(this);
        this.currentCountry = dest;
        dest.enter(this);
    }

    public boolean isHealthy() {
        return true;
    }

    public boolean isInfected() {
        return false;
    }

    public boolean isSick() {
        return false;
    }

    public boolean isImmune() {
        return false;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        String out = "";
        out += "name: " + this.name + "\n";
        out += "id: " + this.bornId + "\n";
        out += "bornCountry: " +
            this.bornCountry.getName() + "\n";
        out += "current country: " +
            this.currentCountry.getName() + "\n";
        if (this.isHealthy()) {
            out += "status: healthy\n";
        }
        return out;
    }

}
