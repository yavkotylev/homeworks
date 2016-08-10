package homeworks.HW5;

import java.math.BigDecimal;

/**
 * Created by Yaroslav on 05.08.16.
 */
public interface TerminalServer {
    String signUp(String name, String pin);

    BigDecimal checkAccount(String id, String pin);

    String signIn(String name, String pin);

    void getMoney(String id, String pin, BigDecimal amount);

    void putMoney(String id, String pin, BigDecimal amount);

    void logOut(String id);

    boolean checkPin(String id, String pin);

}
