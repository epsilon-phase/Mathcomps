package bump.org.test;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import bump.org.comp.PetalChart;
import bump.org.comp.color.RandomColorGen;

public class PetalChartTest extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PetalChartTest frame = new PetalChartTest();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private PetalChart chart;

	/**
	 * Create the frame.
	 */
	public PetalChartTest() {
		chart = new PetalChart();
		chart.addData(new float[] { 1, 2, 3, 4, 6, 1 });
		chart.setCower(new RandomColorGen());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(chart);
	}
}
