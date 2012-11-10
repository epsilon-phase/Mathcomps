package bump.org.comp.color;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseMotionAdapter;

public class ColorPicker extends JPanel {
	private JSpinner blueSpinner;
	private JSpinner greenSpinner;
	private JLabel lblRed;
	private JLabel lblGreen;
	private JSpinner RedSpinner;
	private ColorDisplay colorDisplay;

	private final class ChangeListenerImplementation implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			colorShower.setDisplayColor(getColor());
		}
	}

	int target;
	private ColorShower colorShower;
	private JButton btnSetSelectedIndex;

	private Color getColor() {
		return new Color((Integer) RedSpinner.getValue(),
				(Integer) greenSpinner.getValue(),
				(Integer) blueSpinner.getValue());
	}

	public void setColor(Color g) {
		RedSpinner.setValue(new Integer(g.getRed()));
		greenSpinner.setValue(new Integer(g.getGreen()));
		blueSpinner.setValue(new Integer(g.getBlue()));
		colorShower.setDisplayColor(g);
	}

	/**
	 * Create the panel.
	 */
	public ColorPicker() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		setLayout(gridBagLayout);

		colorDisplay = new ColorDisplay();
		
		colorDisplay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				target = colorDisplay.getIndexOfMouseclip(arg0.getX(),
						arg0.getY());
				if (target != -1) {
					colorDisplay.getColorAtIndex(target);
				}
			}
		});
		GridBagConstraints gbc_colorDisplay = new GridBagConstraints();
		gbc_colorDisplay.fill = GridBagConstraints.BOTH;
		gbc_colorDisplay.gridwidth = 7;
		gbc_colorDisplay.gridheight = 5;
		gbc_colorDisplay.insets = new Insets(0, 0, 0, 5);
		gbc_colorDisplay.gridx = 0;
		gbc_colorDisplay.gridy = 0;
		add(colorDisplay, gbc_colorDisplay);

		lblRed = new JLabel("Red");
		GridBagConstraints gbc_lblRed = new GridBagConstraints();
		gbc_lblRed.insets = new Insets(0, 0, 5, 5);
		gbc_lblRed.gridx = 8;
		gbc_lblRed.gridy = 0;
		add(lblRed, gbc_lblRed);

		RedSpinner = new JSpinner();
		RedSpinner.addChangeListener(new ChangeListenerImplementation());
		RedSpinner.setModel(new SpinnerNumberModel(0, null, 255, 1));
		GridBagConstraints gbc_RedSpinner = new GridBagConstraints();
		gbc_RedSpinner.insets = new Insets(0, 0, 5, 0);
		gbc_RedSpinner.gridx = 9;
		gbc_RedSpinner.gridy = 0;
		add(RedSpinner, gbc_RedSpinner);

		lblGreen = new JLabel("Green");
		GridBagConstraints gbc_lblGreen = new GridBagConstraints();
		gbc_lblGreen.insets = new Insets(0, 0, 5, 5);
		gbc_lblGreen.gridx = 8;
		gbc_lblGreen.gridy = 1;
		add(lblGreen, gbc_lblGreen);

		greenSpinner = new JSpinner();
		greenSpinner.addChangeListener(new ChangeListenerImplementation());
		greenSpinner.setModel(new SpinnerNumberModel(0, null, 255, 1));
		GridBagConstraints gbc_greenSpinner = new GridBagConstraints();
		gbc_greenSpinner.insets = new Insets(0, 0, 5, 0);
		gbc_greenSpinner.gridx = 9;
		gbc_greenSpinner.gridy = 1;
		add(greenSpinner, gbc_greenSpinner);

		JLabel lblBlue = new JLabel("Blue");
		GridBagConstraints gbc_lblBlue = new GridBagConstraints();
		gbc_lblBlue.insets = new Insets(0, 0, 5, 5);
		gbc_lblBlue.gridx = 8;
		gbc_lblBlue.gridy = 2;
		add(lblBlue, gbc_lblBlue);

		blueSpinner = new JSpinner();
		blueSpinner.addChangeListener(new ChangeListenerImplementation());
		blueSpinner.setModel(new SpinnerNumberModel(0, 0, 255, 1));
		GridBagConstraints gbc_blueSpinner = new GridBagConstraints();
		gbc_blueSpinner.insets = new Insets(0, 0, 5, 0);
		gbc_blueSpinner.gridx = 9;
		gbc_blueSpinner.gridy = 2;
		add(blueSpinner, gbc_blueSpinner);

		JButton btnAddToList = new JButton("Add To List");
		btnAddToList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				colorDisplay.addColor(getColor());
			}
		});
		GridBagConstraints gbc_btnAddToList = new GridBagConstraints();
		gbc_btnAddToList.insets = new Insets(0, 0, 5, 5);
		gbc_btnAddToList.gridx = 8;
		gbc_btnAddToList.gridy = 3;
		add(btnAddToList, gbc_btnAddToList);

		colorShower = new ColorShower();
		GridBagConstraints gbc_colorShower = new GridBagConstraints();
		gbc_colorShower.fill = GridBagConstraints.BOTH;
		gbc_colorShower.insets = new Insets(0, 0, 5, 0);
		gbc_colorShower.gridx = 9;
		gbc_colorShower.gridy = 3;
		add(colorShower, gbc_colorShower);

		btnSetSelectedIndex = new JButton("Set Selected Index");
		btnSetSelectedIndex.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (target != -1)
					colorDisplay.setColorAtIndex(target, getColor());
			}
		});
		GridBagConstraints gbc_btnSetSelectedIndex = new GridBagConstraints();
		gbc_btnSetSelectedIndex.insets = new Insets(0, 0, 0, 5);
		gbc_btnSetSelectedIndex.gridx = 8;
		gbc_btnSetSelectedIndex.gridy = 4;
		add(btnSetSelectedIndex, gbc_btnSetSelectedIndex);

	}
}
