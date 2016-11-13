package simulation;

public abstract class HealthState {

    int numInfectedDays;
    HealthGlobals globals;
    Human human;

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
}
