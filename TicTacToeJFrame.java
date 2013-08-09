/**
 * @author Maxx Boehme
 * @version 1
 *
 * Class to initiate the TicTacToe JFrame.
 */

import java.awt.*;
import javax.swing.*;

public class TicTacToeJFrame extends JFrame {
	private static final long serialVersionUID = 2891272891377734444L;
	
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TicTacToeJFrame frame = new TicTacToeJFrame();
					Toolkit toolkit= frame.getToolkit();
					Dimension size = toolkit.getScreenSize();
					frame.setLocation(size.width/2-frame.getWidth()/2, size.height/2-frame.getHeight()/2);
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
	public TicTacToeJFrame() {
		setTitle("Tic-Tac-Toe");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(550, 550);
		contentPane = new TicTacToeContentPane();
		setContentPane(contentPane);
	}

}
