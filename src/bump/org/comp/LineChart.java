package bump.org.comp;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Path2D.Double;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Think ChartingThing without the zoom factor or the automatic scaling.
 * 
 * @author Jaked122
 * 
 */
public class LineChart extends Canvas {
	public void paint(Graphics g) {
		scalex = getWidth() / 10;
		scaley = getHeight() / 10;
		if (datas.size() > 0 && datas != null) {
			Graphics2D h = (Graphics2D) g.create();
			h.setColor(Color.black);
			h.draw(new Line2D.Double(getWidth() / 2, 0, getWidth() / 2,
					getHeight()));
			h.draw(new Line2D.Double(0, getHeight() / 2, getWidth(),
					getHeight() / 2));
			h.drawString(java.lang.Double.toString(originpoint[0]) + ":"
					+ java.lang.Double.toString(originpoint[1]),
					getWidth() / 2, getHeight() / 2);
			// move to the center of where you want rendered.
			h.translate(originpoint[0], originpoint[1]);

			h.scale(scalex, scaley);
			Path2D.Double e = new Path2D.Double();
			e.moveTo(datas.get(0).getX(), datas.get(0).getY());
			for (Point2D.Double d : datas) {
				e.lineTo(d.getX(), d.getY());
			}
			h.setColor(linecolor);
			h.draw(e);
		}
	}

	private double tickx, ticky;

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

	public void shift(double x, double y) {
		shiftx(x);
		shifty(y);
		repaint();
	}

	private Color linecolor = Color.green;
	private Color backgroundColor;
	private double scalingfactor;

	private double[] originpoint = new double[] { 0, 0 };

	public LineChart() {
		datas = new ArrayList<Point2D.Double>();
		scaley = 10;
		scalex = 10;
	}

	public Color getLinecolor() {
		return linecolor;
	}

	public void setLinecolor(Color linecolor) {
		this.linecolor = linecolor;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public double getTicky() {
		return ticky;
	}

	public void setTicky(double ticky) {
		this.ticky = ticky;
	}

	public double getTickx() {
		return tickx;
	}

	public void setTickx(double tickx) {
		this.tickx = tickx;
	}

	/**
	 * @return The data being shown in the component
	 */
	public ArrayList<Point2D.Double> getDatas() {
		return datas;
	}

	/**
	 * @param datas
	 *            The data to show
	 */
	public void setDatas(ArrayList<Point2D.Double> datas) {
		this.datas = datas;
	}

	public void addDatas(double[][] a) {

		for (double[] c : a)
			this.datas.add(new Point2D.Double(c[0], c[1]));
	}

	/**
	 * @return the originpoint
	 */
	public double[] getOriginpoint() {
		return originpoint;
	}

	/**
	 * @param originpoint
	 *            the originpoint to set
	 */
	public void setOriginpoint(double[] originpoint) {
		this.originpoint = originpoint;
	}

	private ArrayList<Point2D.Double> datas;
	private int scalex;
	private int scaley;
}
