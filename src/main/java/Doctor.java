import java.util.*;
import java.util.stream.*;

public class Doctor extends Human  {
    private static int dailyVaccines = 10;

    public static void setDailyVaccines(int v) {
        if (v >= 0) {
            dailyVaccines = v;
        } else {
            // TODO: Error
        }
    }

    public Doctor(Country c) {
        super(c);
    }

    @Override
    public void passDay() {
        // reset daily vaccines
        int vaccines = dailyVaccines;

        List<Human> candidates = country.residents()
            .stream()
            .filter(h -> h.isVaccineCandiate())
            .collect(Collectors.toList());

        for (Human h: candidates) {
            if (vaccines > 0) {
                h.vaccinate();
                vaccines--;
            } else {
                break;
            }
        }

        // do regular passday
        super.passDay();
    }
}
