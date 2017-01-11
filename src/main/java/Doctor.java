import java.util.*;
import java.util.stream.*;

public class Doctor extends Human  {
    public Doctor(Country c) {
        super(c);
    }

    @Override
    public void passDay() {
        if (!health.isDead()) {
            SimulationRules rules = SimulationRules.getInstance();
            // reset daily vaccines
            int vaccines = rules.getDailyVaccines();

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
        }
        // do regular passday
        super.passDay();
    }
}
