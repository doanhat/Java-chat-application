package Communication.common;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static java.util.concurrent.Executors.newCachedThreadPool;

public class TaskManager {

    private ExecutorService pool;
    private List<Future<?>> cyclicTasks;

    public TaskManager() {
        pool = newCachedThreadPool();
        cyclicTasks = new ArrayList<>();
    }

    /**
     * Executer action cyclique au thread pool
     *
     * @param task
     */
    public void appendCyclicTask(Runnable task) {
        cyclicTasks.add(pool.submit(task));
    }

    /**
     * Executer action one-shot au thread pool
     *
     * @param oneShot
     */
    public void appendTask(Runnable oneShot) {
        pool.submit(oneShot);
    }

    public void shutdown() {
        pool.shutdown();

        for (Future<?> t : cyclicTasks) {
            t.cancel(true);
        }
    }
}
