import java.util.*;
import java.util.stream.*;

public class Simulator {
    private ArrayList<Country> countries = new ArrayList<Country>();
    private int dayPassed;
    private int rows;
    private int cols;

    private void westNeighborAdd(Country c, int index) {
        int neighbor;
        if (index % cols != 0) {
            neighbor = index - 1;
        } else {
            neighbor = index - 1 + cols;
        }
        c.addNeighbor(countries.get(neighbor));
    }

    private void eastNeighborAdd(Country c, int index) {
        int neighbor;
        if ((index % cols) != (cols - 1)) {
            neighbor = index + 1;
        } else {
            neighbor = index + 1 - cols;
        }
        c.addNeighbor(countries.get(neighbor));
    }

    private void northNeighborAdd(Country c, int index) {
        int neighbor;
        if (index - rows > 0) {
            neighbor = index - rows;
        } else {
            neighbor = countries.size() - cols + (index % cols);
        }
        c.addNeighbor(countries.get(neighbor));
    }

    private void southNeighborAdd(Country c, int index) {
        int neighbor;
        if (index + rows < countries.size()) {
            neighbor = index + rows;
        } else {
            neighbor = index % cols;
        }
        c.addNeighbor(countries.get(neighbor));
    }

    // Generate a NxM grid
    public Simulator(int n, int m) throws IllegalArgumentException {
        if (n < 1 || m < 1) {
            throw new IllegalArgumentException("Invalid dimentions");
        }

        countries = new ArrayList<Country>();
        dayPassed = 0;
        rows = n;
        cols = m;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                countries.add(new Country(this, (row + 1) + "x" + (col + 1)));
            }
        }

        int idx = 0;
        for (Country c: countries) {
            westNeighborAdd(c, idx);
            eastNeighborAdd(c, idx);
            northNeighborAdd(c, idx);
            southNeighborAdd(c, idx);
            idx++;
        }
    }

    public void populate(int count, double percentInfected, double percentSuper, double percentDoctor) throws IllegalArgumentException {
        Random rng = new Random();
        if (count <= 0) {
            throw new IllegalArgumentException("count is invalid");
        }

        if (percentInfected > 100.0 || percentInfected < 0.0) {
            throw new IllegalArgumentException("percentInfected is invalid");
        }

        if (percentDoctor > 100.0 || percentDoctor < 0.0) {
            throw new IllegalArgumentException("percentDoctor is invalid");
        }

        // Doctor percentage doesn't overlap with Super and Infected percentages
        int docs = Math.round(((float)(percentDoctor / 100.0)) * count);
        int regular = count - docs;

        int supers = Math.round(((float)(percentSuper / 100.0)) * count);
        int infected = Math.round(((float)(percentInfected / 100.0)) * count);

        if (supers + infected > count) {
            throw new IllegalArgumentException("Super + Infected percentage is invalid");
        }

        ArrayList<Human> ppl = new ArrayList<Human>();

        // First create Humans and Doctors
        for (int i = 0; i < docs; i++) {
            int idx = rng.nextInt(countries.size());
            Country dest = countries.get(idx);

            // Human adds itself to the Country
            ppl.add(new Doctor(dest));
        }

        for (int i = 0; i < regular; i++) {
            int idx = rng.nextInt(countries.size());
            Country dest = countries.get(idx);

            // Human adds itself to the Country
            ppl.add(new Human(dest));
        }

        // Then generate health state
        int cnt = 0;
        while(cnt < infected) {
            int idx = rng.nextInt(ppl.size());
            Human h = ppl.get(idx);

            if (h.isHealthy()) {
                h.becomeInfected();
                cnt++;
            }
        }

        cnt = 0;
        while(cnt < supers) {
            int idx = rng.nextInt(ppl.size());
            Human h = ppl.get(idx);

            if (h.isHealthy()) {
                h.becomeSuperHealthy();
                cnt++;
            }
        }

        // Initialize health stats for the first day
        for (Country c: countries) {
            c.updateHealthStats();
        }
    }

    public ArrayList<Country> countryList() {
        return countries;
    }

    public void passDay() {
        System.out.println("== DAY " + dayPassed + " ==\n");

        // Run health related actions
        for (Country c: countries) {
            c.runHealthActions();
        }

        // Complete moves
        for (Country c: countries) {
            c.processMoves();
        }

        // Get the end of the day health state snapshot
        for (Country c: countries) {
            c.updateHealthStats();
        }

        dayPassed++;
    }

    public List<Country.HealthStats> getCountryStats() {
        List<Country.HealthStats> stats = countries
            .stream()
            .map(c -> c.getStats())
            .collect(Collectors.toList());
        return stats;
    }

    public int getDayPassed() {
        return this.dayPassed;
    }
}
