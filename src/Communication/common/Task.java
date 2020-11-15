package Communication.common;

public abstract class Task implements Runnable
{
    private boolean cancel = false;

    public void stop()
    {
        cancel = true;
    }

    public boolean active()
    {
        return (!cancel);
    }
}
