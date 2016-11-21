package simulation;
import java.util.*;

public class Country {
    private String name;
    private ArrayList<Country> neighbors;
    private ArrayList<Human> people;
    private HealthStats stats;

    public class HealthStats {
        private long healthy;
        private long sick;
        private long immune;
        private long dead;
        private long infected;
        public long healthyCount() { return healthy; }
        public long sickCount() { return sick; }
        public long immuneCount() { return immune; }
        public long deadCount() { return dead; }
        public long infectedCount() { return infected; }
    }

    public Country(String n) {
        name = name;
        neighbors = new ArrayList<Country>();
        people = new ArrayList<Human>();
        stats = new HealthStats();
    }

    public void updateHealthStats() {
        HealthStats newStats = new HealthStats();
        newStats.healthy = people.stream().filter(p -> p.isHealthy()).count();
        newStats.sick = people.stream().filter(p -> p.isSick()).count();
        newStats.immune = people.stream().filter(p -> p.isImmune()).count();
        newStats.dead = people.stream().filter(p -> p.isDead()).count();
        newStats.infected = people.stream().filter(p -> p.isInfected()).count();
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
