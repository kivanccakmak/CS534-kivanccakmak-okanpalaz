package simulation;
import java.util.*;

public class Country {
    private String name;
    private ArrayList<Country> neighbors;
    private ArrayList<Human> people;
    private HealthStats stats;

    public class HealthStats {
        private long healthy;
        private long infected;
        private long sick;
        private long immune;
        private long dead;
        private long infectious;
        private long visiblyInfectious;

        public long healthyCount() { return healthy; }
        public long infectedCount() { return infected; }
        public long sickCount() { return sick; }
        public long immuneCount() { return immune; }
        public long deadCount() { return dead; }
        public long infectiousCount() { return infectious; }
        public long visiblyInfectiousCount() { return visiblyInfectious; }

        @Override
        public String toString() {
            String out = "";
            out += "Healthy: " + healthy + "\n";
            out += "Infected: " + infected + "\n";
            out += "Sick: " + sick + "\n";
            out += "Immune: " + immune + "\n";
            out += "Dead: " + dead + "\n";
            return out;
        }
    }

    public Country(String n) {
        name = n;
        neighbors = new ArrayList<Country>();
        people = new ArrayList<Human>();
        updateHealthStats();
    }

    // Called to create a snapshot each time a day passes
    public void updateHealthStats() {
        HealthStats newStats = new HealthStats();
        newStats.healthy = people.stream().filter(p -> p.isHealthy()).count();
        newStats.infected = people.stream().filter(p -> p.isInfected()).count();
        newStats.sick = people.stream().filter(p -> p.isSick()).count();
        newStats.immune = people.stream().filter(p -> p.isImmune()).count();
        newStats.dead = people.stream().filter(p -> p.isDead()).count();
        newStats.infectious = people.stream().filter(p -> p.isInfectious()).count();
        newStats.visiblyInfectious = people.stream().filter(p -> p.isVisiblyInfectious()).count();
        stats = newStats;
    }


    public void passDay() {
        people.stream().forEach(p -> p.passDay());

        // remove people who moved
        people.removeIf(p -> p.country() != this);
    }

    public boolean hasVisiblyInfectious() {
        return stats.visiblyInfectiousCount() > 0;
    }

    public boolean hasInfectious() {
        return stats.infectiousCount() > 0;
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

    public String name() {
        return name;
    }

    public ArrayList<Country> neighbors() {
        return neighbors;
    }

    @Override
    public String toString() {
        String out = name + ":\n";
        out += stats;
        return out;
    }
}
