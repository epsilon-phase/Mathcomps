package bump.org.test;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import bump.org.comp.SingleLineChart;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import bump.org.comp.AnimatedSingleLineChart;
import java.awt.Color;

public class ChartingThingTest extends JFrame {

	private final class ActionListenerImplementation implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setchecks();
		}
	}

	private JPanel contentPane;
	private SingleLineChart qw;
	private JTextField textField;
	private JPopupMenu popupMenu;
	private JCheckBoxMenuItem chckbxmntmBicubicInterpolation;
	private JCheckBoxMenuItem chckbxmntmAntialiasing;
	private JCheckBoxMenuItem chckbxmntmUseQuadraticInterpolation;
	private JMenuItem mntmClearData;
	private JCheckBoxMenuItem chckbxmntmFillInGraph;
	private JMenuItem mntmSetZoom;

	private void setchecks() {
		qw.setAntialias(chckbxmntmAntialiasing.isSelected());
		qw.setBicubic(chckbxmntmBicubicInterpolation.isSelected());
		qw.setCurvelines(chckbxmntmUseQuadraticInterpolation.isSelected());
		qw.setFillArea(chckbxmntmFillInGraph.isSelected());
	}

	private void updatechecks() {
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChartingThingTest frame = new ChartingThingTest();
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
	public ChartingThingTest() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		qw = new AnimatedSingleLineChart();
		qw.setBackgroundcolor(Color.BLACK);
		qw.setCurvelines(false);
		qw.setBicubic(true);
		qw.setFillArea(false);
		qw.setData(new int[] { 23, 34, 52, 23, 35, 12 });
		contentPane.add(qw, BorderLayout.CENTER);

		popupMenu = new JPopupMenu();
		addPopup(qw, popupMenu);

		chckbxmntmBicubicInterpolation = new JCheckBoxMenuItem(
				"Bicubic Interpolation");
		chckbxmntmBicubicInterpolation
				.addActionListener(new ActionListenerImplementation());
		popupMenu.add(chckbxmntmBicubicInterpolation);

		chckbxmntmAntialiasing = new JCheckBoxMenuItem("AntiAliasing");

		popupMenu.add(chckbxmntmAntialiasing);

		chckbxmntmUseQuadraticInterpolation = new JCheckBoxMenuItem(
				"Use quadratic interpolation");

		popupMenu.add(chckbxmntmUseQuadraticInterpolation);
		chckbxmntmAntialiasing
				.addActionListener(new ActionListenerImplementation());
		chckbxmntmBicubicInterpolation
				.addActionListener(new ActionListenerImplementation());

		chckbxmntmFillInGraph = new JCheckBoxMenuItem("Fill in graph");
		chckbxmntmFillInGraph
				.addActionListener(new ActionListenerImplementation());
		popupMenu.add(chckbxmntmFillInGraph);
		mntmClearData = new JMenuItem("Clear Data");
		mntmClearData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				qw.setData(new int[] {});
			}
		});
		
		mntmSetZoom = new JMenuItem("Set Zoom");
		popupMenu.add(mntmSetZoom);

		popupMenu.add(mntmClearData);

		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == e.VK_ENTER) {
					qw.addDataPoint(Integer.parseInt(textField.getText()));
					textField.setText("");
				}
			}
		});
		contentPane.add(textField, BorderLayout.SOUTH);
		textField.setColumns(10);
		updatechecks();
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
	}
}
