package simulation;

public abstract class HealthState {

    protected int numInfectedDays;
    HealthGlobals globals = new HealthGlobals();
    Human human;
    protected boolean isDeath = false;

    public abstract void passDay();
    public abstract boolean isVisiblyInfectious();
    public abstract boolean isInfectious();

    public boolean isHealthy() {
        return false;
    }

    public boolean isInfected() {
        return false;
    }

    public boolean isSick() {
        return false;
    }

    public boolean isImmune() {
        return false;
    }

    public boolean getIsDeath() {
        return this.isDeath;
    }
}
