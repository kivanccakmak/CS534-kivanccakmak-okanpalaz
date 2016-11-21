package simulation;
import java.util.Random;
import java.util.*;

abstract class HealthState {
    protected Human human;

    public abstract void passDay();

    public HealthState(Human h) {
        human = h;
    }

    public boolean isVisiblyInfected() {
        return false;
    }

    public boolean isInfectious() {
        return false;
    }

    public boolean isHealthy() {
        return false;
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

    public boolean isDead() {
        return false;
    }
}

class Healthy extends HealthState {
    public Healthy(Human h) {
        super(h);
    }

    public void passDay() {
    }
}

class Immune extends HealthState {
    public Immune(Human h) {
        super(h);
    }

    public void passDay() {
    }
}

class Infected extends HealthState {
    public Infected(Human h) {
        super(h);
    }

    public void passDay() {
    }
}

class Sick extends HealthState {
    public Sick(Human h) {
        super(h);
    }

    public void passDay() {
    }
}

public class Human {
    static int idGen = 0;
    private final int id;
    private final int birthDay;
    private Country country;

    HealthState health;
    HealthState healthy;
    HealthState infected;
    HealthState sick;
    HealthState immune;

    SimulationGlobals simGlobs = new SimulationGlobals();

    private static int genId() {
        idGen++;
        return idGen;
    }

    public Human(int b, Country c, boolean isInfected) {
        id = genId();
        country = c;
        birthDay = b;
        healthy = new Healthy(this);
        infected = new Infected(this);
        sick = new Sick(this);
        immune = new Immune(this);
        if (isInfected) {
            health = infected;
        } else {
            health = healthy;
        }
        country.addHuman(this);
    }

    public void updateHealth(Country.HealthStats h) {
        health.passDay();
    }

    public boolean isDead() {
        return health.isDead();
    }

    public boolean isHealthy() {
        return health.isHealthy();
    }

    public boolean isInfected() {
        return health.isInfected();
    }

    public boolean isSick() {
        return health.isSick();
    }

    public boolean isImmune() {
        return health.isImmune();
    }

    public boolean isInfectious() {
        return health.isInfectious();
    }

    private Country selectDest() {
        ArrayList<Country> countries = country.getNeighbors();
        int rnd = new Random().nextInt(countries.size());
        return countries.get(rnd);
    }

    private int decideDayToStay() {
        int max_day = simGlobs.getMaxDayToStay();
        int min_day = simGlobs.getMinDayToStay();
        Random random = new Random();
        return random.nextInt(max_day-min_day) + min_day;
    }

    public int getBornId() {
        return id;
    }

    private void move(Country destCountry) {
        country.removeHuman(this);
        destCountry.addHuman(this);
        country = destCountry;
    }

    @Override
    public String toString() {
        String out = "";
        out += "id: " + id + "\n";
        out += "current country: " +
            country.getName() + "\n";
        if (isHealthy()) {
            out += "status: healthy\n";
        }
        return out;
    }
}
