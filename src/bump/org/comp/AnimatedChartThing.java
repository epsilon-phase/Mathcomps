package bump.org.comp;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Path2D.Double;

import bump.org.util.ChartingUtil;

/**
 * Does the same old automatic constrained plotting thing as before, this time,
 * however, it shows the graph animating properly.
 * 
 * @author Jaked122
 * 
 */
public class AnimatedChartThing extends ChartingThing {
	Thread updator;
	private int updaterate = 1;

	private float frame = 0.1F;

	/**
	 * 
	 * @param rate
	 *            the rate as specified by the desired number of frames per
	 *            second
	 */
	public void setframecount(int rate) {
		updaterate = 1000 / rate;
	}

	public AnimatedChartThing() {

		super();
		updator = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					if (AnimatedChartThing.this.isVisible())
						repaint();
					try {
						Thread.sleep((int) 1000
								* AnimatedChartThing.this.getUpdaterate());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		updator.start();
	}

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
			// set the color to the background color member
			put.setColor(getBackgroundcolor());
			// Fill the background of the graph
			put.fill(new Rectangle2D.Float(0, 0, getWidth(), getHeight()));
			put.setColor(ChartingUtil.createInverse(getBackgroundcolor()));
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
			for (int i = 1; i < data.size() && // only go up to the frame count.
					i <= frame; i++) {
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
		frame += 1;
		if (frame >= data.size())
			frame = 1;

	}

	/**
	 * @return the updaterate
	 */
	public int getUpdaterate() {
		return updaterate;
	}

	/**
	 * @param updaterate
	 *            the updaterate to set
	 */
	public void setUpdaterate(int updaterate) {
		this.updaterate = updaterate;
	}
}
