///////////////////////////////////
// This is the main shift register class.
// Notice that it implements the ILFShiftRegister interface.
// You will need to fill in the functionality.
///////////////////////////////////


/**
 * class ShiftRegister
 * @author
 * Description: implements the ILFShiftRegister interface.
 */
public class ShiftRegister implements ILFShiftRegister {
    ///////////////////////////////////
    // Create your class variables here
    int[] shift_register;
    int tap;
    int reg_size;
    ///////////////////////////////////
    // TODO:
    ///////////////////////////////////
    // Create your constructor here:
    ///////////////////////////////////
    ShiftRegister(int size, int tap) {
        // TODO:
        if (tap<0 || tap>size-1) {
            System.out.println("Tap error");
            return;
        } else {
            this.tap = tap;
            reg_size = size;
            shift_register = new int[size];
        }
    }


    ///////////////////////////////////
    // Create your class methods here:
    ///////////////////////////////////
    /**
     * setSeed
     * @param seed
     * Description: since the seed is entered in the reverse order we first check if the seed
     * only contains 0s and 1s. If yes then the register gets reversed and stored in param register.
     * If no error is printed
     */
    @Override
    public void setSeed(int[] seed) {
        // TODO:
        for (int i = 0; i < reg_size; i++) {
            if (seed[i] < 0 || seed[i] > 1) {
                System.out.println("Error");
                return;
            }
        }
        int[] temp = new int[reg_size];

        for (int i = 0; i < reg_size; i++) {
            temp[i] = seed[reg_size - 1 - i];
        }
        shift_register = temp;
    }

    /**
     * shift
     * @return The modified least significant bit
     * Description: Does a left shift on the seed and sets the least significant bit as the XOR
     * value of the most significant bit and the tap value
     */
    @Override
    public int shift() {
        // TODO:
        int new_low = shift_register[0] ^ shift_register[reg_size - tap - 1];
        for (int i = 0; i < reg_size - 1; i++) {
            shift_register[i] = shift_register[i + 1];
        }
        shift_register[reg_size - 1] = new_low;
        return new_low;
    }

    /**
     * generate
     * @param k
     * @return the numerical value from the binary bits returned from shit()
     * Description: It takes in parameter k which is the number of times the shift function is to
     * be run and stored the returned bit value from shit and stores it in binary and converts it into
     * numerical value
     */
    @Override
    public int generate(int k) {
        // TODO:
        int sum = 0;
        while (k > 0) {
            int numb = shift();
            sum = (int) (sum + numb * (Math.pow(2, k - 1)));
            k = k - 1;
        }
        return sum;
    }

    /**
     * Returns the integer representation for a binary int array.
     * @param array
     * @return
     */
    private int toBinary(int[] array) {
        // TODO:
        return 0;
    }

}
