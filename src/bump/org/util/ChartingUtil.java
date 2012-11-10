package bump.org.util;

import java.util.ArrayList;
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

	public static int min(Collection<Collection<Integer>> d, Object e) {
		int i = Integer.MAX_VALUE;
		for (Collection<Integer> g : d)
			i = Math.min(i, min(g));
		return i;
	}

	public static int getdiff(Collection<Integer> a) {
		return Max(a) - min(a);
	}

	public static int getdiff(Collection<Collection<Integer>> a, Object e) {
		int c = 0;
		for (Collection d : a)
			c = Math.max(c, Max(d) - min(d));
		return c;
	}

	public static int Max(ArrayList<ArrayList<Integer>> data) {
		int c = 0;
		for (int i = 0; i < data.size(); i++)
			c = Math.max(c, Max(data.get(i)));
		return c;
	}

	public static int getdiff(ArrayList<ArrayList<Integer>> data, Object e) {
		// TODO Auto-generated method stub
		int c = 0;
		for (Collection d : data)
			c = Math.max(c, Max(d) - min(d));
		return c;
	}
}
