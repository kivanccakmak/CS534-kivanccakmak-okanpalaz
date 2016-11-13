package simulation;
import java.util.Random;

public class Sick extends HealthState {

    public Sick(Human h) {
        this.human = h;
    }

    @Override
    public boolean isSick() {
        return true;
    }

    public boolean isInfectious() {
        return true;
    }

    public boolean isVisiblyInfectious() {
        return true;
    }

    public void passDay() {
        this.numInfectedDays++;
        if (this.numInfectedDays == this.globals.getDayToDie()) {
            Random random = new Random();
            if (this.globals.getProbToDie() < random.nextDouble()) {
                this.die();
            }
        } else if (this.numInfectedDays == this.globals.getDayToImmune()) {
            this.beImmune();
        }
    }

    private void die() {
        this.human.die();
    }

    private void beImmune() {
        this.human.currentHealth = this.human.immune;
        this.human.currentCountry.decrNumSick();
        this.human.currentCountry.incrNumImmune();
        this.human.currentCountry.decrNumVisiblyInfectious();
    }
}
