package bump.org.util;

public class ArrayInsurance {
	public static boolean goodarray(Object[][] e, int mindim) {
		for (int i = 0; i < e.length; i++)
			// If the length is too small, return false
			if (e[i].length < mindim)
				return false;
		return true;
	}
}
