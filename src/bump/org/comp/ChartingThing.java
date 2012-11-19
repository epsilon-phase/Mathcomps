package bump.org.comp;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.FlatteningPathIterator;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
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

	/**
	 * The amount of scaleup to do in rendering the control.
	 */
	protected double zoomfactor = 1.0;

	public void paint(Graphics g) {
		if (data.size() > 0) {
			Graphics2D put = (Graphics2D) g.create();

			double vstep;
			double hstep = (getWidth() / data.size());
			put.scale(zoomfactor, zoomfactor);
			vstep = (getHeight() / (ChartingUtil.getdiff(data)));
			// make sure it isn't too small.
			if (vstep < .2) {
				vstep = .2;
			}

			// Add rendering hints to the graphics objects according to the
			// members of the class.
			RenderingHints h;

			if (antialias) {
				h = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);

			} else {
				h = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_OFF);
			}
			if (bicubic)
				h.add(new RenderingHints(RenderingHints.KEY_INTERPOLATION,
						RenderingHints.VALUE_INTERPOLATION_BICUBIC));
			else
				h.add(new RenderingHints(RenderingHints.KEY_INTERPOLATION,
						RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR));
			put.addRenderingHints(h);
			put.setPaintMode();
			// set the color to the background color member
			put.setColor(backgroundcolor);
			// Fill the background of the graph
			put.fill(new Rectangle2D.Float(0, 0, getWidth(), getHeight()));
			put.setColor(ChartingUtil.createInverse(backgroundcolor));
			// Draw the horizontal Axis.
			put.draw(new Line2D.Double(0, getHeight() / 2, getWidth(),
					getHeight() / 2));
			for (int i = 0; i * vstep < getWidth(); i++)
				if (i % xtick == 0)
					put.draw(new Line2D.Double(i * hstep, 0 + getHeight() / 2,
							i * hstep, 5 + getHeight() / 2));
			// draw the vertical ticks.
			put.draw(new Line2D.Double(getWidth() / 2, -getHeight(),
					getWidth() / 2, getHeight()));
			for (int i = 0; (i * vstep) < (getHeight()); i++) {
				// if the integer is a multiple of i, draw the tick.
				if (i % ytick == 0) {
					put.draw(new Line2D.Double(getWidth() / 2, i * vstep,
							getWidth() / 2 + 5, i * vstep));
				}
			}
			// put the x-axis in the middle of the chart
			// put.translate(0, getHeight() / 2);
			for (int i = 0; i < data.size(); i++) {

				if (i % xtick == 0)// Draw the ticks on the x-axis
					put.draw(new Line2D.Double(hstep * (i - 1), 0, hstep * (i),
							5));
			}
			// allow the data line to overwrite the current colors.
			put.setPaintMode();
			put.setColor(linecolor);
			Path2D.Double e = new Path2D.Double();
			if (!fillArea)// If the component is not set to fill beneath the
							// line, then just move the first point to the
							// appropriate place as specified by the data
							// available
				e.moveTo(0, vstep * data.get(0));
			else {
				// move to origin
				e.moveTo(0, 0);
				// line to the first datapoint.
				e.lineTo(0, vstep * data.get(0));
			}
			for (int i = 1; i < data.size(); i++) {
				if (!isCurvelines())
					e.lineTo(hstep * i, data.get(i) * vstep);
				else {
					// quadratic interpolation
					e.quadTo(hstep * (i - 1) + (i % 3 == 0 ? 4 : 1), vstep
							* data.get(i - 1) + 2, i * hstep, data.get(i)
							* vstep);
				}
				if (fillArea && i == data.size() - 1)
					// put a vertical line going to the initial level of the
					// line.
					e.lineTo(hstep * i, 0);

			}
			// If the flag is set, allow fill in the path, rather than letting
			// it go.
			if (fillArea) {

				put.fill(e);
				e.closePath();
			}
			put.draw(e);

		}

	}

	/**
	 * Controls whether or not Bicubic interpolation is used in rendering the
	 * component.
	 */
	protected boolean bicubic = false;
	/**
	 * Controls whether or not the path is to be filled once it is rendered.
	 */
	protected boolean fillArea = false;
	/**
	 * Whether or not to curve the lines a bit more(Doesn't do much right now).
	 */
	private boolean curvelines = true;
	private Color backgroundcolor = Color.black;
	/**
	 * The value that determines whether or not the lines should be antialiased.
	 */
	protected boolean antialias = true;

	public void SetColor(Color g) {
		linecolor = g;
	}

	public Color getColor() {
		return linecolor;
	}

	public Color linecolor = Color.blue;

	public void addData(int[] data) {
		this.data.ensureCapacity(this.data.size() + data.length);
		for (int i : data)
			// Flip the points to fit with Java's odd coordinate scheme
			this.data.add(i);
		repaint();
	}

	public void addDataPoint(int d) {
		// Flip the points to fit with Java's odd coordinate scheme.
		this.data.add(d);
		repaint();
	}

	public void setData(int[] data) {
		// remove the data
		this.data.clear();
		this.data.ensureCapacity(data.length);
		for (int i : data)
			this.data.add(i);
		repaint();
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
	 * @return the curvelines
	 * @see bump.org.comp.ChartingThing.curvelines
	 */
	public boolean isCurvelines() {
		return curvelines;
	}

	/**
	 * @param curvelines
	 *            whether or not the lines should be curved.
	 * @see bump.org.comp.ChartingThing.curvelines
	 */
	public void setCurvelines(boolean curvelines) {
		this.curvelines = curvelines;
	}

	/**
	 * @return Whether the component is set to fill in underneath the chart.
	 */
	public boolean isAreaFilled() {
		return fillArea;
	}

	/**
	 * @param fillArea
	 *            Set whether or not the component fills in the area underneath
	 *            the chart.
	 */
	public void setFillArea(boolean fillArea) {
		this.fillArea = fillArea;
	}

	/**
	 * @return a boolean indicating whether or not the control is set to use
	 *         Bicubic interpolation.
	 */
	public boolean isBicubic() {
		return bicubic;
	}

	/**
	 * @param boolean considering whether or not the bicubic rendering is being
	 *        used.
	 */
	public void setBicubic(boolean bicubic) {
		this.bicubic = bicubic;
	}

	/**
	 * @return the zoomfactor
	 */
	public double getZoomfactor() {
		return zoomfactor;
	}

	/**
	 * @param zoomfactor
	 *            the zoomfactor to set
	 */
	public void setZoomfactor(double zoomfactor) {
		this.zoomfactor = zoomfactor;
	}

	/**
	 * @return the backgroundcolor
	 */
	public Color getBackgroundcolor() {
		return backgroundcolor;
	}

	/**
	 * @param backgroundcolor
	 *            the backgroundcolor to set
	 */
	public void setBackgroundcolor(Color backgroundcolor) {
		this.backgroundcolor = backgroundcolor;
	}

	int ytick = 5, xtick = 5;
	protected ArrayList<Integer> data;
}
