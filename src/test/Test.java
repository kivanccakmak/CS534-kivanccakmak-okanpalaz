package test;
import simulation.*;

public class Test {
    public static void main(String[] args) {
        int bornId = 0;
        int birthDay = 0;
        Country tr = new Country("Turkey");
        Country eng = new Country("England");
        Human kivanc = new Human("Kivanc", bornId, birthDay, tr, false);
        bornId++;
        System.out.println(kivanc.toString());
        System.out.println(tr.toString());
        kivanc.move(eng);
        System.out.println(kivanc.toString());
        System.out.println(tr.toString());
        System.out.println(eng.toString());
    }
}
