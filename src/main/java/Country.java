import java.util.*;

public class Country {
    private String name;
    private ArrayList<Country> neighbors;
    private ArrayList<Human> people;
    private ArrayList<Human> arrivals;
    private Simulator simulator;
    private HealthStats stats;


    public static class HealthStats {
        private String name = "";
        private long healthy;
        private long infected;
        private long sick;
        private long immune;
        private long dead;
        private long superHealthy;

        private long infectious;
        private long visiblyInfectious;

        private long doctors;
        private long population;

        public String name() { return name; }

        public long healthyCount() { return healthy; }
        public long infectedCount() { return infected; }
        public long sickCount() { return sick; }
        public long immuneCount() { return immune; }
        public long deadCount() { return dead; }
        public long superHealthyCount() { return superHealthy; }

        public long infectiousCount() { return infectious; }
        public long visiblyInfectiousCount() { return visiblyInfectious; }

        public long population() { return population; }
        public long doctors() { return doctors; }

        public void add(HealthStats other) {
            healthy += other.healthy;
            infected += other.infected;
            sick += other.sick;
            immune += other.immune;
            dead += other.dead;
            superHealthy += other.superHealthy;

            infectious += other.infectious;
            visiblyInfectious += other.visiblyInfectious;

            population += other.population;
            doctors += other.doctors;
        }

        @Override
        public String toString() {
            String out = "";
            out += "Healthy: " + healthy + "\n";
            out += "Infected: " + infected + "\n";
            out += "Sick: " + sick + "\n";
            out += "Immune: " + immune + "\n";
            out += "Dead: " + dead + "\n";
            out += "SuperHealthy: " + superHealthy + "\n";
            return out;
        }
    }

    public Country(Simulator s, String n) {
        name = n;
        neighbors = new ArrayList<Country>();
        people = new ArrayList<Human>();
        arrivals = new ArrayList<Human>();
        simulator = s;
        updateHealthStats();
    }

    // Called to create a snapshot each time a day passes
    public void updateHealthStats() {
        HealthStats newStats = new HealthStats();
        newStats.name = name;
        newStats.healthy = people.stream().filter(p -> p.isHealthy()).count();
        newStats.infected = people.stream().filter(p -> p.isInfected()).count();
        newStats.sick = people.stream().filter(p -> p.isSick()).count();
        newStats.immune = people.stream().filter(p -> p.isImmune()).count();
        newStats.dead = people.stream().filter(p -> p.isDead()).count();
        newStats.superHealthy = people.stream().filter(p -> p.isSuperHealthy()).count();

        newStats.infectious = people.stream().filter(p -> p.isInfectious()).count();
        newStats.visiblyInfectious = people.stream().filter(p -> p.isVisiblyInfectious()).count();

        newStats.population = people.size();
        newStats.doctors = people.stream().filter(p -> p.isDoctor()).count();
        stats = newStats;
    }

    public HealthStats getStats() {
        return stats;
    }

    public void runHealthActions() {
        // avoid stream inside stream
        for (Human p: people) {
            p.passDay();
        }

        // remove people who moved
        people.removeIf(p -> p.country() != this);
    }

    public void processMoves() {
        for (Human h: arrivals) {
            people.add(h);
        }
        arrivals.clear();
    }

    // Initilization methods
    public void addHuman(Human h) { people.add(h); }
    public void removeHuman(Human h) { people.remove(h); }
    public void addNeighbor(Country c) { neighbors.add(c); }

    // Methods called from Human
    public void moveHuman(Human h) { arrivals.add(h); }
    public boolean hasVisiblyInfectious() { return stats.visiblyInfectiousCount() > 0; }
    public boolean hasInfectious() { return stats.infectiousCount() > 0; }
    public ArrayList<Country> neighbors() { return neighbors; }
    public ArrayList<Country> allCountries() { return simulator.countryList(); }
    public ArrayList<Human> residents() { return people; }

    public String name() { return name; }

    @Override
    public String toString() {
        String out = name + "\n";
        out += stats;
        out += "Population: " + people.size() + "\n";
        return out;
    }
}
