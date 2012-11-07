package bump.org.comp.color;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;

public class ColorShower extends Canvas {
	public ColorShower() {
		super();

	}

	private Color display;

	public void paint(Graphics g) {
		// if the display color has not yet been initialized, then set the
		// painting color to gray.
		if (display != null)
			g.setColor(display);
		else
			// set the current color to a neutral gray
			g.setColor(Color.gray);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	// the minimum size is now 5x5.
	public Dimension getMinimumSize() {
		return new Dimension(5, 5);
	}

	/**
	 * @return the display
	 */
	public Color getDisplayColor() {
		return display;
	}

	/**
	 * @param display
	 *            the display Color to set
	 */
	public void setDisplayColor(Color display) {
		this.display = display;
	}
}
