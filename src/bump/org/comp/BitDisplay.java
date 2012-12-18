package bump.org.comp;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Arc2D.Float;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Random;

public class BitDisplay extends Canvas {
	private BitSet data = new BitSet(10);

	public BitDisplay() {
		Random q = new Random();
		for (int i = 0; i < 10; i++)
			data.set(i, q.nextBoolean());
		this.addComponentListener(new ComponentListener() {

			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentResized(ComponentEvent e) {
				changed = true;
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub

			}
		});
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (BitDisplay.this.isEditable()) {
					int a = getClickintersects(e.getX(), e.getY());
					// invert the intersecting element.
					data.set(a, !data.get(a));
					repaint();
				}

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}
		});
		this.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseDragged(MouseEvent e) {
				if (paintmode) {
					// set to on
					if (e.getButton() == e.BUTTON1) {
						int i = getClickintersects(e.getX(), e.getY());
						if (i != -1) {
							setDataAtIndex(i, true, false);
						}
					}
					// set to off
					if (e.getButton() == e.BUTTON3) {
						int i = getClickintersects(e.getX(), e.getY());
						if (i != -1) {
							setDataAtIndex(i, false, false);
						}
					}
				}
			}
		});
		hitboxes = new ArrayList<Area>();
	}

	/**
	 * Find the index of the bit that the point intersects with.
	 * 
	 * @param x
	 *            the x coordinate
	 * @param y
	 *            the y coordinate
	 * @return the index of the hitbox which it intersects with, or -1 to
	 *         indicate that there is no place.
	 */
	public int getClickintersects(int x, int y) {
		final int index = -1;
		for (int i = 0; i < hitboxes.size(); i++) {
			if (hitboxes.get(i).contains(new Point2D.Float(x, y))) {
				return i;
			}
		}
		return index;
	}

	public BigDecimal getDecimal() {
		BigDecimal a = new BigDecimal("0", new MathContext(10));
		BigInteger b = new BigInteger("0");
		BigInteger c = new BigInteger("0");
		for (int i = 0; i < data.size(); i++) {

			if (data.get(i)) {
				c = b.setBit(i);
				a = a.add(new BigDecimal(c));
				c.clearBit(i);
			}

		}
		return a;
	}

	/**
	 * Return a BigInteger representation of the bitset.
	 * 
	 * @return the biginteger representation of the bitset.
	 */
	public BigInteger getInteger() {
		BigInteger a = new BigInteger("0", 10);
		for (int i = 0; i < data.size(); i++)
			if (data.get(i))
				a = a.setBit(i);
		return a;
	}

	int drag = 0;

	/**
	 * Randomize the bitset represented without resizin the bitset.
	 */
	public final void randomize() {
		randomize(data.size());
	}

	/**
	 * Randomize the bitset represented and set it to a certain size(or only
	 * randomize up to a certain point).
	 * 
	 * @param upto
	 *            the new size or the size up to the current.
	 */
	public final void randomize(final int upto) {
		Random g = new Random();
		for (int i = 0; i < upto; i++) {
			data.set(i, g.nextBoolean());
		}
		changed = true;
		repaint();
	}

	public void AddData(boolean d) {
		data.set(data.size() + 1, d);
		changed = true;
		repaint();
	}

	public void increaseSize(int i) {
		data.clear(i);
	}

	public BitSet getData() {
		return data;
	}

	public void setDataAtIndex(int i, boolean a, boolean change) {
		data.set(i, a);
		changed = change;
		repaint();
	}

	public void setDataAtIndex(int i, boolean a) {
		data.set(i, a);
		changed = true;
		repaint();
	}

	public void setData(BitSet a) {
		data = a;
		changed = true;
		repaint();
	}

	/**
	 * set that the hitboxes should be recalculated for the data set and have it
	 * repaint the component.
	 */
	public void setChanged() {
		changed = true;
		repaint();
	}

	ArrayList<Area> hitboxes;
	/**
	 * If set to true, then the hitboxes for the display will be recalculated.
	 */
	private boolean changed = false;
	/**
	 * Determines whether or not the data in the bitset is editable.
	 */
	private boolean isEditable = false;
	private boolean paintmode = false;

	public void paint(Graphics g) {
		float width = (float) Math.sqrt((getWidth() * getHeight())
				/ (float) data.size());
		int cols = (int) (getWidth() / width), rows = data.size() / cols > 0 ? data
				.size() / cols
				: 1;
		Graphics2D d = (Graphics2D) g;
		if (isEditable() && (changed || hitboxes.isEmpty())) {
			recalculateHitBoxes(width, width, cols, rows);
		}
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (data.get(i * cols + j)) {
					d.setColor(getForeground());
					d.fill(new Rectangle2D.Float(width * j, i * width, width,
							width));

				}
				// draw the grid line
				d.setColor(Color.black);
				d.draw(new Rectangle2D.Float(width * j, i * width, width, width));
			}
		}
	}

	private void recalculateHitBoxes(float width, float height, int cols,
			int rows) {
		hitboxes.clear();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				hitboxes.add(new Area(new Rectangle2D.Float(width * j, i
						* height, width, height)));
			}
		}
		changed = false;
	}

	/**
	 * @return the isEditable
	 */
	public boolean isEditable() {
		return isEditable;
	}

	/**
	 * @param isEditable
	 *            the isEditable to set
	 */
	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}

	/**
	 * @return the paintmode
	 */
	public boolean isPaintmode() {
		return paintmode;
	}

	/**
	 * @param paintmode
	 *            the paintmode to set
	 */
	public void setPaintmode(boolean paintmode) {
		this.paintmode = paintmode;
	}
}
