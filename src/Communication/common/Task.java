package Communication.common;

public abstract class Task implements Runnable
{
    // NOTE: use cancel flag to stop run() function
    protected boolean cancel = false;

    public void stop()
    {
        cancel = true;
    }

    public boolean isActive()
    {
        return (!cancel);
    }

    public void cleanup()
    {
    }
}
