package homeworks.HW13;
import java.util.Scanner;

/**
 * Created by Yaroslav on 31.08.16.
 */
public class Main {
    public static void main(String[] args) {
        ThreadPool threadPool = new ScalableThreadPool(3, 5);
        threadPool.start();
        new Thread(new TaskCreator(threadPool)).start();
    }
}

class TaskCreator implements Runnable {
    final ThreadPool threadPool;

    public TaskCreator(ThreadPool threadPool) {
        this.threadPool = threadPool;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        String result = "";
        while (true) {
            System.out.println("Write task (number seconds to working):");
            result = scanner.next();
            if (result.equals("exit")) {
                System.out.println("Goodbye");
                break;
            } else threadPool.execute(new taskImpl(Integer.parseInt(result)));
        }
    }

    private class taskImpl implements Runnable {
        int seconds;

        public taskImpl(int seconds) {
            this.seconds = seconds;
        }

        public void run() {
            System.out.println(Thread.currentThread().getName() + " will work " + seconds + " seconds");
            while (seconds != 0) {
                try {
                    System.out.println(Thread.currentThread().getName() + " sleep " + seconds + " seconds");
                    Thread.sleep(1000);
                    seconds--;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}



