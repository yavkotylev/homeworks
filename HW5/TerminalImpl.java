package homeworks.HW5;

import com.sun.deploy.uitoolkit.ui.ConsoleHelper;

import java.io.Console;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;

/**
 * Created by Yaroslav on 05.08.16.
 */
public class TerminalImpl implements Terminal {
    private String id;
    private final TerminalServer server;
    private PinValidator pinValidator;
    private final Scanner scanner;

    public TerminalImpl(TerminalServer server) {
        this.id = null;
        this.server = server;
        this.pinValidator = null;
        scanner = new Scanner(System.in);
    }

    @Override
    public void checkAccount(Commands commands) {
        String pin;
        while (true) {
            pin = getUserInf("pin", commands);
            if (pin != null) {

                if (pinValidator.checkPin(pin) == true) {
                    try {
                        System.out.println("\nYou have " + server.checkAccount(id, pin).toString() + "$ on your account.");
                    } catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                        return;
                    }
                } else {
                    System.out.println("Wrong pin. Try again!\n");
                }
            } else {
                return;
            }
        }
    }

    @Override
    public void getMoney(Commands commands) {
        workWithMoney("You have successfully removed the money.", commands);
    }

    @Override
    public void putMoney(Commands commands) {
        workWithMoney("You have successfully put the money.", commands);
    }

    private void workWithMoney(String resultStr, Commands commands) {
        String pin;
        String amountMoneyString;
        BigDecimal amountMoney;
        while (true) {
            pin = getUserInf("pin", commands);
            amountMoneyString = getUserInf("amount money", commands);
            if (amountMoneyString != null) {
                amountMoney = BigDecimal.valueOf(Double.parseDouble(amountMoneyString));
            } else {
                return;
            }
            if (pin != null) {

                if (pinValidator.checkPin(pin) == true) {
                    try {
                        if (resultStr.equals("You have successfully put the money.")) {
                            server.putMoney(id, pin, amountMoney);
                        } else {
                            server.getMoney(id, pin, amountMoney);
                        }
                        System.out.println(resultStr);
                        return;
                    } catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                        return;
                    }
                } else {
                    System.out.println("Wrong pin. Try again!\n");
                }
            } else {
                return;
            }
        }
    }


    @Override
    public String signIn(Commands commands) {
        String name;
        String pin;
        String resultId = null;
        name = getUserInf("Name", commands);
        if (name == null) {
            return null;
        }

        pin = getUserInf("Pin", commands);
        if (pin == null) {
            return null;
        }

        try {
            resultId = server.signIn(name, pin);
        } catch (RuntimeException e) {
            System.out.println("\n" + e.getMessage() + "\n");
        }
        return resultId;

    }

    @Override
    public String signUp(Commands commands) {
        String name;
        String pin;
        String resultId = null;
        name = getUserInf("Name", commands);
        if (name == null) {
            return null;
        }

        pin = getUserInf("Pin", commands);
        if (pin == null) {
            return null;
        }

        try {
            resultId = server.signUp(name, pin);
        } catch (RuntimeException e) {
            System.out.println("\n" + e.getMessage() + "\n");
            signUp(commands);
        }

        try {
            resultId = server.signIn(name, pin);
        } catch (RuntimeException e) {
            System.out.println("\n" + e.getMessage() + "\n");
        }

        return resultId;
    }

    @Override
    public void exit() {
        server.logOut(id);
        id = null;
        pinValidator = null;
    }


    @Override
    public void drawMenu(Commands mapCommands, Command... commands) {
        if (id != null) {
            System.out.println("*** You are welcome, choose action ***");
        } else {
            System.out.println("*** Choose action ***");
        }

        for (Command command : commands) {
            System.out.println(">" + mapCommands.commandStringMap.get(command));
        }
    }

    @Override
    public String getUserInf(String field, Commands commands) {
        while (true) {
            System.out.println("Write " + field.toUpperCase() + " or \"Exit\" to leave menu:");
            String userCommand = scanner.nextLine();
            if (commands.stringCommandMap.get(userCommand) == Command.EXIT) {
                System.out.println("\n\n");
                return null;
            }

            if (userCommand.contains(" ") == true) {
                System.out.println(field + " shouldn't contains spaces. Try again");
                continue;
            }

            if (field.equals("amount money") == true) {
                try {
                    Double.parseDouble(userCommand);
                    return userCommand;
                } catch (NumberFormatException e) {
                    System.out.println("Wrong number format!");
                }
            }
            return userCommand;
        }
    }

    @Override
    public void start() {
        Commands commands = new Commands();
        boolean startMenu = true;
        boolean userMenu = false;


        while (true) {

            Command userCommand = null;


            //Get command
            if (startMenu) {
                userCommand = getCommand(commands, Command.SIGN_IN, Command.SIGN_UP, Command.EXIT);
            }

            if (userMenu) {
                userCommand = getCommand(commands, Command.CHECK_ACCOUNT, Command.GET_MONEY, Command.PUT_MONEY, Command.EXIT);
            }


            //Perform command in start menu
            if (startMenu && userCommand == Command.SIGN_IN) {
                id = signIn(commands);
                if (id != null) {
                    userMenu = true;
                    startMenu = false;
                    pinValidator = new PinValidatorImpl(id, server);
                }
                continue;
            }

            if (startMenu && userCommand == Command.SIGN_UP) {
                id = signUp(commands);
                if (id != null) {
                    userMenu = true;
                    startMenu = false;
                    pinValidator = new PinValidatorImpl(id, server);
                }
                continue;
            }

            if (startMenu && userCommand == Command.EXIT) {
                scanner.close();
                break;
            }


            //Perform command in user menu
            if (userMenu && userCommand == Command.EXIT) {
                userMenu = false;
                startMenu = true;
                exit();
                continue;
            }


            if (userMenu && userCommand == Command.GET_MONEY) {
                getMoney(commands);
            }

            if (userMenu && userCommand == Command.PUT_MONEY) {
                putMoney(commands);
            }

            if (userMenu && userCommand == Command.CHECK_ACCOUNT) {
                checkAccount(commands);
            }
        }

    }

    @Override
    public Command getCommand(Commands mapCommands, Command... commands) {
        Command result = null;
        String commandStr;
        boolean goodCommand = false;
        while (goodCommand == false) {
            drawMenu(mapCommands, commands);
            System.out.println("\nWrite your command:");
            commandStr = scanner.nextLine();

            for (Command command : commands) {
                if (mapCommands.stringCommandMap.get(commandStr) == command) {
                    goodCommand = true;
                    result = command;
                    System.out.println("\n\n");
                    break;
                }
            }

            if (goodCommand == false) {
                System.out.println("Wrong command, try again!\n");
            } else {
                break;
            }
        }
        return result;
    }


}

class Commands {
    final Map<String, Command> stringCommandMap;

    final Map<Command, String> commandStringMap;

    Commands() {
        stringCommandMap = new HashMap<>();
        stringCommandMap.put("Sign in", Command.SIGN_IN);
        stringCommandMap.put("Sign up", Command.SIGN_UP);
        stringCommandMap.put("Get money", Command.GET_MONEY);
        stringCommandMap.put("Put money", Command.PUT_MONEY);
        stringCommandMap.put("Exit", Command.EXIT);
        stringCommandMap.put("Check account", Command.CHECK_ACCOUNT);

        commandStringMap = new HashMap<>();
        for (String str : stringCommandMap.keySet()) {
            commandStringMap.put(stringCommandMap.get(str), str);
        }
    }
}

enum Command {SIGN_IN, SIGN_UP, GET_MONEY, PUT_MONEY, CHECK_ACCOUNT, EXIT}