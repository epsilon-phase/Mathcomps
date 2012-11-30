package bump.org.comp;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Arc2D.Float;
import java.util.ArrayList;

import javax.swing.JToolTip;

import bump.org.comp.color.IColorFunction;

/**
 * Shows data in a pie-chart like display. Data is stored in float format, and
 * only accepts that.
 * 
 * @author Jaked122
 * 
 */
public class PetalChart extends Canvas {
	public PetalChart() {
		setData(new ArrayList<java.lang.Float>());
		hits = new ArrayList<Area>();
		this.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				occludeToolTips(e.getX(), e.getY());

			}

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

	}

	public void update(Graphics g) {
		super.update(g);
	}

	private boolean antialias = true;
	private boolean bicubic = true;

	public void paint(Graphics g) {
		Graphics2D a = (Graphics2D) g;
		float unit = 360 / sum;
		float usedcircle = 0F;
		int e = 0;
		RenderingHints q = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_DEFAULT);
		if (antialias)
			q.add(new RenderingHints(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON));
		else
			q.add(new RenderingHints(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_OFF));
		if (bicubic)
			q.add(new RenderingHints(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BICUBIC));
		else
			q.add(new RenderingHints(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR));
		a.addRenderingHints(q);
		for (java.lang.Float c : getData()) {
			e += 1;
			a.setColor(cower.getColorAtStep(e % cower.getMaxStep()));
			a.draw(new Arc2D.Float(0, 0, getWidth(), getHeight(), usedcircle,
					usedcircle + unit * c, Arc2D.CHORD));
			usedcircle += unit * c;
		}
	}

	private void fixToolTips() {
		hits.clear();
		float usedcircle = 0;
		float unit = 360F / (float) sum;
		for (java.lang.Float a : getData()) {
			hits.add(new Area(new Arc2D.Float(0, 0, getWidth(), getHeight(),
					usedcircle, usedcircle + unit * a, Arc2D.CHORD)));
		}
	}

	public String getToolTip(int x, int y) {
		for (int i = 0; i < hits.size(); i++) {
			if (hits.get(i).contains(x, y)) {
				return getData().get(i).toString();
			}
		}
		return "";
	}

	private void occludeToolTips(int x, int y) {
		for (int i = 0; i < hits.size(); i++) {
			if (hits.get(i).contains(x, y)) {
				a.setToolTipText(getData().get(i).toString());
				this.a.setLocation(new Point(x, y));
				a.setVisible(true);
				return;
			} else
				a.setVisible(false);
		}
	}

	private ArrayList<java.lang.Float> data;
	ArrayList<Area> hits;

	public void addData(float[] a) {
		for (float e : a)
			this.addData(e);
	}

	float sum = 0;
	private boolean isaddingbatch = false;

	public void setData(float[] a) {
		sum = 0;
		data.clear();
		isaddingbatch = true;
		for (int i = 0; i < a.length; i++) {
			data.add(a[i]);
			sum += a[i];
		}
		isaddingbatch = false;
		repaint();
	}

	JToolTip a = new JToolTip();

	public void addData(float a) {
		getData().add(a);
		sum += a;
		if (!isaddingbatch) {
			this.fixToolTips();
			this.repaint();
		}

	}

	/**
	 * @return the data
	 */
	public ArrayList<java.lang.Float> getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(ArrayList<java.lang.Float> data) {
		this.data = data;
	}

	/**
	 * @return the cower
	 */
	public IColorFunction getCower() {
		return cower;
	}

	/**
	 * @param cower
	 *            the cower to set
	 */
	public void setCower(IColorFunction cower) {
		this.cower = cower;
	}

	private IColorFunction cower;
}
