package bump.org.comp.color;

import java.awt.Color;
import java.util.Random;

/**
 * Generates Random colors in a manner persistent enough for actual usage.
 * 
 * @author Jaked122
 * 
 */
public class RandomColorGen implements IColorFunction {

	public RandomColorGen() {
		seed = new Random().nextInt();
	}

	/**
	 * Set the seed to a new random number.
	 */
	public void ReRandomize() {
		seed = new Random().nextInt();
	}

	int seed;

	@Override
	public Color getColorAtStep(int i) {
		// make a new random generator with the given seed
		Random a = new Random(seed);
		for (int z = 0; z < i; z++)
			a.nextInt();
		return new Color(a.nextInt(256), a.nextInt(256), a.nextInt(256));
	}

	/**
	 * Convert to ColorList in order to allow representation in the ColorPicker
	 * interface along with editing.
	 * 
	 * @param start
	 *            The index to start getting the color from
	 * @param end
	 *            The index to stop at
	 * @return A ColorList that replicates the function of this between start
	 *         and end.
	 */
	public ColorList getColorList(int start, int end) {
		ColorList a = new ColorList();
		for (int i = start; i < end; i++)
			a.addColor(getColorAtStep(i));
		return a;

	}

	@Override
	public int getMaxStep() {
		return Integer.MAX_VALUE;
	}

}
