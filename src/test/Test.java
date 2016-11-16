package test;
import simulation.*;

public class Test {
    public static void main(String[] args) {
        System.out.println("hello");
        int numDaysToSimulate = 20;
        Simulator sim = new Simulator(numDaysToSimulate);
        sim.simulate();
    }
}
