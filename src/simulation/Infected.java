package simulation;

public class Infected extends HealthState {

    public Infected(Human h) {
        this.human = h;
    }

    @Override
    public boolean isInfected() {
        return true;
    }

    public boolean isInfectious() {
        return true;
    }

    public boolean isVisiblyInfectious() {
        return false;
    }

    public void passDay() {
        this.numInfectedDays++;
        if (this.numInfectedDays  == this.globals.getDayToSick()) {
            this.beSick();
        }
    }

    private void beSick() {
        this.human.currentHealth = this.human.sick;
        this.human.currentCountry.decrNumInfected();
        this.human.currentCountry.incrNumSick();
        this.human.currentCountry.incrNumVisiblyInfectious();
    }

}
