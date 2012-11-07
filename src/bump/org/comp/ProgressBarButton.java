package bump.org.comp;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;

public class ProgressBarButton extends JButton {
	public ProgressBarButton(String text) {
		super(text);
	}

	public ProgressBarButton(String text, int minvalue, int maxvalue, int value) {
		this(text);
		this.minvalue = minvalue;
		this.maxvalue = maxvalue;
		this.value = value;
	}

	public int getMaxValue() {
		return maxvalue;
	}

	public void setMaxValue(int i) {
		maxvalue = i;
	}

	private int maxvalue;
	private int minvalue;

	public void paint(Graphics g) {// call the super thingy so that text gets
									// rendered
		int hstep = 1;
		if (getWidth() - (maxvalue - minvalue) > 0)
			hstep = getWidth() / (maxvalue - minvalue);
		else
			// if the hstep is too big to work afterwards, use the reciprocal.
			hstep = (maxvalue - minvalue) / getWidth();
		super.paint(g);
		g.setXORMode(Color.cyan);
		g.fillRect(0, 0, hstep * value, getHeight());
	}

	public void incrementvalue() {
		value++;
		repaint();
	}

	public int getValue() {
		return value;
	}

	private int value;

	public void setValue(int i) {
		this.value = i;
		repaint();
	}

}
