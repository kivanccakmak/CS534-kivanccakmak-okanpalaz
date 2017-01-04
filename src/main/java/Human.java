import java.util.*;
import java.util.stream.*;

abstract class HealthState {
    protected Human human;

    public HealthState(Human h) { human = h; }

    // Actions
    public void passDay() { }
    public void infectionChance(boolean infectious) { }
    public void vaccinate() { }

    // Type queries
    public boolean isHealthy() { return false; }
    public boolean isInfected() { return false; }
    public boolean isSick() { return false; }
    public boolean isImmune() { return false; }
    public boolean isDead() { return false; }
    public boolean isSuperHealthy() { return false; }

    // State queries
    public boolean isInfectious() { return false; }
    public boolean isVisiblyInfectious() { return false; }
    public boolean isVaccineCandiate() { return isHealthy(); }
}

class SuperHealthy extends HealthState {
    public SuperHealthy(Human h) { super(h); }

    @Override
    public boolean isSuperHealthy() { return true; }
}

class Healthy extends HealthState {
    public Healthy(Human h) { super(h); }

    @Override
    public boolean isHealthy() { return true; }

    @Override
    public void vaccinate() { human.getSuperHealthy(); }

    @Override
    public void infectionChance(boolean infectious) {
        SimulationRules rules = SimulationRules.getInstance();

        if (infectious && rules.infectionDiceThrow()) {
            human.getInfected();
        }
    }
}

class Infected extends HealthState {
    private int days = 0;

    public Infected(Human h) { super(h); }

    @Override
    public boolean isInfected() { return true; }

    @Override
    public boolean isInfectious() { return true; }

    @Override
    public void passDay() {
        SimulationRules rules = SimulationRules.getInstance();

        days++;
        if (days == rules.getDaysUntilSick()) {
            human.getSick();
        }
    }
}

class Sick extends HealthState {
    private int days = 0;

    public Sick(Human h) {
        super(h);
    }

    @Override
    public boolean isSick() { return true; }

    @Override
    public boolean isInfectious() { return true; }

    @Override
    public boolean isVisiblyInfectious() { return true; }

    @Override
    public void passDay() {
        SimulationRules rules = SimulationRules.getInstance();

        days++;
        if (days == rules.getDaysUntilDeathChance()) {
            if (rules.dieDiceThrow()) {
                human.die();
            }
        }

        if (days == rules.getDaysUntilImmune()) {
            human.becomeImmune();
        }
    }
}

class Immune extends HealthState {
    private int days = 0;

    public Immune(Human h) { super(h); }

    @Override
    public boolean isImmune() { return true; }

    @Override
    public boolean isInfectious() { return true; }

    @Override
    public void passDay() {
        SimulationRules rules = SimulationRules.getInstance();

        days++;
        if (days == rules.getDaysUntilHealthy()) {
            human.getHealthy();
        }
    }
}


class Dead extends HealthState {
    public Dead(Human h) {
        super(h);
    }

    @Override
    public boolean isDead() { return true; }

    @Override
    public boolean isInfectious() { return true; }

    @Override
    public boolean isVisiblyInfectious() { return true; }
}

public class Human {
    static int idGen = 0;
    private final int id;
    private int daysUntilMove;
    protected Country country;
    HealthState health;

    private static int genId() {
        idGen++;
        return idGen;
    }

    public Human(Country c) {
        id = genId();
        country = c;
        getHealthy();
        genMoveDate();
        country.addHuman(this);
    }

    private void genMoveDate() {
        SimulationRules rules = SimulationRules.getInstance();

        int upper = rules.getMaxDayToStay();
        int lower = rules.getMinDayToStay();
        daysUntilMove = lower + rules.getRng().nextInt(upper - lower);
    }

    private Country selectDest() {
        SimulationRules rules = SimulationRules.getInstance();
        List<Country> candidates;

        if (rules.airtravelDiceThrow()) {
            candidates = country.allCountries()
                .stream()
                .filter(c -> c != country)
                .collect(Collectors.toList());
        } else {
            candidates = country.neighbors()
                .stream()
                .filter(c -> !c.hasVisiblyInfectious())
                .collect(Collectors.toList());
        }

        if (candidates.size() > 0) {
            int rnd = rules.getRng().nextInt(candidates.size());
            return candidates.get(rnd);
        } else {
            return null;
        }
    }

    private void move(Country destCountry) {
        destCountry.moveHuman(this);
        country = destCountry;
        health.infectionChance(country.hasInfectious());
    }

    public void passDay() {
        health.passDay();

        if (!health.isDead()) {
            daysUntilMove--;
        }

        if (daysUntilMove == 0) {
            genMoveDate();
            Country dest = selectDest();
            if (dest != null) {
                move(dest);
            }
        }
    }

    public int id() { return id; }
    public Country country() { return country; }

    //// Health state query methods
    // Type queries
    public boolean isHealthy() { return health.isHealthy(); }
    public boolean isInfected() { return health.isInfected(); }
    public boolean isSick() { return health.isSick(); }
    public boolean isImmune() { return health.isImmune(); }
    public boolean isDead() { return health.isDead(); }
    public boolean isSuperHealthy() { return health.isSuperHealthy(); }

    // State queries
    public boolean isInfectious() { return health.isInfectious(); }
    public boolean isVisiblyInfectious() { return health.isVisiblyInfectious(); }
    public boolean isVaccineCandiate() { return health.isVaccineCandiate(); }
    ////

    // Health releated actions
    protected void getHealthy() { health = new Healthy(this); }
    protected void getInfected() { health = new Infected(this); }
    protected void getSick() { health = new Sick(this); }
    protected void becomeImmune() { health = new Immune(this); }
    protected void die() { health = new Dead(this); }
    protected void getSuperHealthy() { health = new SuperHealthy(this); }
    protected void vaccinate() { health.vaccinate(); }
    //

    @Override
    public String toString() {
        String out = "";
        out += "id: " + id + "\n";
        out += "current country: " +
            country.name() + "\n";
        if (isHealthy()) {
            out += "status: healthy\n";
        }
        return out;
    }
}
