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
import java.beans.PropertyVetoException;

import javax.swing.JInternalFrame;
import javax.swing.border.CompoundBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.BevelBorder;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class LineChartTest extends JFrame {
	/**
	 * Holds coordinates for future use in drawing the coordinates out properly.
	 * 
	 * @author Jaked122
	 * 
	 */
	private final class CoordinateTable extends DefaultTableModel {
		Class[] columnTypes = new Class[] { Double.class, Double.class };

		private CoordinateTable(final Object[][] data,
				final Object[] columnNames) {
			super(data, columnNames);
		}

		/**
		 * Construct a two dimensional array that contains the validated data in
		 * the table(e.g. the data where both columns have entries).
		 * 
		 * @return the two dimensional array with fully validated answers.
		 */
		public double[][] getpositions() {
			int totalcount = 0;
			for (int i = 0; i < getRowCount(); i++) {
				if (getValueAt(i, 0) != null && getValueAt(i, 1) != null) {
					totalcount += 1;
				}
			}
			double[][] a;
			a = new double[totalcount][];
			for (int i = 0; i < totalcount; i++) {
				a[i] = new double[2];
				a[i][0] = (Double) getValueAt(i, 0);
				a[i][1] = (Double) getValueAt(i, 1);
			}

			return a;
		}

		/**
		 * Should return that the two different types of columns are doubles.
		 * 
		 * @return a Double.class
		 * @param columnIndex
		 *            the index of the column that you feel like confirming as a
		 *            double.
		 */
		public Class getColumnClass(final int columnIndex) {
			return columnTypes[columnIndex];
		}
	}

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {
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

	private void setoptions() {
		lineChart.setShowCoordinates(chckbxmntmShowcoordinates.isSelected());
	}

	private Point last;
	private LineChart lineChart;
	private JTable table;
	private JCheckBoxMenuItem chckbxmntmShowcoordinates;

	/**
	 * Goes through the table and returns all completed rows(or up to the first
	 * row that is not completed).
	 * 
	 * @return two dimensional array that contains the validated information in
	 *         the table.
	 */
	private double[][] constructfromtable() {
		int totalcount = 0;
		for (int i = 0; i < table.getRowCount(); i++) {
			if (table.getValueAt(i, 0) != null
					&& table.getValueAt(i, 1) != null)
				totalcount += 1;
		}
		double[][] a;
		a = new double[totalcount][];
		for (int i = 0; i < totalcount; i++) {
			a[i] = new double[2];
			a[i][0] = (Double) table.getValueAt(i, 0);
			a[i][1] = (Double) table.getValueAt(i, 1);
		}

		return a;
	}

	/**
	 * Create the frame.
	 */
	public LineChartTest() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(final ComponentEvent e) {
				lineChart.setBounds(getBounds());
				lineChart.setLocation(0, 0);
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 607, 464);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JInternalFrame internalFrame_1 = new JInternalFrame("Table");
		internalFrame_1.setIconifiable(true);

		internalFrame_1.setBounds(155, 187, 424, 289);
		internalFrame_1.setMaximizable(true);
		internalFrame_1.setResizable(true);
		contentPane.add(internalFrame_1);

		table = new JTable();
		table.setModel(new CoordinateTable(new Object[][] { { null, null },
				{ null, null }, { null, null }, { null, null }, { null, null },
				{ null, null }, { null, null }, { null, null }, { null, null },
				{ null, null }, { null, null }, { null, null }, { null, null },
				{ null, null }, { null, null }, { null, null }, },
				new String[] { "X", "Y" }));
		table.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(final FocusEvent e) {
				lineChart.setDatas(constructfromtable());
			}
		});
		internalFrame_1.getContentPane().add(table, BorderLayout.CENTER);
		internalFrame_1.setVisible(true);

		lineChart = new LineChart();
		lineChart.setShowCoordinates(true);
		lineChart.setLinecolor(Color.BLUE);
		lineChart.setLocation(0, 2);
		lineChart.setSize(32, 343);
		contentPane.add(lineChart);
		
				JPopupMenu popupMenu = new JPopupMenu();
				popupMenu.setBounds(0, 0, 200, 50);
				contentPane.add(popupMenu);
				
						chckbxmntmShowcoordinates = new JCheckBoxMenuItem("ShowCoordinates");
						chckbxmntmShowcoordinates.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								setoptions();
							}
						});
						popupMenu.add(chckbxmntmShowcoordinates);
		lineChart.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(final MouseEvent e) {
				// set the last coordinates equal to null(so that it does not
				// ruin the calculations after the user resumes dragging).
				last = null;
			}

			@Override
			public void mouseClicked(final MouseEvent e) {

				if (e.getClickCount() >= 2) {
					lineChart.setOriginpoint(new double[] { 0, 0 });
				}
				lineChart.repaint();
			}

			@Override
			public void mouseEntered(final MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(final MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(final MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		lineChart.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(final MouseEvent e) {
				if (e.isShiftDown())
					if (last == null) {
						last = e.getPoint();
					} else {
						int x = e.getX() - (int) last.getX();
						int y = e.getY() - (int) last.getY();
						// make a smoother buffer strategy than this.
						lineChart.createBufferStrategy(2);
						lineChart.shift(x, y);

						last = e.getPoint();
					}

			}
		});
		lineChart.addDatas(new double[][] { new double[] { 0, 0 },
				new double[] { 1, 1 }, new double[] { 2, 3 },
				new double[] { 3, 3 } });
	}
}
