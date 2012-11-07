package bump.org.comp;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;

;
public class LineChart extends Canvas {
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D h = (Graphics2D) g.create();
		h.translate(getWidth() / 2, getHeight() / 2);
	}
}
