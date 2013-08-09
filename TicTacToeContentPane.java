/**
 * @author Maxx Boehme
 * @version 1
 *
 * Class that use used to hold the whole TicTacToe GUI.
 */

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;


public class TicTacToeContentPane extends JPanel {
	private static final long serialVersionUID = 5453749950717452195L;
	
	private JPanel me;
	private CardLayout c;
	private Game game;
	private JPanel PlayersPanel;
	private JPanel gamePanel;
	private JPanel AIPanel;
	private JPanel NumberOfPlayersPanel;
	private Player p1;
	private Player p2;
	
	/**
	 * Create the panel.
	 */
	public TicTacToeContentPane() {
		me = this;
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		c = new CardLayout(0, 0);
		this.setLayout(c);
		
		this.p1 = new Player(Token.X, Color.red, "Maxx Amand Boehme");
		this.p2 = new Player(Token.O, true, Color.cyan, "Gus Stonewall Boehme");
		this.p2.setAILevel(AILevel.Medium);
		
		this.setUpNumberOfPlayersPanel();
		
		this.setUpAIPanel();
		this.setUpPlayersPanel();
		
		/* Game Panel */
		gamePanel = new JPanel();
		this.add(gamePanel, "Game");
		gamePanel.setLayout(null);
		GraphicsPanel gp = new GraphicsPanel(500, 450);
		Graphics g = gp.getG();
		this.game = new Game(g, p1, p2, 2);
		gp.game(this.game);
		gp.setBounds(15, 10, 500, 450);
		gamePanel.add(gp);
		gp.addMouseListener(new MouseAdapter() {
	         @Override
	         public void mouseClicked(MouseEvent e) {  // mouse-clicked handler
	            int x = e.getX();
	            int y = e.getY();
	            if(game.getState() == GameState.Turn && x >= 100 && y >=100 && x<= 400 && y <= 400) {
	            	int selectedr = (y / 100) - 1;
	            	int selectedc = (x / 100) -1;
	            	System.out.println("R: "+selectedr+ " C: "+selectedc);
	            	game.playerTurn(selectedr, selectedc);
	            } else if(game.getState() == GameState.NewGame){
	            	System.out.println("Mouse Click");
	            	game.createNewGame();
	            }
	         }
	      });
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				game.clearGame();
				c.show(me, "NumberPlayersPanel");
			}
		});
		btnBack.setBounds(15, 478, 89, 23);
		gamePanel.add(btnBack);
		
		JButton btnNewGame = new JButton("New Game");
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				game.createNewGame();
			}
		});
		btnNewGame.setBounds(415, 478, 100, 23);
		gamePanel.add(btnNewGame);
	}
	
	private void setUpNumberOfPlayersPanel(){
		this.NumberOfPlayersPanel = new JPanel();
		this.add(this.NumberOfPlayersPanel, "NumberPlayersPanel");
		this.NumberOfPlayersPanel.setLayout(null);
		
		JButton OnePlayerButton = new JButton("1 Player");
		OnePlayerButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		OnePlayerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				c.show(me, "AIPanel");
				p2.setAI(true);
			}
		});
		OnePlayerButton.setBounds(198, 194, 150, 60);
		this.NumberOfPlayersPanel.add(OnePlayerButton);
		
		JButton TwoPlayersButton = new JButton("2 Players");
		TwoPlayersButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		TwoPlayersButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				c.show(me, "PlayersPanel");
				p2.setAI(false);
			}
		});
		TwoPlayersButton.setBounds(198, 277, 150, 60);
		this.NumberOfPlayersPanel.add(TwoPlayersButton);
		
		JLabel title = new JLabel("Tic-Tac-Toe");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 40));
		title.setBounds(155, 104, 234, 53);
		this.NumberOfPlayersPanel.add(title);
	}
	
	private void setUpAIPanel() {
		this.AIPanel = new JPanel();
		this.add(this.AIPanel, "AIPanel");
		this.AIPanel.setLayout(null);
		
		JButton easyAIButton = new JButton("Easy");
		easyAIButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				p2.setAILevel(AILevel.Easy);
				c.show(me, "PlayersPanel");
			}
		});
		easyAIButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		easyAIButton.setBounds(195, 176, 150, 40);
		AIPanel.add(easyAIButton);
		
		JButton mediumAIButton = new JButton("Medium");
		mediumAIButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				p2.setAILevel(AILevel.Medium);
				c.show(me, "PlayersPanel");
			}
		});
		mediumAIButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		mediumAIButton.setBounds(195, 221, 150, 40);
		AIPanel.add(mediumAIButton);
		
		JButton hardAIButton = new JButton("Hard");
		hardAIButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				p2.setAILevel(AILevel.Hard);
				c.show(me, "PlayersPanel");
			}
		});
		hardAIButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		hardAIButton.setBounds(195, 266, 150, 40);
		AIPanel.add(hardAIButton);
		
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				c.show(me, "NumberPlayersPanel");
			}
		});
		backButton.setBounds(10, 478, 89, 23);
		AIPanel.add(backButton);
		
		JLabel lblNewLabel_1 = new JLabel("Computer Level");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel_1.setBounds(152, 109, 244, 40);
		AIPanel.add(lblNewLabel_1);
	}
	
	private void setUpPlayersPanel(){
		PlayersPanel = new JPanel();
		this.add(PlayersPanel, "PlayersPanel");
		PlayersPanel.setLayout(null);
		
		final JTextField Player1TextField = new ReturnIfEmptyTextField("Player 1");
		Player1TextField.setFont(new Font("Tahoma", Font.BOLD, 14));
		Player1TextField.setBounds(147, 14, 279, 30);
		PlayersPanel.add(Player1TextField);
		Player1TextField.setColumns(10);
		Player1TextField.setDocument(new JTextFieldLimit(20));
		TextPrompt tp = new TextPrompt("Player1", Player1TextField);
		tp.changeAlpha(0.8f);

		
		final JTextField Player2TextField = new ReturnIfEmptyTextField("Player 2");
		Player2TextField.setFont(new Font("Tahoma", Font.BOLD, 14));
		Player2TextField.setBounds(147, 239, 279, 30);
		PlayersPanel.add(Player2TextField);
		Player2TextField.setColumns(10);
		Player2TextField.setDocument(new JTextFieldLimit(20));
		TextPrompt tp2 = new TextPrompt("Player2", Player2TextField);
		tp2.changeAlpha(0.8f);
		
		JLabel lblPlayer = new JLabel("Player 1");
		lblPlayer.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblPlayer.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblPlayer.setBounds(66, 17, 100, 23);
		PlayersPanel.add(lblPlayer);
		
		JLabel lblPlayer_1 = new JLabel("Player 2");
		lblPlayer_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblPlayer_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblPlayer_1.setBounds(66, 242, 78, 23);
		PlayersPanel.add(lblPlayer_1);
		
		final ButtonGroup order = new ButtonGroup();
		
		final JRadioButton Player1First = new JRadioButton("Player 1");
		Player1First.setSelected(true);
		Player1First.setBounds(184, 402, 100, 23);
		PlayersPanel.add(Player1First);
		
		final JRadioButton Player2First = new JRadioButton("Player 2");
		Player2First.setBounds(286, 402, 100, 23);
		PlayersPanel.add(Player2First);
		
		final JRadioButton SwitchWhogoesFirst = new JRadioButton("Switch");
		SwitchWhogoesFirst.setBounds(388, 402, 89, 23);
		PlayersPanel.add(SwitchWhogoesFirst);
		order.add(Player1First);
		order.add(Player2First);
		order.add(SwitchWhogoesFirst);
		
		JButton Back = new JButton("Back");
		Back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				c.show(me, "NumberPlayersPanel");
			}
		});
		Back.setBounds(10, 478, 89, 23);
		PlayersPanel.add(Back);
		
		JLabel lblWhoGoesFirst = new JLabel("Who Goes First");
		lblWhoGoesFirst.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblWhoGoesFirst.setBounds(49, 402, 114, 23);
		PlayersPanel.add(lblWhoGoesFirst);
		
		ButtonGroup playerType = new ButtonGroup();
		
		final JRadioButton RadioButtonX = new JRadioButton("X");
		RadioButtonX.setSelected(true);
		RadioButtonX.setFont(new Font("Mistral", Font.BOLD, 30));
		RadioButtonX.setBounds(218, 65, 65, 39);
		PlayersPanel.add(RadioButtonX);
		playerType.add(RadioButtonX);
		
		final JRadioButton RadioButtonO = new JRadioButton("O");
		RadioButtonO.setFont(new Font("Mistral", Font.BOLD, 30));
		RadioButtonO.setBounds(285, 65, 54, 39);
		PlayersPanel.add(RadioButtonO);
		playerType.add(RadioButtonO);
		
		JLabel lblNewLabel = new JLabel("Player Type");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(123, 78, 89, 19);
		PlayersPanel.add(lblNewLabel);
		
		ButtonGroup p1Colors = new ButtonGroup();
		
		final JRadioButton P1ColorRedRB = new JRadioButton("Red");
		P1ColorRedRB.setSelected(true);
		P1ColorRedRB.setBounds(176, 132, 65, 23);
		PlayersPanel.add(P1ColorRedRB);
		
		final JRadioButton P1ColorBlueRB = new JRadioButton("Blue");
		P1ColorBlueRB.setBounds(243, 132, 65, 23);
		PlayersPanel.add(P1ColorBlueRB);
		
		final JRadioButton P1ColorGreenRB = new JRadioButton("Green");
		P1ColorGreenRB.setBounds(314, 132, 70, 23);
		PlayersPanel.add(P1ColorGreenRB);
		
		JRadioButton P1ColorPurpleRB = new JRadioButton("Purple");
		P1ColorPurpleRB.setBounds(399, 132, 89, 23);
		PlayersPanel.add(P1ColorPurpleRB);
		
		p1Colors.add(P1ColorRedRB);
		p1Colors.add(P1ColorBlueRB);
		p1Colors.add(P1ColorGreenRB);
		p1Colors.add(P1ColorPurpleRB);
		
		ButtonGroup p2Colors = new ButtonGroup();
		
		final JRadioButton P2RedRB = new JRadioButton("Red");
		P2RedRB.setBounds(164, 302, 65, 23);
		PlayersPanel.add(P2RedRB);
		
		final JRadioButton P2BlueRB = new JRadioButton("Blue");
		P2BlueRB.setSelected(true);
		P2BlueRB.setBounds(231, 302, 65, 23);
		PlayersPanel.add(P2BlueRB);
		
		final JRadioButton P2GreenRB = new JRadioButton("Green");
		P2GreenRB.setBounds(302, 302, 70, 23);
		PlayersPanel.add(P2GreenRB);
		
		JRadioButton P2PurpleRB = new JRadioButton("Purple");
		P2PurpleRB.setBounds(399, 302, 89, 23);
		PlayersPanel.add(P2PurpleRB);
		
		p2Colors.add(P2RedRB);
		p2Colors.add(P2BlueRB);
		p2Colors.add(P2GreenRB);
		p2Colors.add(P2PurpleRB);
		
		JButton Start = new JButton("Start");
		Start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//System.out.println("p: "+Player1TextField.getText());
				p1.setName(Player1TextField.getText());
				Player1TextField.setText("");
				p2.setName(Player2TextField.getText());
				Player2TextField.setText("");
				
				if(RadioButtonX.isSelected()){
					p1.setType(Token.X);
					p2.setType(Token.O);
				} else {
					p1.setType(Token.O);
					p2.setType(Token.X);
				}
				
				if(Player1First.isSelected()) {
					game.initNewGame(0);
				} else if(Player2First.isSelected()) {
					game.initNewGame(1);
				} else {
					game.initNewGame(2);
				}
				
				if(P1ColorRedRB.isSelected()){
					p1.setColor(Color.red);
				} else if(P1ColorBlueRB.isSelected()) {
					p1.setColor(Color.cyan);
				} else if(P1ColorGreenRB.isSelected()) {
					p1.setColor(Color.green);
				} else {
					p1.setColor(Color.magenta);
				}
				
				P1ColorRedRB.setSelected(true);
				
				if(P2RedRB.isSelected()){
					p2.setColor(Color.red);
				} else if(P2BlueRB.isSelected()) {
					p2.setColor(Color.cyan);
				} else if(P2GreenRB.isSelected()) {
					p2.setColor(Color.green);
				} else {
					p2.setColor(Color.magenta);
				}
				
				P2BlueRB.setSelected(true);
				RadioButtonX.setSelected(true);
				Player1First.setSelected(true);
				c.show(me, "Game");
			}
		});
		Start.setBounds(435, 478, 89, 23);
		PlayersPanel.add(Start);
		
		JLabel lblPlayerColor = new JLabel("Player Color");
		lblPlayerColor.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPlayerColor.setBounds(66, 132, 89, 18);
		PlayersPanel.add(lblPlayerColor);
		
		JLabel label = new JLabel("Player Color");
		label.setFont(new Font("Tahoma", Font.BOLD, 12));
		label.setBounds(54, 306, 89, 18);
		PlayersPanel.add(label);
	}
}
