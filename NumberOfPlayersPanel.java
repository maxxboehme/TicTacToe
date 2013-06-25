import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class NumberOfPlayersPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JPanel pl;
	private CardLayout cl;
	private JButton a;
	private JButton b;
	private JLabel title;
	
	NumberOfPlayersPanel(JPanel p, CardLayout c){
		this.setLayout(null);
		this.pl = p;
		this.cl = c;
		
		JButton OnePlayerButton = new JButton("1 Player");
		OnePlayerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		OnePlayerButton.setBounds(218, 198, 100, 40);
		this.add(OnePlayerButton);
		
		JButton TwoPlayersButton = new JButton("2 Players");
		TwoPlayersButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cl.show(pl, "PlayersPanel");
			}
		});
		TwoPlayersButton.setBounds(218, 249, 100, 40);
		this.add(TwoPlayersButton);
		
		title = new JLabel("Tic-Tac-Toe");
		title.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 30));
		title.setBounds(174, 98, 234, 53);
		this.add(title);
	}
	/*JPanel NumberPlayersPanel = new JPanel();
	contentPane.add(NumberPlayersPanel, "NumberPlayersPanel");
	NumberPlayersPanel.setLayout(null);
	
	JButton OnePlayerButton = new JButton("1 Player");
	OnePlayerButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
		}
	});
	OnePlayerButton.setBounds(176, 113, 100, 40);
	NumberPlayersPanel.add(OnePlayerButton);
	
	JButton TwoPlayersButton = new JButton("2 Players");
	TwoPlayersButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			c.show(contentPane, "PlayersPanel");
		}
	});
	TwoPlayersButton.setBounds(176, 160, 100, 40);
	NumberPlayersPanel.add(TwoPlayersButton);
	
	JLabel lblTictactoe = new JLabel("Tic-Tac-Toe");
	lblTictactoe.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 30));
	lblTictactoe.setBounds(137, 11, 234, 53);
	NumberPlayersPanel.add(lblTictactoe);*/
}
