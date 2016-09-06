package homeworks.HW6;

import java.util.Random;

/**
 * Created by Yaroslav on 23.08.16.
 */


public class Example {

    public static void main(String[] args) {
        Example example1 = new Example();
        Example example2 = new Example();
        BeanUtils.assign(example1, example2);
    }


    public Integer getInteger() {
        System.out.println("Get - Int was called");
        return new Random().nextInt(100);
    }

    public Number getNumber() {
        System.out.println("Get - Number was called");
        return new Random().nextDouble();
    }

    public Object getObject() {
        System.out.println("Get - Object was called");
        Object res = "my object is string";
        return res;
    }

    public void setInteger(Integer setValue) {
        printSetValue("setInt was set value ", setValue);
    }

    public void setNumber(Number setValue) {
        printSetValue("setNumber was set value ", setValue);
    }

    public void setObject(Object setValue) {
        printSetValue("setObject was set value ", setValue);
    }

    private void printSetValue(String str, Object setValuet) {
        System.out.println(str + "\"" + setValuet + "\"");
    }
}
