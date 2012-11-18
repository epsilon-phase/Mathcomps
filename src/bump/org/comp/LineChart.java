package bump.org.comp;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Path2D;
import java.awt.geom.Path2D.Double;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import bump.org.util.ChartingUtil;

/**
 * Think ChartingThing without the zoom factor or the automatic scaling.
 * 
 * @author Jaked122
 * 
 */
public final class LineChart extends Canvas {
	private boolean antialias = true;

	/**
	 * The background Color to be drawn behind the graph.
	 */
	private Color backgroundColor = Color.white;
	/**
	 * A variable that controls whether or not the rendering process uses
	 * bicubic interpolation to compute colors.
	 */
	private boolean bicubic = false;
	/**
	 * The arraylist containing datapoints which are to be diplayed.
	 */
	private ArrayList<Point2D.Double> datas;
	/**
	 * The last size of the component(if it scales up, then the component will
	 * resize to fit the view it held before).
	 */
	private int[] lastsize;
	/**
	 * The Color of the line drawn by the control to represent the data.
	 */
	private Color linecolor = Color.green;
	/**
	 * The move
	 */
	private double[] originpoint = new double[] { 0, 0 };

	private double scalex = 0;

	private double scaley = 0;

	private double scalingfactor;
	/**
	 * If this is turned on, then the coordinates of the points on the line will
	 * be shown as well upon the line.
	 */
	private boolean showCoordinates = false;
	private double tickx, ticky;

