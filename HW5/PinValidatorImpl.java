package homeworks.HW5;

/**
 * Created by Yaroslav on 07.08.16.
 */
public class PinValidatorImpl implements PinValidator {
    private final String userId;
    private final TerminalServer server;
    int count;

    public PinValidatorImpl(String userId, TerminalServer server) {
        this.server = server;
        this.userId = userId;
        this.count = 0;
    }

    @Override
    public boolean checkPin(String PIN) {
        if (server.checkPin(userId, PIN) == true) {
            count = 0;
            return true;
        } else {
            count++;
            if (count >= 3) {
                int seconds = 5;
                System.out.println("Wrong PIN 3 times, wait " + seconds + " seconds");

                while (seconds > 0) {
                    System.out.println("Wait " + seconds + " seconds, please.");
                    seconds--;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ignore) {
                    }
                }
            }
            return false;
        }
    }
}
