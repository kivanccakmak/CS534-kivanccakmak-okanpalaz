package simulation;
import java.util.Random;
import java.util.*;

public class Human {
    private final String name;
    private final int bornId;
    private final int birthDay;
    private Country currentCountry;
    private Country targetCountry;
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
        this.targetCountry = null;
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
        this.currentCountry.giveBirth(this);
    }

    public void updateHealth() {
        this.currentHealth.passDay();
    }

    public boolean getIsDeath() {
        return this.currentHealth.getIsDeath();
    }

    //decide to move from country or not, if days expired.
    public boolean updateLocation() {
        boolean shallMove = false;
        this.remDayToStay--;
        if (this.remDayToStay != 0) {
            return false;
        }

        //now decide to move
        if (this.currentCountry.getNumVisiblyInfectious() > 0) {
            Country dest = this.selectDest();
            this.targetCountry = dest;
            shallMove = true;
            this.printMoveMessage(dest, this.currentCountry);
        }

        //either he move or stay, select random days
        this.remDayToStay = this.decideDayToStay();
        return shallMove;
    }

    private void printMoveMessage(Country dest, Country src) {
        String msg = "";
        msg += this.name + " " + src.getName() +
            " -> " + dest.getName();
        System.out.println(msg);
    }

    public Country getTargetCountry() {
        return this.targetCountry;
    }

    public void clearTargetCountry() {
        this.targetCountry = null;
    }

    private Country selectDest() {
        ArrayList<Country> countries =
            this.currentCountry.getNeighbours();
        int rnd = new Random().nextInt(countries.size());
        return countries.get(rnd);
    }

    private int decideDayToStay() {
        int max_day = this.simGlobs.getMaxDayToStay();
        int min_day = this.simGlobs.getMinDayToStay();
        Random random = new Random();
        return random.nextInt(max_day-min_day) + min_day;
    }

    public boolean hasInfectiousCountry() {
        int numInfectious = this.currentCountry.getNumInfectious();
        if (numInfectious > 0) {
            return true;
        }
        return false;
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

    public int getBornId() {
        return this.bornId;
    }

    @Override
    public String toString() {
        String out = "";
        out += "name: " + this.name + "\n";
        out += "id: " + this.bornId + "\n";
        out += "current country: " +
            this.currentCountry.getName() + "\n";
        if (this.isHealthy()) {
            out += "status: healthy\n";
        }
        return out;
    }
}
