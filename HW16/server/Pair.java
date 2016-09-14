package homeworks.HW16.server;

import homeworks.HW16.Player;

import java.net.InetAddress;

/**
 * Created by Yaroslav on 13.09.16.
 */
public class Pair implements Runnable {
    private final Player player1;
    private final Player player2;
    private final Object startLock;

    public Pair(Player player1, Player player2, Object startLock) {
        this.player1 = player1;
        this.player2 = player2;
        this.startLock = startLock;
    }


    @Override
    public void run() {
        new Thread(player1).start();
        new Thread(player2).start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (true){
            synchronized (startLock){
                startLock.notify();
            }
        }
    }
}
