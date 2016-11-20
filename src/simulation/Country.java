package simulation;
import java.util.*;

public class Country {
    private String name;
    private ArrayList<Country> neighbours;
    private ArrayList<Human> citizens;
    private ArrayList<Human> graveyard;
    private ArrayList<Human> arrivals;

    private int numHealthy;
    private int numInfected;
    private int numSick;
    private int numImmune;
    private int numDeath;
    private int numInfectious;
    private int numVisiblyInfectious;

    public Country(String name) {
        this.name = name;
        this.neighbours = new ArrayList<Country>();
        this.citizens = new ArrayList<Human>();
        this.graveyard = new ArrayList<Human>();
        this.arrivals = new ArrayList<Human>();
        this.numHealthy = 0;
        this.numInfected = 0;
        this.numSick = 0;
        this.numImmune = 0;
        this.numDeath = 0;
        this.numInfectious = 0;
        this.numVisiblyInfectious = 0;
    }

    public void giveBirth(Human h) {
        System.out.println(h.getName() + " born");
        this.citizens.add(h);
        this.countHealthStats();
    }

    private void reqEnter(Human h) {
        this.arrivals.add(h);
    }

    private void countHealthStats() {
        this.numHealthy = 0;
        this.numInfected = 0;
        this.numSick = 0;
        this.numImmune = 0;
        this.numInfectious = 0;
        this.numVisiblyInfectious = 0;

        for (Human h: this.citizens) {
            if (h.isHealthy()) {
                this.numHealthy++;
            } else if (h.isInfected()) {
                this.numInfected++;
            } else if (h.isSick()) {
                this.numSick++;
            } else if (h.isImmune()) {
                this.numImmune++;
            }
            if (h.isInfectious()) {
                this.numInfectious++;
                if (h.isVisiblyInfectious()) {
                    this.numVisiblyInfectious++;
                }
            }
        }
    }

    public void reqUpdateHealth() {
        FuncInterfaceDeathCheck health;
        health = (h, c) -> {
            h.updateHealth();
            if (h.getIsDeath()) {
                System.out.println("RIP " + h.getName());
                c.numDeath++;
                c.graveyard.add(h);
                return true;
            } else {
                return false;
            }
        };
        this.citizens.removeIf(p -> health.iter(p, this));
    }

    public void reqUpdateLocation() {
        this.countHealthStats();
        FuncInterfaceMoveCheck location;
        location = (h, c) -> {
            boolean shallMove = h.updateLocation();
            if (shallMove) {
                Country dest = h.getTargetCountry();
                dest.reqEnter(h);
                return true;
            }
            return false;
        };
        this.citizens.removeIf(p -> location.iter(p, this));
    }

    public void reqCountHealthStats() {
        this.countHealthStats();
    }

    public void reqPullArrivals() {
        for (Human h: this.arrivals) {
            this.citizens.add(h);
            h.clearTargetCountry();
        }
        this.arrivals.clear();
    }

    public void addNeighbour(Country c) {
        this.neighbours.add(c);
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Country> getNeighbours() {
        return this.neighbours;
    }

    public int getNumHealthy() {
        return this.numHealthy;
    }

    public int getNumInfected() {
        return this.numInfected;
    }

    public int getNumSick() {
        return this.numSick;
    }

    public int getNumImmune() {
        return this.numImmune;
    }

    public int getNumInfectious() {
        return this.numInfectious;
    }

    public int getNumVisiblyInfectious() {
        return this.numVisiblyInfectious;
    }

    public int getNumDeath() {
        return this.numDeath;
    }

    @Override
    public String toString() {
        String out = "";
        out += "\n[" + this.name + "]\n";
        out += "# healthy people: " + this.numHealthy + "\n";
        out += "# infected people: " + this.numInfected + "\n";
        out += "# sick people: " + this.numSick + "\n";
        out += "# immune people: " + this.numImmune + "\n";
        out += "# death people: " + this.numDeath + "\n";
        return out;
    }
}

interface FuncInterfaceMoveCheck {
    public boolean iter(Human h, Country c);
}

interface FuncInterfaceDeathCheck {
    public boolean iter(Human h, Country c);
}
