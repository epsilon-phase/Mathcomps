package bump.org.test;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import bump.org.comp.ProgressBarButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ProgressBarButtonTest extends JFrame {

	private JPanel contentPane;
	private ProgressBarButton prgrsbrbtnIncrement;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProgressBarButtonTest frame = new ProgressBarButtonTest();
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
	public ProgressBarButtonTest() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		prgrsbrbtnIncrement = new ProgressBarButton((String) null);
		prgrsbrbtnIncrement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prgrsbrbtnIncrement.incrementvalue();
			}
		});
		prgrsbrbtnIncrement.setMaxValue(100);
		prgrsbrbtnIncrement.setText("Increment");
		contentPane.add(prgrsbrbtnIncrement, BorderLayout.CENTER);
	}
}
