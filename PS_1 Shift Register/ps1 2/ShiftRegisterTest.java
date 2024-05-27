import static org.junit.Assert.*;

import org.junit.Test;

/**
 * ShiftRegisterTest
 * @author dcsslg
 * Description: set of tests for a shift register implementation
 */
public class ShiftRegisterTest {
    /**
     * Returns a shift register to test.
     * @param size
     * @param tap
     * @return a new shift register
     */
    ILFShiftRegister getRegister(int size, int tap) {
        return new ShiftRegister(size, tap);
    }

    /**
     * Tests shift with simple example.
     */
    @Test
    public void testShift1() {
        ILFShiftRegister r = getRegister(9, 7);
        int[] seed = { 0, 1, 0, 1, 1, 1, 1, 0, 1 };
        r.setSeed(seed);
        int[] expected = { 1, 1, 0, 0, 0, 1, 1, 1, 1, 0 };
        for (int i = 0; i < 10; i++) {
            assertEquals(expected[i], r.shift());
        }
    }

    /**
     * Tests generate with simple example.
     */
    @Test
    public void testGenerate1() {
        ILFShiftRegister r = getRegister(9, 7);
        int[] seed = { 0, 1, 0, 1, 1, 1, 1, 0, 1 };
        r.setSeed(seed);
        int[] expected = { 6, 1, 7, 2, 2, 1, 6, 6, 2, 3 };
        for (int i = 0; i < 10; i++) {
            assertEquals("GenerateTest", expected[i], r.generate(3));
        }
    }

    /**
     * Tests register of length 1.
     */
    @Test
    public void testOneLength() {
        ILFShiftRegister r = getRegister(1, 0);
        int[] seed = { 1 };
        r.setSeed(seed);
        int[] expected = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, };
        for (int i = 0; i < 10; i++) {
            assertEquals(expected[i], r.generate(3));
        }
    }

    /**
     * Tests with erroneous seed.
     * if the seed is larger than the specified size the code should stop and terminate
     * one possible way to handle it is the seed gets modified to only contain the first
     * four bits instead of the entire size.
     */
    @Test
    public void testError() {
        ILFShiftRegister r = getRegister(4, 1);
        int[] seed = { 1, 0, 0, 0, 1, 1, 0 };
        r.setSeed(seed);
        r.shift();
        r.generate(4);
    }

    /**
     * if there is a bit that is not 0 or 1 the code terminates and register is set to null
     * all methods return the integer 0 to confirm
     */
    @Test
    public void error_not0or1() {
        ILFShiftRegister r = getRegister(9, 7);
        int[] seed = { 0, 1, 0, 1, 4, 1, 1, 0, 1 };
        r.setSeed(seed);
        int expected = 0;
        for (int i = 0; i < 10; i++) {
            int val = r.shift();
            assertEquals(expected, val);
        }
    }

    /**
     * if there is only one bit in the seed so the output will always be 0
     */
    @Test
    public void only_1_bit() {
        ILFShiftRegister r = getRegister(1, 0);
        int[] seed = {1};
        r.setSeed(seed);
        int[] expected = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        for (int i = 0; i < 10; i++) {
            int val = r.shift();
            assertEquals(expected[i], val);
        }
    }

    /**
     * Tests shift with simple example.
     */
    @Test
    public void test_shift() {
        ILFShiftRegister r = getRegister(7, 4);
        int[] seed = {1, 0, 1, 0, 1, 0, 1};
        r.setSeed(seed);
        int[] expected = {0, 0, 0, 0, 0, 0, 1, 0, 0, 0};
        for (int i = 0; i < 10; i++) {
            int val = r.shift();
            assertEquals(expected[i], val);
        }
    }

    /**
     * Tests generate with simple example.
     */
    @Test
    public void test_generate() {
        ILFShiftRegister r = getRegister(9, 7);
        int[] seed = { 0, 1, 0, 1, 1, 1, 1, 0, 1 };
        r.setSeed(seed);
        int[] expected = {24, 30, 18, 7, 12, 19, 11, 11, 27, 24};
        for (int i = 0; i < 10; i++) {
            int var = r.generate(5);
            assertEquals("GenerateTest", expected[i], var);
        }
    }
}
