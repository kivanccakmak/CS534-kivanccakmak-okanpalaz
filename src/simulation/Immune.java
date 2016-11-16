package simulation;

public class Immune extends HealthState {

    public Immune(Human h) {
        this.human = h;
    }

    @Override
    public boolean isImmune() {
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
        if (this.numInfectedDays == this.globals.getDayToBackHealthy()) {
            this.beHealthy();
        }
    }

    private void beHealthy() {
        this.human.currentHealth = this.human.healthy;
        this.numInfectedDays = 0;
        this.human.notifyRecovery();
    }
}
