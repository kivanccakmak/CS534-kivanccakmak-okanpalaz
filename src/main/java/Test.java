import java.util.*;
import java.awt.FlowLayout;
import javax.swing.*;

public class Test {
    public static void main(String[] args) {
        int numVertical = 3;
        int numHorizontal = 3;
        int numPeople = 18;
        double percentInfected = 0.3;
        double percentSuper = 0.3;
        double percentDoctor = 0.3;
        int vaccineCnt = 3;
        WorldController cntrl =
            new WorldController(numVertical, numHorizontal,
                    numPeople, percentInfected, percentSuper, percentDoctor, vaccineCnt);
        cntrl.startSimulation();
    }
}
