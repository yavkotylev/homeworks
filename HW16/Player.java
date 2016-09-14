package homeworks.HW16;

/**
 * Created by Yaroslav on 13.09.16.
 */
public class Player implements Runnable {
    private final String hitMessage;
    private final Object startLock;

    public Player(String hitMessage, Object startLock) {
        this.hitMessage = hitMessage;
        this.startLock = startLock;
    }

    public void hit() {
        System.out.println(hitMessage);
    }

    @Override
    public void run() {
        while (true) {
            synchronized (startLock) {
                hit();
                try {
                    Thread.sleep(1000);
                    startLock.wait();
                } catch (InterruptedException e){
                    throw new RuntimeException(e);
                }
            }
        }
    }
}

