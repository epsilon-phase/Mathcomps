package bump.org.util;

public class ArrayInsurance {
	/**
	 * Does absolutely nothing whatesoever.
	 */
	private ArrayInsurance() {
	}

	/**
	 * Ensure that all the entries in the multidimensional array are filled
	 * properly(I.E. have the minimum elements in each subarray).
	 * 
	 * @param e
	 *            the array to test
	 * @param mindim
	 *            the minimum required dimension of the array
	 * @return whether or not the array fits the specifications.
	 */
	public static boolean goodarray(Object[][] e, int mindim) {
		for (int i = 0; i < e.length; i++)
			// If the length is too small, return false
			if (e[i].length < mindim)
				return false;
		return true;
	}
}
