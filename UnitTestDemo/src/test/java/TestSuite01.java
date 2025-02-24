
import daihocmo.unittestdemo.DemoTest;
import daihocmo.unittestdemo.Radix;
import java.time.Duration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author admin
 */
public class TestSuite01 {
    @Test
    public void testEvenNumber() {
        int n = 2;
        boolean expectedOutput = true;
        boolean actualOutput = DemoTest.isNt(n);
        
        Assertions.assertEquals(expectedOutput, actualOutput);
    }
    
    @Test
    public void testWrongEvenNumber() {
        int n = 4;
        Assertions.assertFalse(DemoTest.isNt(n));
    }
    
    @Test
    public void testOddNumber() {
        int n = 11;
        Assertions.assertTrue(DemoTest.isNt(n));
    }
    
    @Test
    public void testException() {
        Assertions.assertThrows(ArithmeticException.class, () -> {
            DemoTest.isNt(-7);
        });
    }
    
    @Test
    public void testTimeout() {
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            DemoTest.isNt(117);
        });
    }
    
    @Test
    public void testEx() {
        Assertions.assertThrows(ArithmeticException.class, () -> new Radix(-5));
    }
}
