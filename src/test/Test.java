package test;
import simulation.*;

public class Test {
    public static void main(String[] args) {
        Country tr = new Country("Turkey", 0, 0);
        Country eng = new Country("England", 0, 0);
        Human kivanc = new Human("Kivanc", 0, 0, tr);
        Human okan = new Human("Okan", 1, 0, eng);
        System.out.println(kivanc.toString());
        System.out.println(okan.toString());
        System.out.println(tr.toString());
        System.out.println(eng.toString());
        kivanc.move(eng);
        System.out.println(kivanc.toString());
        System.out.println(tr.toString());
        System.out.println(eng.toString());
    }
}
