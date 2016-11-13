package simulation;
import java.util.*;

public class Country {
    private String name;
    private Country[] neighbours;
    private ArrayList<Human> citizens = new ArrayList<Human>();
    private ArrayList<Human> graveyard = new ArrayList<Human>();

    private int numHealthy;
    private int numInfected;
    private int numSick;
    private int numImmune;
    private int numDeath;
    private int numInfectious;
    private int numVisiblyInfectious;

    public Country(String name) {
        this.name = name;
        this.numHealthy = 0;
        this.numInfected = 0;
        this.numSick = 0;
        this.numImmune = 0;
        this.numDeath = 0;
    }

    public void passDay() {
        for (Human h: citizens) {
            h.passDay();
        }
    }

    public void enter(Human h) {
        if (h.isHealthy()) {
            this.incrNumHealthy();
        } else if(h.isInfected()) {
            this.incrNumInfected();
        } else if(h.isSick()) {
            this.incrNumSick();
        } else if(h.isImmune()) {
            this.incrNumImmune();
        }

        if (h.isInfectious()) {
            this.incrNumInfectious();
            if (h.isVisiblyInfectious()) {
                this.incrNumVisiblyInfectious();
            }
        }

        System.out.println("Welcome " + h.getName());
        this.citizens.add(h);
    }

    public void recordDeath(Human h) {
        this.incrNumDeath();
        this.exit(h);
        System.out.println("RIP " + h.getName());
        this.graveyard.add(h);
    }

    public void exit(Human h) {
        if (h.isHealthy()) {
            this.decrNumHealthy();
        } else if(h.isInfected()) {
            this.decrNumInfected();
        } else if(h.isSick()) {
            this.decrNumSick();
        } else if(h.isImmune()) {
            this.decrNumImmune();
        }

        if (h.isInfectious()) {
            this.decrNumInfectious();
            if (h.isVisiblyInfectious()) {
                this.decrNumVisiblyInfectious();
            }
        }
        this.citizens.remove(h);
    }

    public Country[] getNeighbours() {
        return this.neighbours;
    }

    public String getName() {
        return this.name;
    }

    public int getNumHealthy() {
        return this.numHealthy;
    }

    public void incrNumHealthy() {
        this.numHealthy++;
    }

    public void decrNumHealthy() {
        this.numHealthy--;
    }

    public int getNumInfected() {
        return this.numInfected;
    }

    public void incrNumInfected() {
        this.numInfected++;
    }

    public void decrNumInfected() {
        this.numInfected--;
    }

    public int getNumSick() {
        return this.numSick;
    }

    public void incrNumSick() {
        this.numSick++;
    }

    public void decrNumSick() {
        this.numSick--;
    }

    public int getNumImmune() {
        return this.numImmune;
    }

    public void incrNumImmune() {
        this.numImmune++;
    }

    public void decrNumImmune() {
        this.numImmune--;
    }

    public int getNumInfectious() {
        return this.numInfectious;
    }

    public void incrNumInfectious() {
        this.numInfectious++;
    }

    public void decrNumInfectious() {
        this.numInfectious--;
    }

    public int getNumVisiblyInfectious() {
        return this.numVisiblyInfectious;
    }

    public void incrNumVisiblyInfectious() {
        this.numVisiblyInfectious++;
    }

    public void decrNumVisiblyInfectious() {
        this.numVisiblyInfectious--;
    }

    public int getNumDeath() {
        return this.numDeath;
    }

    public void incrNumDeath() {
        this.numDeath++;
    }

    @Override
    public String toString() {
        String out = "";
        out += "name: " + this.name + "\n";
        out += "# healthy people: " + this.numHealthy + "\n";
        out += "# infected people: " + this.numInfected + "\n";
        out += "# sick people: " + this.numSick + "\n";
        out += "# immune people: " + this.numImmune + "\n";
        out += "# death people: " + this.numDeath + "\n";
        return out;
    }
}
