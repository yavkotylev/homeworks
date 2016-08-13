package homeworks.HW5;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.Scanner;

/**
 * Created by Yaroslav on 05.08.16.
 */
public class TerminalServerImpl implements TerminalServer {

    String filePath;
    Map<String, Account> userList;

    public TerminalServerImpl() {

        userList = new HashMap<>();
        filePath = "DB.txt";
        try {
            readBD(userList, filePath);
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't find file to read.\n" + e.getMessage());
        }

    }

    @Override
    public String signUp(String name, String pin) {

        readBD(userList, filePath);

        if (name.contains(" ") == true) {
            throw new RuntimeException("Name shouldn't contains spaces");
        }

        if (pin.contains(" ") == true) {
            throw new RuntimeException("Pin shouldn't contains spaces");
        }
        if (userList.containsKey(name) == true) {
            throw new RuntimeException("User name \"" + name + "\' is already used.");
        }
        String id = String.valueOf(Math.random()).substring(2);
        while (userList.containsKey(id) == true) {
            id = String.valueOf(Math.random()).substring(2);
        }
        Account newUser = new Account(id, name, pin);
        userList.put(id, newUser);
        writeToBD(newUser.getStringInformation());
        return id;

    }

    @Override
    public BigDecimal checkAccount(String id, String pin) {
        try {
            return userList.get(id).getAmountMoney(pin);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public String signIn(String name, String pin) {
        try {
            for (String i : userList.keySet()){
                if (userList.get(i).loginUser(name, pin) == true){
                    return i;
                }
            }
        } catch (RuntimeException e){
            throw  new RuntimeException(e.getMessage(), e);
        }

        throw new RuntimeException("Wrong user id");
    }

    @Override
    public void getMoney(String id, String pin, BigDecimal amount) {
        if (userList.containsKey(id) == true) {
            try {
                userList.get(id).getMoney(amount, pin);
                writeToBD(userList);
            } catch (RuntimeException e) {
                throw new RuntimeException(e.getMessage(), e);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        } else {
            throw new RuntimeException("Wrong user id!");
        }
    }

    @Override
    public void putMoney(String id, String pin, BigDecimal amount) {
        if (userList.containsKey(id) == true) {
            try {
                userList.get(id).putMoney(amount, pin);
                writeToBD(userList);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        } else {
            throw new RuntimeException("Wrong user id!");
        }
    }

    @Override
    public void logOut(String id){
        if (userList.containsKey(id) == true){
            userList.get(id).logOut();
        } else {
            throw new RuntimeException("Wrong user id");
        }

        try {
            writeToBD(userList);
        } catch (RuntimeException e){
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public boolean checkPin(String id, String pin){
        if (userList.containsKey(id) == true){
            return userList.get(id).checkPin(pin);
        }
        else {
            throw new RuntimeException("Wrong user id!");
        }
    }



    private void writeToBD(String text) {

        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(text + "\n");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("Can't find file \"" + filePath + "\' to write", e);

        }

    }

    private void writeToBD(Map<String, Account> users) {
        try (FileWriter writer = new FileWriter(filePath, false)) {
            for (String i : users.keySet()) {
                writer.write(users.get(i).getStringInformation() + "\n");
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("Can't find file \"" + filePath + "\' to write", e);

        }
    }

    private void readBD(Map<String, Account> users, String filePath) {

        File file = new File(filePath);
        if (file.exists() == false){
            try {
                file.createNewFile();
            } catch (Exception e){
                throw new RuntimeException("Exception creating file");
            }
        }
        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine()) {
                String date = scanner.nextLine();
                if (users.containsKey(date.split(" ")[0]) == false) {
                    String[] ar = date.split(" ");
                    Account user = new Account(ar[0], ar[1], ar[2], ar[3]);
                    users.put(ar[0], user);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Can't find file \"" + filePath + "\" to read!", e);
        } catch (Exception e) {
            throw new RuntimeException("Can't read from file \"" + filePath + "\"!", e);
        }

    }
}

class Account {
    private final String id;
    private final String name;
    private final String pin;
    private BigDecimal amountMoney;
    private boolean login;

    Account(String id, String name, String pin) {
        this.name = name;
        this.pin = pin;
        this.id = id;
        login = false;
        amountMoney = BigDecimal.ZERO;
    }

    Account(String id, String name, String pin, String money) {
        this.name = name;
        this.pin = pin;
        this.id = id;
        login = false;
        try {
            amountMoney = BigDecimal.valueOf(Double.parseDouble(money));
        } catch (Exception e) {
            throw new RuntimeException("Can't read amount of money form user id = " + id + ".", e);
        }
    }

    void getMoney(BigDecimal money, String pin) throws Exception {
        checkLog();

        if (this.pin.equals(pin) == false) {
            throw new RuntimeException("Wrong pin!");
        }

        if (money.compareTo(BigDecimal.ZERO) <= 0) {
            // TODO: 07.08.16 change message
            throw new Exception("Нельзя добавить отрицатильное число или ноль");
        }

        if (amountMoney.compareTo(money) < 0) {
            //TODO: change message
            throw new Exception("Попытка снять больше денег, чем есть на счету");
        }

        if (money.doubleValue() % 100 != 0){
            throw new Exception("Sum should be divided by 100!");
        }

        amountMoney = amountMoney.subtract(money);


    }

    void putMoney(BigDecimal money, String pin) throws Exception {

        checkLog();

        if (this.pin.equals(pin) == false) {
            throw new Exception("Wrong pin!");
        }

        if (money.compareTo(BigDecimal.ZERO) <= 0) {
            //TODO: change message
            throw new Exception("Нельзя добавить отрицатильное число или ноль");
        }

        if (money.doubleValue() % 100 != 0){
            throw new Exception("Sum should be divided by 100!");
        }

        amountMoney = amountMoney.add(money);
    }

    String getStringInformation() {
        String res = "";
        res += id + " " + name + " " + pin + " " + amountMoney;
        return res;
    }

    BigDecimal getAmountMoney(String pin) throws RuntimeException {
        checkLog();
        if (this.pin.equals(pin)) {
            return amountMoney;
        } else {
            throw new RuntimeException("Wrong pin!");
        }
    }

    private void checkLog() throws RuntimeException {
        if (login == false) {
            throw new RuntimeException("User is not sign in");
        }
    }

    boolean loginUser(String name, String pin) {
        if (this.name.equals(name)) {

            if (this.pin.equals(pin)) {
                this.login = true;
                return true;
            } else {
                throw new RuntimeException("Wrong pin!");
            }
        } else {
            return false;
        }
    }

    void logOut() {
        login = false;
    }

    boolean checkPin(String pin) {
        return this.pin.equals(pin);
    }

}
