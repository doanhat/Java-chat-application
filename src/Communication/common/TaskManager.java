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

    public void appendTask(Task task)
    {
        tasks.add(task);
        pool.execute(task);
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