	public LineChart() {
		datas = new ArrayList<Point2D.Double>();
		scaley = 10;
		scalex = 10;
		this.addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				zoom(e.getWheelRotation());
			}
		});
	}

	/**
	 * Add data to the set, displaying it when it is done
	 * 
	 * @param a
	 *            The two dimensional array with two elements to each
	 *            element.(the points)
	 */
	public void addDatas(double[][] a) {

		for (double[] c : a)
			this.datas.add(new Point2D.Double(c[0], c[1]));
		repaint();
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * @return The data being shown in the component
	 */
	public ArrayList<Point2D.Double> getDatas() {
		return datas;
	}

	public Color getLinecolor() {
		return linecolor;
	}

	/**
	 * @return the originpoint
	 */
	public double[] getOriginpoint() {
		return originpoint;
	}

	public double getTickx() {
		return tickx;
	}

	public double getTicky() {
		return ticky;
	}

	/**
	 * @return the bicubic
	 */
	public boolean isBicubic() {
		return bicubic;
	}

	/**
	 * @return Whether or not the coordinates are rendered as text on the line.
	 */
	public boolean isShowCoordinates() {
		return showCoordinates;
	}

	public void paint(Graphics g) {
		if (lastsize == null) {
			lastsize = new int[2];
			lastsize[0] = getWidth();
			lastsize[1] = getHeight();
		}
		if (scalex == 0) {
			// initialize the scalex to be a tenth of the width of
			// the entire component
			scalex = getWidth() / 10;
		}
		if (scaley == 0) {
			// Initialize the scaley to be a tenth of width of the
			// entire component
			scaley = getHeight() / 10;
		}
		if (datas.size() > 0 && datas != null) {
			Graphics2D h = (Graphics2D) g.create();
			RenderingHints j;
			if (antialias)
				j = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
			else
				j = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_OFF);
			if (bicubic)
				j.add(new RenderingHints(RenderingHints.KEY_INTERPOLATION,
						RenderingHints.VALUE_INTERPOLATION_BICUBIC));
			else
				j.add(new RenderingHints(RenderingHints.KEY_INTERPOLATION,
						RenderingHints.VALUE_INTERPOLATION_BILINEAR));
			h.addRenderingHints(j);
			// Fill in with the background color
			h.setColor(backgroundColor);
			h.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
			// move to the center of where you want rendered.
			h.translate(originpoint[0], originpoint[1]);
			// scale up appropriately
			h.scale(-scalex, -scaley);
			// rotate so higher coordinates are up, rather than down.
			// h.rotate(Math.PI);
			Path2D.Double e = new Path2D.Double();
			e.moveTo(datas.get(0).getX(), datas.get(0).getY());
			for (Point2D.Double d : datas) {
				e.lineTo(d.getX(), d.getY());
			}
			h.setColor(linecolor);
			h.draw(e);

			h.setColor(ChartingUtil.createInverse(backgroundColor));
			if (points) {

				for (Point2D.Double d : datas) {

					h.fill(new Ellipse2D.Double(d.getX(), d.getY(), 1, 1));
				}

			}
			if (showCoordinates)
				drawCoordinate(h);

			try {
				h.transform(h.getTransform().createInverse());
			} catch (NoninvertibleTransformException e1) {
				h.scale(-1 / scalex, -1 / scaley);
				h.translate(-originpoint[0], -originpoint[1]);
				e1.printStackTrace();
			}
			// Set the color to be the inverse of the background.
			h.draw(new Line2D.Double(getWidth() / 2, 0, getWidth() / 2,
					getHeight()));
			h.draw(new Line2D.Double(0, getHeight() / 2, getWidth(),
					getHeight() / 2));
			h.drawString(java.lang.Double.toString(originpoint[0]) + ":"
					+ java.lang.Double.toString(originpoint[1]),
					getWidth() / 2, getHeight() / 2);
		}
	}

	/**
	 * Controls whether or not the control renders the points as circles over
	 * them.
	 */
	private boolean points = true;

	private void drawCoordinate(Graphics2D h) {
		// TODO write coordinate translating code
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	/**
	 * @param bicubic
	 *            Whether or not the component should render with Bicubic color
	 *            interpolation
	 */
	public void setBicubic(boolean bicubic) {
		this.bicubic = bicubic;
	}

	/**
	 * @param datas
	 *            The data to show
	 */
	public void setDatas(ArrayList<Point2D.Double> datas) {
		this.datas = datas;
	}

	/**
	 * Clear the dataset, allowing for a new function to be properly rendered.
	 * 
	 * @param a
	 *            A two dimensional array of doubles with paired values
	 *            expressing points.
	 */
	public void setDatas(double[][] a) {
		datas.clear();
		for (double[] c : a)
			this.datas.add(new Point2D.Double(c[0], c[1]));
		repaint();

	}

	public void setLinecolor(Color linecolor) {
		this.linecolor = linecolor;
	}

	/**
	 * @param originpoint
	 *            the pair of coordinates(x,y) which the display is offset by
	 */
	public void setOriginpoint(double[] originpoint) {
		this.originpoint = originpoint;
	}

	/**
	 * @param showCoordinates
	 *            Set whether or not coordinates are rendered as text.
	 */
	public void setShowCoordinates(boolean showCoordinates) {
		this.showCoordinates = showCoordinates;
	}

	public void setTickx(double tickx) {
		this.tickx = tickx;
	}

	public void setTicky(double ticky) {
		this.ticky = ticky;
	}

	public void setzoom(double x, double y) {
		scalex = x;
		scaley = y;
		repaint();
	}

	public void shift(double x, double y) {

		shiftx(x);
		shifty(y);
		repaint();

	}

	/**
	 * shift to the left.
	 * 
	 * @param x
	 */
	private void shiftx(double x) {
		originpoint[0] += x;
	}

	/**
	 * Shift up.
	 * 
	 * @param y
	 */
	private void shifty(double y) {
		originpoint[1] += y;
	}

	public void zoom(double factor) {
		scalex += factor;
		scaley += factor;
		repaint();
	}

	/**
	 * @return whether or not the points are rendered above the line
	 */
	public boolean isPoints() {
		return points;
	}

	/**
	 * @param points
	 *            whether or not to include the points as drawn upon the
	 *            component.
	 */
	public void setPoints(boolean points) {
		this.points = points;
	}
}
