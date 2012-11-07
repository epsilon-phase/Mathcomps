package bump.org.comp.color;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JLabel;

public class ColorPicker extends JPanel {

	/**
	 * Create the panel.
	 */
	public ColorPicker() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		ColorDisplay colorDisplay = new ColorDisplay();
		GridBagConstraints gbc_colorDisplay = new GridBagConstraints();
		gbc_colorDisplay.gridwidth = 7;
		gbc_colorDisplay.gridheight = 4;
		gbc_colorDisplay.insets = new Insets(0, 0, 0, 5);
		gbc_colorDisplay.gridx = 0;
		gbc_colorDisplay.gridy = 0;
		add(colorDisplay, gbc_colorDisplay);
		
		JLabel lblRed = new JLabel("Red");
		GridBagConstraints gbc_lblRed = new GridBagConstraints();
		gbc_lblRed.insets = new Insets(0, 0, 5, 5);
		gbc_lblRed.gridx = 8;
		gbc_lblRed.gridy = 0;
		add(lblRed, gbc_lblRed);
		
		JSpinner RedSpinner = new JSpinner();
		RedSpinner.setModel(new SpinnerNumberModel(0, null, 255, 1));
		GridBagConstraints gbc_RedSpinner = new GridBagConstraints();
		gbc_RedSpinner.insets = new Insets(0, 0, 5, 0);
		gbc_RedSpinner.gridx = 9;
		gbc_RedSpinner.gridy = 0;
		add(RedSpinner, gbc_RedSpinner);
		
		JLabel lblGreen = new JLabel("Green");
		GridBagConstraints gbc_lblGreen = new GridBagConstraints();
		gbc_lblGreen.insets = new Insets(0, 0, 5, 5);
		gbc_lblGreen.gridx = 8;
		gbc_lblGreen.gridy = 1;
		add(lblGreen, gbc_lblGreen);
		
		JSpinner greenSpinner = new JSpinner();
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
		
		JSpinner blueSpinner = new JSpinner();
		blueSpinner.setModel(new SpinnerNumberModel(0, 0, 255, 1));
		GridBagConstraints gbc_blueSpinner = new GridBagConstraints();
		gbc_blueSpinner.insets = new Insets(0, 0, 5, 0);
		gbc_blueSpinner.gridx = 9;
		gbc_blueSpinner.gridy = 2;
		add(blueSpinner, gbc_blueSpinner);

	}

}
