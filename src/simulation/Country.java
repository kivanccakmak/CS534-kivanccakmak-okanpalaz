package simulation;
import java.util.*;

public class Country {
    private String name;
    private ArrayList<Country> neighbors;
    private ArrayList<Human> people;
    private ArrayList<Human> arrivals;
    private HealthStats stats;

    public class HealthStats {
        private int healthy;
        private int sick;
        private int immune;
        private int dead;
        private int infected;
        public void addHealthy() { healthy++; }
        public void addSick() { sick++; }
        public void addImmune() { immune++; }
        public void addDead() { dead++; }
        public void addInfected() { infected++; }
        public int getHealthy() { return healthy; }
        public int getSick() { return sick; }
        public int getImmnune() { return immune; }
        public int getDead() { return dead; }
        public int getInfected() { return infected; }
    }

    public Country(String n) {
        name = name;
        neighbors = new ArrayList<Country>();
        people = new ArrayList<Human>();
        arrivals = new ArrayList<Human>();
        stats = new HealthStats();
    }

    public void updateHealthStats() {
        HealthStats newStats = new HealthStats();
        people.stream().filter(p -> p.isHealthy()).forEach(p -> stats.addHealthy());
        people.stream().filter(p -> p.isDead()).forEach(p -> stats.addDead());
        people.stream().filter(p -> p.isSick()).forEach(p -> stats.addSick());
        people.stream().filter(p -> p.isImmune()).forEach(p -> stats.addImmune());
        people.stream().filter(p -> p.isDead()).forEach(p -> stats.addDead());
        people.stream().filter(p -> p.isInfected()).forEach(p -> stats.addInfected());
        stats = newStats;
    }

    // Called each time a day passes
    public void updateHealth() {
        people.stream().forEach(p -> p.updateHealth(stats));
    }

    public void updateActions() {
    }

    public void removeHuman(Human h) {
        people.remove(h);
    }

    public void addHuman(Human h) {
        people.add(h);
    }

    public void addNeighbor(Country c) {
        neighbors.add(c);
    }

    public String getName() {
        return name;
    }

    public ArrayList<Country> getNeighbors() {
        return neighbors;
    }

    @Override
    public String toString() {
        String out = "";
        return out;
    }
}
