package simulation;
import java.util.*;

abstract class HealthState {
    protected Human human;

    public abstract void passDay();

    public HealthState(Human h) {
        human = h;
    }

    public boolean isVisiblyInfectious() {
        return false;
    }

    public boolean isInfectious() {
        return false;
    }

    public boolean isHealthy() {
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

    @Override
    public boolean isInfectious() {
        return true;
    }

    public void passDay() {
    }
}

class Sick extends HealthState {
    public Sick(Human h) {
        super(h);
    }

    @Override
    public boolean isInfectious() {
        return true;
    }

    @Override
    public boolean isVisiblyInfectious() {
        return true;
    }

    public void passDay() {
    }
}

class Dead extends HealthState {
    public Dead(Human h) {
        super(h);
    }

    @Override
    public boolean isInfectious() {
        return true;
    }

    @Override
    public boolean isVisiblyInfectious() {
        return true;
    }

    public void passDay() {
    }
}

public class Human {
    static int idGen = 0;
    private final int id;
    private final int birthDay;
    private int daysUntilMove;
    private Country country;

    HealthState health;
    HealthState healthy;
    HealthState infected;
    HealthState sick;
    HealthState immune;
    HealthState dead;

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
        dead = new Dead(this);
        if (isInfected) {
            health = infected;
        } else {
            health = healthy;
        }
        updateMoveDate();
        country.addHuman(this);
    }

    private void updateMoveDate() {
        int upper = SimulationGlobals.getMaxDayToStay();
        int lower = SimulationGlobals.getMinDayToStay();
        daysUntilMove = lower + SimulationGlobals.getRng().nextInt(upper - lower);
    }

    private Country selectDest() {
        // TODO: is this mutating the list?
        ArrayList<Country> available = country.neighbors()
            .stream()
            .filter(c -> !c.hasVisiblyInfectious())
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

        if (available.size() > 0) {
            int rnd = SimulationGlobals.getRng().nextInt(available.size());
            return available.get(rnd);
        } else {
            return null;
        }
    }

    private void move(Country destCountry) {
        country.removeHuman(this);
        destCountry.addHuman(this);
        country = destCountry;
    }

    public void passDay(Country.HealthStats h) {
        health.passDay();
    }

    public boolean isDead() {
        return health.isDead();
    }

    public boolean isHealthy() {
        return health.isHealthy();
    }

    public boolean isInfectious() {
        return health.isInfectious();
    }

    public boolean isVisiblyInfectious() {
        return health.isVisiblyInfectious();
    }

    public boolean isImmune() {
        return health.isImmune();
    }

    public int id() {
        return id;
    }

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
