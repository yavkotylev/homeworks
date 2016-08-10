package homeworks.HW5;

/**
 * Created by Yaroslav on 07.08.16.
 */
public class Main {
    public static void main(String[] args) {
        try {
            TerminalServer server = new TerminalServerImpl();
            Terminal terminal = new TerminalImpl(server);
            terminal.start();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
