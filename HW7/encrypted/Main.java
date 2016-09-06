package homeworks.HW7.encrypted;

import java.io.File;
import java.util.Scanner;

/**
 * Created by Yaroslav on 20.08.16.
 */
public class Main {

    public static void main(String[] args) {
        while (true) {
            Command userCommand = getCommand();
            if (userCommand.equals(Command.Exit)) {
                System.out.println("Exit");
                break;
            }
            EncryptedClassLoader encryptedClassLoader = getInf();
            String className = getClassName(encryptedClassLoader);

            switch (userCommand) {

                case Read:
                    try {
                        readClass(encryptedClassLoader, className);
                    } catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case Write:
                    encryptedClassLoader.encrypt(className);
                    break;
            }
        }
    }


    private static void readClass(EncryptedClassLoader encryptedClassLoader, String className) {
        try {
            Class<?> clazz = encryptedClassLoader.findClass(className);
            try {
                clazz.getMethod("doUsefull").invoke(clazz.newInstance());
            } catch (Exception e) {

            }
        } catch (ClassNotFoundException e) {
            System.out.println("Can't find class");
        }
    }


    private static Command getCommand() {
        String commandStr;

        Scanner scanner = new Scanner(System.in);

        boolean rightCommand = false;
        Command userCommand = null;
        do {
            System.out.println("Choose command:\n1) \"Read\" - to read encrypted class\n2) " +
                    "\"Write\" - to encrypt class\n3) \"Exit\"");
            commandStr = scanner.next();

            for (Command command : Command.values()) {
                if (command.name().equals(commandStr)) {
                    rightCommand = true;
                    userCommand = command;
                    break;
                }
            }
        } while (rightCommand == false);


        return userCommand;
    }

    private static EncryptedClassLoader getInf() {
        String filePath, key;
        File file;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("\nWrite file path:");
            filePath = scanner.next();

            System.out.println("Write encrypting key:");
            key = scanner.next();
            file = new File(filePath);

            if (file.isDirectory() == false || file.exists() == false) {
                System.out.println("Wrong filepath!");
            }
        } while ((file.exists() && file.isDirectory()) == false);
        EncryptedClassLoader encryptedClassLoader = new EncryptedClassLoader(key, file,
                ClassLoader.getSystemClassLoader());

        return encryptedClassLoader;
    }

    private static String getClassName(EncryptedClassLoader encryptedClassLoader) {

        String className;
        Scanner scanner = new Scanner(System.in);
        File file;
        do {

            System.out.println("\nWrite class name:");
            className = scanner.next();

            if (encryptedClassLoader.getDir().getPath() != "") {
                file = new File(encryptedClassLoader.getDir().getPath() + "/" + className + ".class");
            } else {
                file = new File(encryptedClassLoader.getDir().getPath() + className + ".class");
            }

            if (file.isFile() == false) {
                System.out.println("Wrong file name");
            }
            if (file.exists() == false) {
                System.out.println("File doesn't exist");
            }
        } while ((file.exists() && file.isFile()) == false);

        return className;

    }

    private enum Command {
        Read, Write, Exit
    }
}

