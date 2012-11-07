package bump.org.comp;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Float;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import bump.org.util.ChartingUtil;

public class ChartingThing extends Canvas {
	public ChartingThing() {
		super();
		this.data = new ArrayList<Integer>();
	}

	public void setTicks(int x, int y) {
		xtick = x;
		ytick = y;
	}

	public void paint(Graphics g) {
		Graphics2D put = (Graphics2D) g.create();

		float vstep;
		float hstep = zoomfactor * (getWidth() / data.size());
		vstep = zoomfactor * (getHeight() / (ChartingUtil.getdiff(data)) / 2);
		// put the x-axis in the middle of the chart
		put.translate(0, getHeight() / 2);
		// Essentially configuring whether or not the antialiasing is on
		RenderingHints h;
		if (antialias) {
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
		put.setStroke(new BasicStroke(zoomfactor));
		for (int i = 1; i < data.size(); i++) {
			put.draw(new Line2D.Float(hstep * (i - 1), data.get(i - 1) * vstep,
					i * hstep, data.get(i) * vstep));
		}

	}

	private Color backgroundcolor = Color.white;
	/**
	 * The value that determines whether or not the lines should be antialiased.
	 */
	private boolean antialias = true;
	/**
	 * The amount to zoom in by
	 */
	private float zoomfactor = 1.0f;

	public void SetColor(Color g) {
		linecolor = g;
	}

	public Color getColor() {
		return linecolor;
	}

	public Color linecolor;

	public void addData(int[] data) {
		this.data.ensureCapacity(this.data.size() + data.length);
		for (int i : data)
			this.data.add(i);
	}

	public void setData(int[] data) {
		// remove the data
		this.data.clear();
		this.data.ensureCapacity(data.length);
		for (int i : data)
			this.data.add(i);
	}

	/**
	 * @return the antialias
	 */
	public boolean isAntialias() {
		return antialias;
	}

	/**
	 * @param antialias
	 *            the antialias to set
	 */
	public void setAntialias(boolean antialias) {
		this.antialias = antialias;
	}

	/**
	 * @return the zoomfactor
	 */
	public float getZoomfactor() {
		return zoomfactor;
	}

	/**
	 * @param zoomfactor
	 *            the zoomfactor to set
	 */
	public void setZoomfactor(float zoomfactor) {
		this.zoomfactor = zoomfactor;
	}

	int ytick = 5, xtick = 5;
	ArrayList<Integer> data;
}
