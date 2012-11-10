package bump.org.comp;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;

import bump.org.comp.color.IColorFunction;
import bump.org.util.ChartingUtil;

public class MultiLineChart extends ChartingThing {
	public void AddSeries(ArrayList<Integer> g) {
		this.data.add(g);
		repaint();
	}
	
	public void setSeriesAtIndex(ArrayList<Integer> g, int c) {
		this.data.set(c, g);
	}

	public void paint(Graphics g) {
		Graphics2D put = (Graphics2D) g.create();

		float vstep;
		float hstep = getZoomfactor() * (getWidth() / data.size());
		vstep = getZoomfactor()
				* (getHeight() / (ChartingUtil.getdiff(data, (Object) null)) / 2);
		// put the x-axis in the middle of the chart
		put.translate(0, getHeight() / 2);
		// Essentially configuring whether or not the antialiasing is on
		RenderingHints h;
		if (isAntialias()) {
			h = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);

		} else {
			h = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_OFF);
		}
		put.addRenderingHints(h);
		put.setColor(Color.black);

		for (int i = 0; i < data.size(); i++) {
			put.draw(new Line2D.Float(hstep * (i - 1), 0, hstep * (i), 0));
			if (i % xtick == 0)
				put.draw(new Line2D.Float(hstep * (i - 1), -vstep, hstep * (i),
						vstep));
		}// draw the vertical ticks.
		put.draw(new Line2D.Float(0, -getHeight() / 2, 0, getHeight() / 2));
		for (int i = 0; i * vstep < getHeight() / (2 * ChartingUtil.Max(data)); i++) {
			// start in the middle and radiate outwards.
			put.draw(new Line2D.Float(0, vstep * i, hstep, vstep * i));
			put.draw(new Line2D.Float(0, -vstep * i, hstep, -vstep * i));
		}
		// preserve the ticks.
		put.setXORMode(Color.gray);
		put.draw(new Rectangle2D.Float(-getWidth() / 2, -getHeight() / 2,
				getWidth() / 2, getHeight() / 2));
		// allow the data line to overwrite the current colors.
		put.setPaintMode();
		put.setColor(linecolor);
		put.setStroke(new BasicStroke(getZoomfactor()));
		for (int c = 0; c < data.size(); c++) {
			// if the color function can't provide for the number of lines.
			put.setColor(color.getColorAtStep(c % color.getMaxStep()));
			for (int i = 1; i < data.size(); i++) {
				put.draw(new Line2D.Float(hstep * (i - 1), data.get(c).get(
						i - 1)
						* vstep, i * hstep, data.get(c).get(i) * vstep));
			}
		}

	}

	/**
	 * @return the color
	 */
	public IColorFunction getColorFunction() {
		return color;
	}

	/**
	 * @param color
	 *            the color to set
	 */
	public void setColorFunction(IColorFunction color) {
		this.color = color;
	}

	private IColorFunction color;
	ArrayList<ArrayList<Integer>> data;
}
