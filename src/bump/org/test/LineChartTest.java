package bump.org.test;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollBar;
import bump.org.comp.LineChart;

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

public class LineChartTest extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LineChartTest frame = new LineChartTest();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private Point last;
	private LineChart lineChart;

	/**
	 * Create the frame.
	 */
	public LineChartTest() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		lineChart = new LineChart();
		lineChart.addMouseListener(new MouseListener() {

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
				// TODO Auto-generated method stub
				if (e.getClickCount() >= 2)
					lineChart.setOriginpoint(new double[] { 0, 0 });
				lineChart.repaint();
			}
		});
		lineChart.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {

				if (last == null)
					last = e.getPoint();
				else {
					int x = e.getX() - (int) last.getX();
					int y = e.getY() - (int) last.getY();
					lineChart.shift(x, y);
					last = e.getPoint();
				}

			}
		});
		lineChart.addDatas(new double[][] { new double[] { 0, 0 },
				new double[] { 1, 1 } });
		contentPane.add(lineChart, BorderLayout.CENTER);
	}

}
