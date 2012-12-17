package bump.org.comp;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Arc2D.Float;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Random;

public class BitDisplay extends Canvas {
	private BitSet data = new BitSet(10);

	public BitDisplay() {
		Random q = new Random();
		for (int i = 0; i < 10; i++)
			data.set(i, q.nextBoolean());
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

				drag = getClickintersects(e.getX(), e.getY());
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				int a = getClickintersects(e.getX(), e.getY());
				if (drag != -1)
					for (int i = Math.min(drag, a); i <= Math.max(drag, a); i++) {
						data.set(i, !data.get(i));
					}
				changed = true;
				repaint();
				drag = -1;
			}
		});
		this.hitboxes = new ArrayList<Area>();
	}

	public int getClickintersects(int x, int y) {
		final int index = -1;
		for (int i = 0; i < hitboxes.size(); i++) {
			if (hitboxes.get(i).contains(new Point2D.Float(x, y))) {
				return i;
			}
		}
		return index;
	}

	int drag = 0;

	public void Randomize() {
		Randomize(data.size());
	}

	public void Randomize(int upto) {
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

	public BitSet getData() {
		return data;
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

	public void setChanged() {
		changed = true;
		repaint();
	}

	ArrayList<Area> hitboxes;
	private boolean changed = false;
	private boolean isEditable = false;

	public void paint(Graphics g) {
		float width = 8F;
		int cols = getWidth() / 8, rows = data.size() / cols > 0 ? data.size()
				/ cols : 1;
		float height = getHeight() / rows;
		Graphics2D d = (Graphics2D) g;
		if (isEditable() && (changed || hitboxes.isEmpty())) {
			recalculateHitBoxes(width, cols, rows, height);
		}
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (data.get(i * cols + j)) {
					d.setColor(getForeground());
					d.fill(new Rectangle2D.Float(width * j, i * height, width,
							height));

				}
				d.setColor(Color.black);
				d.draw(new Rectangle2D.Float(width * j, i * height, width,
						height));
			}
		}
	}

	private void recalculateHitBoxes(float width, int cols, int rows,
			float height) {
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
}
