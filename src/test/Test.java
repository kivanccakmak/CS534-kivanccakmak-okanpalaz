package test;
import simulation.*;
import java.util.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

public class Test {
    public static void main(String[] args) {
        Simulator sim = new Simulator(3, 3);
        sim.populate(100, 10.0);
        ArrayList<JSONObject> info = new ArrayList<JSONObject>();
        info = sim.getWorldInfo();
        System.out.println(info);
    }
}
