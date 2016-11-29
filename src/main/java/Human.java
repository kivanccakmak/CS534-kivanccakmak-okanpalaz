import java.util.*;

abstract class HealthState {
    protected Human human;

    public void passDay() {
    }

    public HealthState(Human h) {
        human = h;
    }

    public void infectionChance(boolean infectious) {
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

    public boolean isInfectious() {
        return false;
    }

    public boolean isVisiblyInfectious() {
        return false;
    }
}

class Healthy extends HealthState {
    public Healthy(Human h) {
        super(h);
    }

    @Override
    public boolean isHealthy() {
        return true;
    }

    @Override
    public void infectionChance(boolean infectious) {
        if (HealthGlobals.infectionDiceThrow()) {
            human.getInfected();
        }
    }
}

class Infected extends HealthState {
    private int days = 0;

    public Infected(Human h) {
        super(h);
    }

    @Override
    public boolean isInfected() {
        return true;
    }

    @Override
    public boolean isInfectious() {
        return true;
    }

    @Override
    public void passDay() {
        days++;
        if (days == HealthGlobals.getDaysUntilSick()) {
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
    public boolean isSick() {
        return true;
    }

    @Override
    public boolean isInfectious() {
        return true;
    }

    @Override
    public boolean isVisiblyInfectious() {
        return true;
    }

    @Override
    public void passDay() {
        days++;
        if (days == HealthGlobals.getDaysUntilDeathChance()) {
            if (HealthGlobals.dieDiceThrow()) {
                human.die();
            }
        }

        if (days == HealthGlobals.getDaysUntilImmune()) {
            human.becomeImmune();
        }
    }
}

class Immune extends HealthState {
    private int days = 0;

    public Immune(Human h) {
        super(h);
    }

    @Override
    public boolean isImmune() {
        return true;
    }

    @Override
    public boolean isInfectious() {
        return true;
    }

    @Override
    public void passDay() {
        days++;
        if (days == HealthGlobals.getDaysUntilHealthy()) {
            human.getHealthy();
        }
    }
}


class Dead extends HealthState {
    public Dead(Human h) {
        super(h);
    }

    @Override
    public boolean isDead() {
        return true;
    }

    @Override
    public boolean isInfectious() {
        return true;
    }

    @Override
    public boolean isVisiblyInfectious() {
        return true;
    }
}

public class Human {
    static int idGen = 0;
    private final int id;
    private int daysUntilMove;
    private Country country;
    HealthState health;

    private static int genId() {
        idGen++;
        return idGen;
    }

    public Human(Country c, boolean isInfected) {
        id = genId();
        country = c;
        if (isInfected) {
            getInfected();
        } else {
            getHealthy();
        }
        genMoveDate();
        country.addHuman(this);
    }

    private void genMoveDate() {
        int upper = SimulationGlobals.getMaxDayToStay();
        int lower = SimulationGlobals.getMinDayToStay();
        daysUntilMove = lower + HealthGlobals.getRng().nextInt(upper - lower);
    }

    private Country selectDest() {
        ArrayList<Country> available = country.neighbors()
            .stream()
            .filter(c -> !c.hasVisiblyInfectious())
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

        if (available.size() > 0) {
            int rnd = HealthGlobals.getRng().nextInt(available.size());
            return available.get(rnd);
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

    public Country country() {
        return country;
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

    public boolean isDead() {
        return health.isDead();
    }

    public boolean isInfectious() {
        return health.isInfectious();
    }

    public boolean isVisiblyInfectious() {
        return health.isVisiblyInfectious();
    }

    public void getHealthy() {
        health = new Healthy(this);
    }

    public void getInfected() {
        health = new Infected(this);
    }

    public void getSick() {
        health = new Sick(this);
    }

    public void becomeImmune() {
        health = new Immune(this);
    }

    public void die() {
        health = new Dead(this);
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
