package bump.org.test;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.math.BigInteger;
import java.util.BitSet;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import bump.org.comp.BitDisplay;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BitDisplayTest extends JFrame {

	private JPanel contentPane;
	private BitDisplay bitDisplay;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BitDisplayTest frame = new BitDisplayTest();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public BitDisplayTest() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 445, 283);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));

		setContentPane(contentPane);

		bitDisplay = new BitDisplay();
		bitDisplay.setPaintmode(true);
		bitDisplay.setEditable(true);
		bitDisplay.setForeground(Color.RED);
		bitDisplay.getData().set(0, 2, true);
		bitDisplay.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == e.BUTTON1) {
					// bitDisplay.Randomize();
				} else {
					// bitDisplay.Randomize(bitDisplay.getData().size()+1);
				}
				if (e.isShiftDown()) {
					bitDisplay.AddData(true);
				}
				if (e.isControlDown()) {
					BitSet gg = bitDisplay.getData();
					BigInteger a = new BigInteger("0", 10);
					for (int i = 0; i < gg.size(); i++) {
						if (gg.get(i))
							a = a.setBit(i);
					}
					setTitle(a.toString(10) + ":" + Integer.toString(gg.size()));
				} else {
					setTitle(Integer.toString(bitDisplay.getData().size()));
				}
			}
		});

		contentPane.add(bitDisplay, BorderLayout.CENTER);
	}

}
