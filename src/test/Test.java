package test;
import simulation.*;
import java.util.*;

public class Test {
    public static void main(String[] args) {
        SimulationGlobals glob = new SimulationGlobals();
        Simulator sim = new Simulator(glob.getSimDayLimit());
        sim.simulate();
    }
}
