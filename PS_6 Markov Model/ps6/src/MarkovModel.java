import java.util.HashMap;
import java.util.Random;
import java.util.Map;
/**
 * This is the main class for your Markov Model.
 *
 * Assume that the text will contain ASCII characters in the range [1,255].
 * ASCII character 0 (the NULL character) will be treated as a non-character.
 *
 * Any such NULL characters in the original text should be ignored.
 */
public class MarkovModel {

	// Use this to generate random numbers as needed
	private Random generator = new Random();
	private int order = 1;

	// This is a special symbol to indicate no character
	public static final char NOCHARACTER = (char) 0;

	public Map<String, int[]> hashMap;

	/**
	 * Constructor for MarkovModel class.
	 *
	 * @param order the number of characters to identify for the Markov Model sequence
	 * @param seed the seed used by the random number generator
	 */
	public MarkovModel(int order, long seed) {
		// Initialize your class here
		this.order = order;
		hashMap = new HashMap<>();
		// Initialize the random number generator
		generator.setSeed(seed);
	}

	/**
	 * Builds the Markov Model based on the specified text string.
	 */
	public void initializeText(String text) {
		if (text == null) return;
		for (int i  = 0; i < text.length() - order; i++) {
			int ASCII = text.charAt(i + order);
			if (!hashMap.containsKey(text.substring(i, i + order))) {
				hashMap.put(text.substring(i, i + order), new int[257]);
			}
			hashMap.get(text.substring(i, i+order))[ASCII]++;
			hashMap.get(text.substring(i, i+order))[256]++;
		}
	}

	/**
	 * Returns the number of times the specified kgram appeared in the text.
	 */
	public int getFrequency(String kgram) {
		if (kgram == null || kgram.length() != this.order) return 0;
		if (hashMap.containsKey(kgram)) {
			return hashMap.get(kgram)[256];
		}
		return 0;
	}

	/**
	 * Returns the number of times the character c appears immediately after the specified kgram.
	 */
	public int getFrequency(String kgram, char c) {
		if (kgram == null || kgram.length() != this.order) return 0;
		if (hashMap.containsKey(kgram)) {
			return hashMap.get(kgram)[(int) c];
		}
		return 0;
	}

	/**
	 * Generates the next character from the Markov Model.
	 * Return NOCHARACTER if the kgram is not in the table, or if there is no
	 * valid character following the kgram.
	 */
	public char nextCharacter(String kgram) {
		// See the problem set description for details
		// on how to make the random selection.
		if (kgram == null || kgram.length() != this.order) return NOCHARACTER;
		if (!hashMap.containsKey(kgram)) return NOCHARACTER;
		int[] arr = hashMap.get(kgram);
		int stopPoint = generator.nextInt(arr[256]);
		int sum = -1;
		for (int i = 0; i < 256; i++) {
			sum += arr[i];
			if (sum >= stopPoint) {
				return (char) i;
			}
		}
		return NOCHARACTER;
	}

}
