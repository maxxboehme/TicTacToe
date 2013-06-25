import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.print.attribute.AttributeSet;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.MaskFormatter;
import javax.swing.text.PlainDocument;

import java.awt.CardLayout;
import java.awt.GridLayout;

import javax.swing.ButtonModel;
import javax.swing.SpringLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import java.awt.Component;
import javax.swing.ButtonGroup;
import java.awt.Font;
import javax.swing.UIManager;
import javax.swing.JFormattedTextField;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class TicTacToeGUI extends JFrame {

	private JPanel contentPane;
	private CardLayout c;
	private Game game;
	private JPanel PlayersPanel;
	private JPanel gamePanel;
	private Player p1;
	private Player p2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TicTacToeGUI frame = new TicTacToeGUI();
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
	public TicTacToeGUI() {
		setTitle("Tic-Tac-Toe");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(550, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		c = new CardLayout(0, 0);
		contentPane.setLayout(c);
		
		this.p1 = new Player(Token.X, Color.red, "Maxx Amand Boehme");
		this.p2 = new Player(Token.O, new ComputerAI(), Color.cyan, "Gus Stonewall Boehme");
		
		NumberOfPlayersPanel npp = new NumberOfPlayersPanel(contentPane, c);
		contentPane.add(npp, "NumberPlayersPanel");
		npp.setLayout(null);
		
		this.setUpPlayersPanel();
		
		
		/* Game Panel */
		gamePanel = new JPanel();
		contentPane.add(gamePanel, "Game");
		gamePanel.setLayout(null);
		GraphicsPanel gp = new GraphicsPanel(500, 450);
		Graphics g = gp.getG();
		this.game = new Game(g, p1, p2, 2);
		gp.game(this.game);
		gp.setBounds(15, 10, 500, 450);
		gamePanel.add(gp);
		/*game.playerTurn(0, 0);
		game.playerTurn(1, 1);
		game.playerTurn(0, 2);
		game.playerTurn(0, 1);
		game.playerTurn(1, 0);
		game.playerTurn(2, 1);
		game.playerTurn(2, 1);*/
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
				c.show(contentPane, "NumberPlayersPanel");
			}
		});
		btnBack.setBounds(426, 478, 89, 23);
		gamePanel.add(btnBack);
	}
	
	private void setUpPlayersPanel(){
		PlayersPanel = new JPanel();
		contentPane.add(PlayersPanel, "PlayersPanel");
		PlayersPanel.setLayout(null);
		
		final JTextField Player1TextField = new ReturnIfEmptyTextField("Player 1");
		Player1TextField.setFont(new Font("Tahoma", Font.BOLD, 14));
		Player1TextField.setBounds(147, 14, 279, 30);
		PlayersPanel.add(Player1TextField);
		Player1TextField.setColumns(10);
		Player1TextField.setDocument(new JTextFieldLimit(20));
		TextPrompt tp = new TextPrompt("Player1", Player1TextField);
		tp.changeAlpha(0.5f);

		
		final JTextField Player2TextField = new ReturnIfEmptyTextField("Player 2");
		Player2TextField.setFont(new Font("Tahoma", Font.BOLD, 14));
		Player2TextField.setBounds(147, 239, 279, 30);
		PlayersPanel.add(Player2TextField);
		Player2TextField.setColumns(10);
		Player2TextField.setDocument(new JTextFieldLimit(20));
		TextPrompt tp2 = new TextPrompt("Player2", Player2TextField);
		tp2.changeAlpha(0.5f);
		
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
				c.show(contentPane, "NumberPlayersPanel");
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
				c.show(contentPane, "Game");
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
