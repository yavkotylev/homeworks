package homeworks.HW16.server;

import homeworks.HW16.Player;

/**
 * Created by Yaroslav on 13.09.16.
 */
public class Server {
    public static void main(String[] args) {
        Server s = new Server();
        s.createPair();
    }

    private void createPair(){
        Object playerLock1 = new Object();
        Object playerLock2 = new Object();
        Object startLock = new Object();
        Player player1 = new Player("ping", startLock);
        Player player2 = new Player("pong", startLock);
        Pair pair = new Pair(player1, player2, startLock);
        new Thread(pair).start();
    }
}
