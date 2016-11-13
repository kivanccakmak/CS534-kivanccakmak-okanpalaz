package simulation;
import java.util.*;

public class Country {
    private String name;
    private Country[] neighbours;
    private ArrayList<Human> humanArray = new ArrayList<Human>();

    private int numHealthy;
    private int numInfected;
    private int numSick;
    private int numImmune;
    private int numDeath;

    public Country(String name, int numHealthy, int numInfected) {
        this.name = name;
        this.numHealthy = numHealthy;
        this.numInfected = numInfected;
        this.numSick = 0;
        this.numImmune = 0;
        this.numDeath = 0;
    }

    public String getName() {
        return this.name;
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

    public int getNumDeath() {
        return this.numDeath;
    }

    public void enter(Human h) {
        if (h.isHealthy()) {
            this.numHealthy++;
        } else if(h.isInfected()) {
            this.numInfected++;
        } else if(h.isSick()) {
            this.numSick++;
        } else if(h.isImmune()) {
            this.numImmune++;
        }
        this.humanArray.add(h);
    }

    public void exit(Human h) {
        if (h.isHealthy()) {
            this.numHealthy--;
        } else if(h.isInfected()) {
            this.numInfected--;
        } else if(h.isSick()) {
            this.numSick--;
        } else if(h.isImmune()) {
            this.numImmune--;
        }
        this.humanArray.remove(h);
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
