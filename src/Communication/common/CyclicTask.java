package Communication.common;

public abstract class CyclicTask implements Runnable {
    protected Boolean cancel = false;

    @Override
    public void run() {
        while (true) {
            synchronized (cancel) {
                if (cancel) {
                    break;
                }
            }

            action();
        }

        cleanup();
    }

    public void stop() {
        synchronized (cancel) {
            cancel = true;
        }
    }

    public boolean isActive() {
        return (!cancel);
    }

    protected abstract void action();

    protected void cleanup() {
    }
}
