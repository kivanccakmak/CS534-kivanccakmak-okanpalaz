package test;
import simulation.*;
import java.util.*;

public class Test {
    public static void main(String[] args) {
        Simulator sim = new Simulator(3, 3);
        sim.populate(100, 10.0);
        for (int i = 0; i < 1000; i++) {
            sim.passDay();
        }
    }
}
