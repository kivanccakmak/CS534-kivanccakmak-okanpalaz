package simulation;
import java.util.*;

public class Simulator {

    private ArrayList<Country> countries = new ArrayList<Country>();
    private int daysToSimulate;
    private int dayPassed;
    private int nextBornId;

    public Simulator(int daysToSimulate) {
        this.daysToSimulate = daysToSimulate;
        this.dayPassed = 0;
        this.nextBornId = 0;
    }

    //now just creates 3 countries with 6 people, 2 infected; 4 healthy
    private void initSimulator () {
        int birthDay = 0;

        Country tr = new Country("Turkey");
        Country grc = new Country("Greece");
        Country rus = new Country("Russia");

        tr.addNeighbor(grc);
        tr.addNeighbor(rus);
        grc.addNeighbor(tr);
        grc.addNeighbor(rus);
        rus.addNeighbor(tr);
        rus.addNeighbor(grc);

        Human kivanc = new Human(birthDay, tr, false);
        Human okan = new Human(birthDay, tr, false);
        Human yorgo = new Human(birthDay, grc, false);
        Human kostas = new Human(birthDay, grc, false);
        Human yuri = new Human(birthDay, rus, true);
        Human oleg = new Human(birthDay, rus, true);

        this.countries.add(tr);
        this.countries.add(rus);
        this.countries.add(grc);
        for (Country c: this.countries) {
        }
    }

    private void passDay() {
        System.out.println("== DAY " + this.dayPassed + " ==\n");

        for (Country c: this.countries)
            c.updateHealth();

        printCountryStatus();
    }

    public void printCountryStatus() {
        for (Country c: this.countries) {
            System.out.println(c.toString());
        }
    }

    public void simulate() {
        this.initSimulator();
        if (this.daysToSimulate == 0) {
            return;
        }
        while (this.daysToSimulate > this.dayPassed) {
            this.passDay();
            this.dayPassed++;
        }
    }
}
