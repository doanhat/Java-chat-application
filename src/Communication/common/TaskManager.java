package Communication.common;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.newCachedThreadPool;

public class TaskManager {

    private final ExecutorService pool;
    private final List<CyclicTask> cyclicTasks;

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
        pool.execute(task);
    }

    /**
     * Executer action one-shot au thread pool
     *
     * @param oneShot
     */
    public void appendTask(Runnable oneShot) {
        pool.execute(oneShot);
    }

    /**
     * Terminate all thread and shutdown thread pool
     */
    public void shutdown() {
        System.err.println("Task manager s'arrete, annuler " + cyclicTasks.size() + " taches !");

        for (CyclicTask t : cyclicTasks) {
            t.stop();
        }

        pool.shutdownNow();
    }
}
