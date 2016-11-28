import java.util.*;
import java.awt.FlowLayout;
import javax.swing.*;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

public class Test {
    public static void main(String[] args) {
        JSONObject obj = new JSONObject();
        obj.put("name", "mkyong.com");
        obj.put("age", new Integer(100));
        System.out.println(obj);
        //Simulator sim = new Simulator(3, 3);
        //sim.populate(100, 10.0);
        //ArrayList<JSONObject> info = new ArrayList<JSONObject>();
        //info = sim.getWorldInfo();
        //System.out.println(info);
    }
}
