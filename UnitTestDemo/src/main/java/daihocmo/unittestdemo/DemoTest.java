/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daihocmo.unittestdemo;

import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class DemoTest {

    public static boolean isNt(int n) {

//        try {
//            Thread.sleep(Duration.ofSeconds(2));
//        } catch (InterruptedException ex) {
//            Logger.getLogger(DemoTest.class.getName()).log(Level.SEVERE, null, ex);
//        }

        if (n < 0) {
            throw new ArithmeticException();
        }

        if (n < 2) {
            return false;
        }
        for (int i = 2; i < Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }

    public static double Power(double x, int n) {
        if (n == 0) {
            return 1.0;
        } else if (n > 0) {
            return x * Power(x, n - 1);
        } else {
            return Power(x, n + 1) / x;
        }
    }
}
