package Communication.common;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import static java.util.concurrent.Executors.newCachedThreadPool;

public class TaskManager
{
    private ExecutorService pool;
    private List<Task> tasks;

    public TaskManager()
    {
        pool  = newCachedThreadPool();
        tasks = new ArrayList<>();
    }

    /**
     * Executer action cyclique au thread pool
     * @param task
     */
    public void appendTask(Task task)
    {
        tasks.add(task);
        pool.execute(task);
    }

    /**
     * Executer action one-shot au thread pool
     * @param oneShot
     */
    public void appendTask(Runnable oneShot)
    {
        pool.execute(oneShot);
    }

    public void shutdown()
    {
        pool.shutdown();

        for (Task t: tasks)
        {
            t.stop();
        }
    }
}
