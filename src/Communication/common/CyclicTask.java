package Communication.common;

public abstract class CyclicTask implements Runnable
{
    // NOTE: use cancel flag to stop run() function
    protected boolean cancel = false;

    @Override
    public void run()
    {
        while (!cancel)
        {
            action();
        }

        cleanup();
    }

    public void stop()
    {
        cancel = true;
    }

    public boolean isActive()
    {
        return (!cancel);
    }

    protected abstract void action();

    protected void cleanup()
    {
    }
}
