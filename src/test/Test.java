package test;
import simulation.*;
import java.util.*;

public class Test {
    public static void main(String[] args) {
        System.out.println("hello");
        int numDaysToSimulate = 20;
        Simulator sim = new Simulator(numDaysToSimulate);
        sim.simulate();
        //ArrayList<Human> persons = new ArrayList<Human>();
        //Country tr = new Country("Turkey");
        //persons.add(new Human("Kivanc", 0, 0, tr, false));
        //persons.add(new Human("Okan", 1, 0, tr, false));
        //persons.add(new Human("Osman", 2, 0, tr, false));

        //persons.removeIf(p -> p.getBornId() > 1);

        //for (Human h: persons) {
            //System.out.println(h.getName());
        //}
    }
}
