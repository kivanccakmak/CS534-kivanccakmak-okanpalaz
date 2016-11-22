package test;
import simulation.*;
import java.util.*;

public class Test {
    public static void main(String[] args) {
        Simulator sim = new Simulator(SimulationGlobals.getSimDayLimit());
        sim.simulate();
    }
}
