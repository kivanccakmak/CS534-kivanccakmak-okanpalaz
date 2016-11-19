 package simulation;
import java.util.Random;

public class Healthy extends HealthState {

    public Healthy(Human h) {
        this.human = h;
    }

    @Override
    public boolean isHealthy() {
        return true;
    }

    public boolean isInfectious() {
        return false;
    }

    public boolean isVisiblyInfectious() {
        return false;
    }

    private void beInfected() {
        this.human.currentHealth = this.human.infected;
        this.human.currentHealth.numInfectedDays = 0;
        this.human.notifyInfected();
    }

    public void passDay() {
        if (this.human.currentCountry.getNumInfectious() > 0) {
            Random random = new Random();
            double prob = this.globals.getProbToTransmitVirus();
            if (prob > random.nextDouble()) {
                System.out.println(this.human.getName() + " is infected");
                this.beInfected();
            }
        }
    }
}
