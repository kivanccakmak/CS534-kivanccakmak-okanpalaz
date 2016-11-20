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

    private int getNextBornId() {
        int val = this.nextBornId;
        this.nextBornId++;
        return val;
    }
    
    //now just creates 3 countries with 6 people, 2 infected; 4 healthy
    private void initSimulator () {
        int birthDay = 0; 

        Country tr = new Country("Turkey");
        Country grc = new Country("Greece");
        Country rus = new Country("Russia");

        tr.addNeighbour(grc);
        tr.addNeighbour(rus);
        grc.addNeighbour(tr);
        grc.addNeighbour(rus);
        rus.addNeighbour(tr);
        rus.addNeighbour(grc);

        Human kivanc = new Human("Kivanc", getNextBornId(), birthDay, tr, false);
        Human okan = new Human("Okan", getNextBornId(), birthDay, tr, false);
        Human yorgo = new Human("Yorgo", getNextBornId(), birthDay, grc, false);
        Human kostas = new Human("Kostas", getNextBornId(), birthDay, grc, false);
        Human yuri = new Human("Yuri", getNextBornId(), birthDay, rus, true);
        Human oleg = new Human("Oleg", getNextBornId(), birthDay, rus, true);
        
        this.countries.add(tr);
        this.countries.add(rus);
        this.countries.add(grc);
        for (Country c: this.countries) {
            c.reqPullArrivals();
        }
    }

    private void passDay() {
        System.out.println("== DAY " + this.dayPassed + " ==\n");

        for (Country c: this.countries)
            c.reqUpdateHealth();

        for (Country c: this.countries)
            c.reqUpdateLocation();

        for (Country c: this.countries)
            c.reqPullArrivals();

        for (Country c: this.countries)
            c.reqCountHealthStats();

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
