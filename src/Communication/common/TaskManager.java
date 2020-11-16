package Communication.common;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import static java.util.concurrent.Executors.newCachedThreadPool;

public class TaskManager {

    private ExecutorService pool;
    private List<CyclicTask> cyclicTasks;

    public TaskManager() {
        pool = newCachedThreadPool();
        cyclicTasks = new ArrayList<>();
    }

    /**
     * Executer action cyclique au thread pool
     *
     * @param task
     */
    public void appendCyclicTask(CyclicTask task) {
        cyclicTasks.add(task);
        pool.submit(task);
    }

    /**
     * Executer action one-shot au thread pool
     *
     * @param oneShot
     */
    public void appendTask(Runnable oneShot) {
        pool.execute(oneShot);
    }

    public void shutdown() {
        pool.shutdown();

        for (CyclicTask t : cyclicTasks) {
            t.stop();
        }
    }
}
