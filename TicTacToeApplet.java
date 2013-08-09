/**
 * @author Maxx Boehme
 * @version 1
 *
 * Class to initiate the TicTacToe applet.
 */

import javax.swing.*;


public class TicTacToeApplet extends JApplet {
	private static final long serialVersionUID = 7139529379345798289L;
	
	private JPanel contentPane;
	
	/**
	 * Create the applet.
	 */
	public void init(){
		setSize(550, 550);
		contentPane = new TicTacToeContentPane();
		setContentPane(contentPane);
	}

}
