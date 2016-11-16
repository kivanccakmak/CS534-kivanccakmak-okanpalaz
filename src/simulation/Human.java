package simulation;
import java.util.Random;
import java.util.*;

public class Human {

    private String name;
    private int bornId;
    private int birthDay;
    private final Country bornCountry;
    public Country currentCountry;
    private int remDayToStay;

    HealthState currentHealth;
    HealthState healthy;
    HealthState infected;
    HealthState sick;
    HealthState immune;

    SimulationGlobals simGlobs = new SimulationGlobals();

    public Human(String name, int bornId, int birthDay, 
            Country bornCountry, boolean isBornInfected) {
        this.name = name;
        this.bornCountry = bornCountry;
        this.currentCountry = bornCountry;
        this.bornId = bornId;
        this.birthDay = birthDay;
        this.remDayToStay = this.decideDayToStay();
        this.healthy = new Healthy(this);
        this.infected = new Infected(this);
        this.sick = new Sick(this);
        this.immune = new Immune(this);

        if (isBornInfected) {
            this.currentHealth = this.infected;
        } else {
            this.currentHealth = this.healthy;
        }
        bornCountry.recordEnter(this);
    }

    public void passDay() {
        this.decrRemDayToStay();
        this.currentHealth.passDay();
        if (this.remDayToStay == 0) {
            if (this.currentCountry.getNumVisiblyInfectious() > 0) {
                Country dest = this.selectDest();
                this.move(dest);
            }
        } else {
            this.remDayToStay = this.decideDayToStay();
        }
    }

    private Country selectDest() {
        ArrayList<Country> countries = this.currentCountry.getNeighbours();
        int rnd = new Random().nextInt(countries.size());
        return countries.get(rnd);
    }

    private int decideDayToStay() {
        int max_day = this.simGlobs.getMaxDayToStay();
        int min_day = this.simGlobs.getMinDayToStay();
        Random random = new Random();
        return random.nextInt(max_day-min_day) + min_day;
    }

    public void move(Country dest) {
        this.remDayToStay = this.decideDayToStay();
        System.out.println(this.name + " enters gate of " + dest.getName());
        System.out.println(this.name + " would stay for " + this.remDayToStay);
        dest.enterGate(this);
    }

    public void notifyInfected() {
        this.currentCountry.decrNumHealthy();
        this.currentCountry.incrNumInfected();
        this.currentCountry.incrNumInfectious();
    }

    public void notifySick() {
        this.currentCountry.decrNumInfected();
        this.currentCountry.incrNumSick();
        this.currentCountry.incrNumVisiblyInfectious();
    }

    public void notifyImmune() {
        this.currentCountry.decrNumSick();
        this.currentCountry.incrNumImmune();
        this.currentCountry.decrNumVisiblyInfectious();
    }

    public void notifyRecovery() {
        this.currentCountry.decrNumImmune();
        this.currentCountry.incrNumHealthy();
        this.currentCountry.decrNumInfectious();
    }

    public int getRemDayToStay() {
        return this.remDayToStay;
    }

    private void decrRemDayToStay() {
        this.remDayToStay--;
    }

    public boolean isHealthy() {
        return this.currentHealth.isHealthy();
    }

    public boolean isInfected() {
        return this.currentHealth.isInfected();
    }

    public boolean isSick() {
        return this.currentHealth.isSick();
    }

    public boolean isImmune() {
        return this.currentHealth.isImmune();
    }

    public boolean isInfectious() {
        return this.currentHealth.isInfectious();
    }

    public boolean isVisiblyInfectious() {
        return this.currentHealth.isVisiblyInfectious();
    }

    public String getName() {
        return this.name;
    }

    public void die() {
        this.currentCountry.recordDeath(this);
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
