package homeworks.HW1;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Random;
import java.util.Timer;

/**
 * Created by Yaroslav on 05.08.16.
 */
public class Solution {
    public static void main(String[] args) {
        long i = 0;
        int period = 0;
        while (true) {
            period ++;
            double a = Math.random();
            int len = String.valueOf(a).length();
            if (len < 10){
                System.out.println("len = " + len + "\ni = " + i + "\na = " + a);
                break;
            }
            if (period % 1000000 == 0){
                i = i + 1;
                period = 0;
                System.out.println("i = " + i);
            }

        }
    }
}
