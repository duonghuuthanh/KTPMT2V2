
import daihocmo.unittestdemo.DemoTest;
import daihocmo.unittestdemo.Radix;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author admin
 */
public class TestParams {
    @BeforeAll
    public static void testBefore() {
        System.out.println("BEFORE ALL");
    }
    
    @AfterAll
    public static void testAfter() {
        System.out.println("AFTER ALL");
    }
    
    @BeforeEach
    public void testBeforeEach() {
        System.out.println("BEFORE EACH ALL");
    }
    
    @AfterEach
    public void testAfterEach() {
        System.out.println("AFTER EACH ALL");
    }
    
    @ParameterizedTest
    @ValueSource(ints = {4, 6, 110, 250})
    public void testEven(int n) {
        Assertions.assertFalse(DemoTest.isNt(n));
    }
    
    @ParameterizedTest
    @CsvSource({"5,true", "7,true", "12,false", "4,false"})
    public void testEven(int n, boolean expected) {
        Assertions.assertEquals(expected, DemoTest.isNt(n));
    }
    
     @ParameterizedTest
    @CsvFileSource(resources = "data.csv", numLinesToSkip = 1)
    public void testEven2(int n, boolean expected) {
        Assertions.assertEquals(expected, DemoTest.isNt(n));
    }
    
    @ParameterizedTest
    @CsvFileSource(resources = "power.csv", numLinesToSkip = 1)
    public void testPower(double x, int n, double expoected) {
        double actual = DemoTest.Power(x, n);
        Assertions.assertEquals(expoected, actual);
    }
    
    @ParameterizedTest
    @CsvFileSource(resources = "radix.csv", numLinesToSkip = 1)
    public void testRadix(int n, int radix, String expected) {
        Radix r = new Radix(n);
        String actual = r.ConvertDecimalToAnother(radix);
        Assertions.assertEquals(expected, actual);
    }
}
