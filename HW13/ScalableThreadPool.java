import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yaroslav on 06.09.16.
 */
public class ScalableThreadPool implements ThreadPool {
    private final int min;
    private final int max;
    private final List<Thread> workers;
    private final List<Runnable> tasks;
    private final Object lock;
    private final Object lockList;

    public ScalableThreadPool(int min, int max) {
        this.min = min;
        this.max = max;
        workers = new ArrayList<Thread>();
        tasks = new ArrayList<Runnable>();
        lock = new Object();
        lockList = new Object();
        for (int i = 0; i < min; i++) {
            workers.add(new Worker());
        }
    }

    public void execute(Runnable runnable) {

        synchronized (lockList) {
            if (tasks.size() > 0 && workers.size() < max) {
                Thread worker = new Worker();
                worker.start();
                workers.add(worker);
            }
            tasks.add(runnable);
        }
        synchronized (lock) {
            lock.notify();
        }
    }

    public void start() {
        synchronized (lockList) {
            for (Thread worker : workers) {
                worker.start();
            }
        }
    }

    private class Worker extends Thread {
        @Override
        public void run() {
            while (true) {
                synchronized (lockList) {
                    if (tasks.size() == 0 && workers.size() > min) {
                        workers.remove(this);
                        return;
                    }
                }

                if (tasks.size() == 0) {
                    try {
                        synchronized (lock) {
                            System.out.println(Thread.currentThread().getName() + " wait");
                            lock.wait();
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }


                Runnable runnable = null;
                synchronized (lockList) {
                    if (tasks.size() >= 0) {
                        System.out.println("Workers size = " + workers.size());
                        System.out.println("tasks size = " + tasks.size() + "\n");
                        runnable = tasks.remove(0);
                    }
                }
                if (runnable != null) runnable.run();
                System.out.println(Thread.currentThread().getName() + " end. Tasks size = " + tasks.size() + "\n");
            }
        }
    }
}


