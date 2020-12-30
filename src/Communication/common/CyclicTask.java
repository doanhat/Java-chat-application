package Communication.common;

/**
 * Classe abstraite d'une tache cyclique implementant {@link java.lang.Runnable} et permettant de la lancer comme thread
 */
public abstract class CyclicTask implements Runnable {
    protected Boolean cancel = false;

    @Override
    public void run() {
        while (isActive()) {
            action();
        }

        cleanup();
    }

    public void stop() {
        cancel = true;
    }

    public boolean isActive() {
        return (!cancel);
    }

    protected abstract void action();

    protected void cleanup() {
    }
}
