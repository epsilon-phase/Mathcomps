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
	public void ReRandomize()
	{
		seed=new Random().nextInt();
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

	@Override
	public int getMaxStep() {
		// TODO Auto-generated method stub
		return Integer.MAX_VALUE;
	}

}
