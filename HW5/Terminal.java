package homeworks.HW5;

import java.math.BigDecimal;

/**
 * Created by Yaroslav on 05.08.16.
 */
public interface Terminal {

    void checkAccount(Commands commands);

    void getMoney(Commands commands);

    void putMoney(Commands commands);

    String signIn(Commands commands);

    String signUp(Commands commands);

    void exit();


    void drawMenu(Commands mapCommands, Command... commands);

    String getUserInf(String field, Commands commands);

    void start();

    Command getCommand(Commands mapCommands, Command... commands);

}
