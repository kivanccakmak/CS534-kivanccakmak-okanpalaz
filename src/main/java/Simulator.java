import java.util.*;

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
    public Simulator(int n, int m) {
        countries = new ArrayList<Country>();
        dayPassed = 0;
        rows = n;
        cols = m;

        for (int row = 0; row < cols; row++) {
            for (int col = 0; col < cols; col++) {
                countries.add(new Country((row + 1) + "x" + (col + 1)));
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

    public void populate(int count, double percentInfected, double percentSuper) {
        Random rng = new Random();
        // TODO: Throw exception percentages don't make sense
        int infected = Math.round(((float)(percentInfected / 100.0)) * count);
        int supers = Math.round(((float)(percentSuper / 100.0)) * count);
        int healthy = count - infected - supers;

        for (int i = 0; i < infected; i++) {
            int idx = rng.nextInt(countries.size());
            Country dest = countries.get(idx);

            // Human adds itself to the list
            new Human(dest, Human.InitialHealth.INFECTED);
        }

        for (int i = 0; i < healthy; i++) {
            int idx = rng.nextInt(countries.size());
            Country dest = countries.get(idx);

            // Human adds itself to the list
            new Human(dest, Human.InitialHealth.HEALTHY);
        }

        for (int i = 0; i < supers; i++) {
            int idx = rng.nextInt(countries.size());
            Country dest = countries.get(idx);

            // Human adds itself to the list
            new Human(dest, Human.InitialHealth.SUPERHEALTHY);
        }
    }

    public void passDay() {
        System.out.println("== DAY " + dayPassed + " ==\n");

        // Get the daily stat snapshot for decisions
        for (Country c: countries) {
            c.updateHealthStats();
        }

        // Run health related actions
        for (Country c: countries) {
            c.runHealthActions();
        }

        // Complete moves
        for (Country c: countries) {
            c.processMoves();
        }

        for (Country c: countries) {
            System.out.println(c.toString());
        }

        dayPassed++;
    }
}
