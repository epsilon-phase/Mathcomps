package bump.org.util;

import java.util.Collection;

public class ChartingUtil {

	public static int Max(Collection<Integer> a) {
		int i = 0;
		for (int z : a)
			i = Math.max(i, z);
		return i;

	}

	public static int min(Collection<Integer> a) {
		int i = Integer.MAX_VALUE;
		for (int z : a)
			i = Math.min(z, i);
		return i;
	}

	public static int getdiff(Collection<Integer> a) {
		return Max(a) - min(a);
	}
}
