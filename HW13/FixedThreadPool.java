import java.util.LinkedList;
import java.util.List;

/**
 * Created by Yaroslav on 31.08.16.
 */
public class FixedThreadPool implements ThreadPool {
    private final int numberThread;
    private final Thread[] workers;
    private final List<Runnable> tasks = new LinkedList<Runnable>();
    private final Object lock = 0;

    public FixedThreadPool(int numberThread) {
        this.numberThread = numberThread;
        workers = new Thread[numberThread];
        for (int i = 0; i < numberThread; i++) {
            workers[i] = new Thread(new Worker());
        }
    }

    public void execute(Runnable runnable) {
        System.out.println("Task received");
        synchronized (lock) {
            tasks.add(runnable);
            System.out.println("size = " + tasks.size());
            lock.notify();
        }
    }

    public void start() {
        for (int i = 0; i < numberThread; i++) {
            workers[i].start();
        }
    }

    private class Worker extends Thread {

        @Override
        public void run() {
            while (true) {
                try {
                    if (tasks.size() == 0) {
                        System.out.println(Thread.currentThread().getName() + " wait.");
                        synchronized (lock) {
                            lock.wait();
                        }
                    } else {
                        System.out.println(Thread.currentThread().getName() + " run.");
                        tasks.remove(0).run();
                        System.out.println("Tasks size = " + tasks.size());
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}

