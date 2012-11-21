package bump.org.comp;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

import bump.org.util.ChartingUtil;

public class AnimatedMultiLineChart extends MultiLineChart {
	public void paint(Graphics g) {
		Graphics2D put = (Graphics2D) g.create();

		double vstep;
		double hstep = (getWidth() / data.size());
		vstep = (getHeight() / (ChartingUtil.getdiff(data, (Object) null)));

		// Essentially configuring whether or not the antialiasing is on
		RenderingHints h;
		if (isAntialias()) {
			h = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);

		} else {
			h = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_OFF);
		}
		// If set for bicubic rendering, add it.
		if (isBicubic()) {
			h.add(new RenderingHints(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BICUBIC));
		}
		put.addRenderingHints(h);
		put.setColor(getBackgroundcolor());
		// fill in the background.
		put.fill(new Rectangle2D.Float(0, 0, getWidth(), getHeight()));
		put.setColor(ChartingUtil.createInverse(getBackgroundcolor()));
		for (int i = 0; i < data.size(); i++) {
			put.draw(new Line2D.Double(hstep * (i - 1), 0, hstep * (i), 0));
			if (i % xtick == 0)
				put.draw(new Line2D.Double(hstep * (i - 1), -vstep,
						hstep * (i), vstep));
		}
		// draw the vertical ticks.
		put.draw(new Line2D.Double(getWidth() / 2, 0, getWidth() / 2,
				getHeight()));
		for (int i = 0; i * vstep < getHeight() / (2 * ChartingUtil.Max(data)); i++) {
			put.draw(new Line2D.Double(0, vstep * i, hstep, vstep * i));
		}
		// allow the data line to overwrite the current colors.
		put.setPaintMode();
		put.setColor(linecolor);

		for (int c = 0; c < data.size(); c++) {
			// if the color function can't provide for the number of lines, wrap
			// around so that it does not throw an exception.
			put.setColor(color.getColorAtStep(c % color.getMaxStep()));
			// Initialize the path.
			Path2D.Double e = new Path2D.Double();
			// Move the start point of the path to the zero index of the list in
			// question right now.
			e.moveTo(0, vstep * data.get(c).get(0));
			for (int i = 2; i < data.size() && i <= frame; i++) {
				if (!isCurvelines())
					e.lineTo(hstep * i, data.get(c).get(i) * vstep);
				else {
					// quadratic interpolation
					// Just interpolate with the former point.
					e.quadTo(hstep * (i - 1) + (i % 3 == 0 ? 4 : 1), vstep
							* data.get(c).get(i - 1) + 2, i * hstep, data
							.get(c).get(i) * vstep);
				}
			}
			put.draw(e);

		}
		frame += 1;
		if (frame >= ChartingUtil.maxLength(data)) {
			frame = 1;
		}

	}

	private float updateRate = 0.1F;

	public void setUpdateRate(int updateRate) {
		this.updateRate = 1000F / (float) updateRate;
	}

	public float getUpdateRate() {
		return updateRate;
	}

	private int frame = 1;
	Thread painted;

	public void finalize() throws Throwable {
		this.painted.interrupt();
		super.finalize();
	}

	public AnimatedMultiLineChart() {
		painted = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					if (AnimatedMultiLineChart.this.isVisible())
						AnimatedMultiLineChart.this.repaint();
					try {
						Thread.sleep((int) (1000 * AnimatedMultiLineChart.this
								.getUpdateRate()));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return;
					}
				}
			}

		});
		painted.run();

	}
}
