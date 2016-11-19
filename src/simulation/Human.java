package simulation;
import java.util.Random;
import java.util.*;

public class Human {

    private String name;
    private int bornId;
    private int birthDay;
    public Country currentCountry;
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
        bornCountry.requestEnter(this);
    }

    public void updateHealth() {
        this.currentHealth.passDay();
    }

    public boolean getIsDeath() {
        return this.currentHealth.getIsDeath();
    }

    public void clearTargetCountry() {
        this.targetCountry = null;
    }

    //decide to move from country or not if remained days is zero.
    //otherwise, just decrease remained days.
    public boolean updateLocation() {
        boolean shallMove = false;
        this.decrRemDayToStay();
        if (this.remDayToStay != 0) {
            return shallMove;
        }

        //now decide to move
        if (this.currentCountry.getNumVisiblyInfectious() > 0) {
            Country dest = this.selectDest();
            System.out.println(this.name + " " + this.currentCountry.getName()
                    + " -> " + dest.getName());
            this.targetCountry = dest;
            shallMove = true;
        }

        //either he decide to move or stay, select random days
        //for new country or this country
        this.remDayToStay = this.decideDayToStay();
        return shallMove;
    }

    public Country getTargetCountry() {
        return this.targetCountry;
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
