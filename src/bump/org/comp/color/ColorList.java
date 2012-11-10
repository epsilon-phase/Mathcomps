package bump.org.comp.color;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class ColorList implements IColorFunction {
	/**
	 * Initialize the Colorlist with an empty list of Colors.
	 */
	public ColorList() {
		datas = new ArrayList<Color>();
	}

	/**
	 * Initialize the ColorList with an extant list.
	 * 
	 * @param a
	 *            The ColorList to copy.
	 */
	public ColorList(ColorList a) {
		datas = a.getColors();
	}

	public void addColor(Color g) {
		datas.add(g);
	}

	public ArrayList<Color> getColors() {
		return datas;
	}

	public void setColorAtStep(int i, Color e) {
		datas.set(i, e);
	}

	public void addColors(List<Color> a) {
		for (Color i : a)
			datas.add(i);
	}

	@Override
	public Color getColorAtStep(int i) {
		return datas.get(i);
	}

	@Override
	public int getMaxStep() {
		// TODO Auto-generated method stub
		return datas.size();
	}

	ArrayList<Color> datas;

}
