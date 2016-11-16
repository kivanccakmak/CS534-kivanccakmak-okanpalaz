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
    public void initSimulation () {
        int birthDay = 0; 
        int bornId;

        Country tr = new Country("Turkey");
        Country grc = new Country("Greece");
        Country rus = new Country("Russia");

        tr.addNeighbour(grc);
        tr.addNeighbour(rus);

        grc.addNeighbour(tr);
        grc.addNeighbour(rus);

        rus.addNeighbour(tr);
        rus.addNeighbour(grc);
        
        bornId = this.getNextBornId();
        Human kivanc = new Human("Kivanc", bornId, birthDay, tr, false);
        bornId = this.getNextBornId();
        Human okan = new Human("Okan", bornId, birthDay, tr, false);

        bornId = this.getNextBornId();
        Human yorgo = new Human("Yorgo", bornId, birthDay, grc, false); 
        bornId = this.getNextBornId();
        Human kostas = new Human("Kostas", bornId, birthDay, grc, false);

        bornId = this.getNextBornId();
        Human yuri = new Human("Yuri", bornId, birthDay, rus, true);
        bornId = this.getNextBornId();
        Human oleg = new Human("Oleg", bornId, birthDay, rus, true);
        
        this.countries.add(tr);
        this.countries.add(rus);
        this.countries.add(grc);
    }

    public void passDay() {
        System.out.println("== DAY" + this.dayPassed + " ==\n");

        for (Country c: this.countries) {
            c.passDay();
        }

        for (Country c: this.countries) {
            c.openGate();
        }

        for (Country c: this.countries) {
            System.out.println(c.toString());
        }
    }

    public void simulate() {
        this.initSimulation();

        if (this.daysToSimulate == 0) {
            return;
        }
        while (this.dayPassed < this.daysToSimulate) {
            this.passDay();
            this.dayPassed++;
        }
    }

}
