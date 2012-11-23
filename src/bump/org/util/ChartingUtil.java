package bump.org.util;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;

public class ChartingUtil {

	public static int Max(Collection<Integer> a) {
		int i = 0;
		for (int z : a)
			i = Math.max(i, z);
		return i;

	}

	public static double slope(int point1, int point2, int diff) {
		return (point2 - point1) / diff;
	}

	public static double pointslope(double x, double slope, int point1, int x1) {
		return point1 + slope * x + slope * x1;
	}

	/**
	 * Find the maximum length of any given element in the array.
	 * 
	 * @param a
	 *            the array of arrays to find the length of.
	 * @return the largest length of any array in the list.
	 */
	public static int maxLength(ArrayList<ArrayList<Integer>> a) {
		int i = 0;
		for (ArrayList<Integer> e : a)
			i = Math.max(i, e.size());
		return i;
	}

	/**
	 * Find the smallest integer in a collection.
	 * 
	 * @param a
	 *            the collection of integers
	 * @return the smallest integer in a.
	 */
	public static int min(Collection<Integer> a) {
		int i = Integer.MAX_VALUE;
		for (int z : a)
			i = Math.min(z, i);
		return i;
	}

	/**
	 * Inverts a color so as to give maximum contrast.
	 * 
	 * @param i
	 *            The color to create an inverse of
	 * @return The inverted color
	 */
	public static Color createInverse(Color i) {
		Color g = new Color(255 - i.getRed(), 255 - i.getGreen(),
				255 - i.getBlue());
		return g;
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
