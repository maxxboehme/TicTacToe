/*********************
 * Author: Maxx Boehme
 */

import java.awt.*;
import java.util.Random;
import java.util.concurrent.locks.*;


public class Game implements Runnable{
	
	private Board board;
	private Player[] players;
	private int[] winPositon;
	private GameState state;
	private int currentplayer;
	private int startingplayer;
	private int firstplayer;
	private int turn;
	private Graphics g;
	private Lock stateLock;
	

	Game(Graphics g, Player p1, Player p2, int playerfirst){
		this.g = g;
		board = new Board(g, 100, 100, 300);
		players = new Player[2];
		players[0] = p1;
		players[1] = p2;
		firstplayer = playerfirst;
		if(playerfirst != 2){
			startingplayer = playerfirst;
			currentplayer = playerfirst;
		} else {
			Random r = new Random();
			startingplayer = r.nextInt(2);
			currentplayer = startingplayer;
		}
		turn = 1;
		stateLock = new ReentrantLock();
		state = GameState.Clear;
		
	}
	
	public void changeState(GameState gs){
		stateLock.lock();
		this.state = gs;
		stateLock.unlock();
	}
	
	public GameState getState(){
		return state;
	}
	
	public void setPlayers(Player p1, Player p2){
			players[0] = p1;
			players[1] = p2;
	}
	
	private void computerTurn() {
		if(players[currentplayer].isAI()){
			this.changeState(GameState.Running);
			ComputerAI ai = players[currentplayer].getAI();
			Point p = ai.turn(this.board, players[currentplayer].getType(), this.turn);
			board.setCellifEmpty(players[currentplayer], p.x, p.y);
			turn++;
			if((this.winPositon = board.hasWonArray(players[currentplayer], p.x, p.y)) != null){
				this.changeState(GameState.NewGame);
				players[currentplayer].win();
				this.changePlayer();
				players[currentplayer].lost();
				System.out.println("Won");
			} else if(turn >= 10) {
				this.changeState(GameState.NewGame);
				players[0].draw();
				players[1].draw();
				System.out.println("Draw");
			} else {
				this.changePlayer();
				this.changeState(GameState.Turn);
			}
		} else {
			this.changeState(GameState.Turn);
		}
		//this.paint();
	}
	
	public boolean playerTurn(int x, int y){
		boolean result = false;
		if(x >= 0 && y >= 0 && x < 3 && y < 3){
			if(state == GameState.Turn){
				boolean w = board.setCellifEmpty(players[currentplayer], x, y);
				if(w){
					result = true;
					this.turn++;
					if((this.winPositon = board.hasWonArray(players[currentplayer], x, y)) != null){
						this.changeState(GameState.NewGame);
						players[currentplayer].win();
						this.changePlayer();
						players[currentplayer].lost();
						System.out.println("Won");
					} else if(turn >= 10) {
						this.changeState(GameState.NewGame);
						players[0].draw();
						players[1].draw();
						System.out.println("Draw");
					} else {
						this.changePlayer();
						if(players[currentplayer].isAI()){
							this.changeState(GameState.Running);
							Thread aithread = new Thread(this);
							aithread.start();
						} else {
							this.changeState(GameState.Turn);
						}
					}
					System.out.println("Turn: "+this.turn);
					//this.paint();
				}
			}
		}
		return result;
	}
	
	private void changePlayer(){
		this.currentplayer = (this.currentplayer + 1) % 2;
	}
	
	public void createNewGame(){
		turn = 1;
		board.clear();
		if(firstplayer != 2){
			startingplayer = firstplayer;
			currentplayer = firstplayer;
		} else {
			startingplayer = (this.startingplayer + 1) % 2;
			currentplayer = startingplayer;
		}
		
		if(players[currentplayer].isAI()){
			this.changeState(GameState.Running);
			Thread aithread = new Thread(this);
			aithread.start();
		} else {
			this.changeState(GameState.Turn);
		}
		//this.paint();
	}
	
	public void paint(){
		this.g.clearRect(0, 0, 500, 450);
		
		this.g.setColor(new Color(56, 168, 169));
		this.g.fillRoundRect(100, 10, 300, 50, 25, 25);
		this.g.setColor(Color.black);
		this.g.fillRoundRect(103, 13, 294, 44, 25, 25);
		this.g.setColor(new Color(21, 82, 83));
		this.g.fillRoundRect(105, 15, 290, 40, 25, 25);
		this.g.setColor(Color.white);
		this.g.setFont(new Font("Ariel", Font.BOLD, 35));
		this.g.drawString("Tic-Tac-Toe", 171, 48);
		this.g.drawString("Tic-Tac-Toe", 169, 46);
		this.g.drawString("Tic-Tac-Toe", 171, 46);
		this.g.drawString("Tic-Tac-Toe", 169, 48);
		this.g.setColor(Color.black);
		this.g.setFont(new Font("Ariel", Font.BOLD, 35));
		this.g.drawString("Tic-Tac-Toe", 170, 47);
		
		this.board.paint();
		
		if(this.state != GameState.Clear) {
			if(this.state == GameState.NewGame && this.winPositon != null){
				int cell_size = this.board.getCellSize();
				int mid = cell_size /2;
				int[] pos = this.board.getPosition();
				g.setColor(Color.white);
				Graphics2D g2d = (Graphics2D)g;

				g.setColor(Color.white);
				g2d.setStroke(new BasicStroke(14, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
				g2d.drawLine(this.winPositon[1]*cell_size + mid + pos[0], this.winPositon[0]*cell_size + mid + pos[1],
						this.winPositon[3]*cell_size + mid + pos[0], this.winPositon[2]*cell_size + mid + pos[1]);

				g.setColor(Color.black);
				g2d.setStroke(new BasicStroke(10, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
				g2d.drawLine(this.winPositon[1]*cell_size + mid + pos[0], this.winPositon[0]*cell_size + mid + pos[1],
						this.winPositon[3]*cell_size + mid + pos[0], this.winPositon[2]*cell_size + mid + pos[1]);

				g.setColor(Color.white);
				g2d.setStroke(new BasicStroke(6, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
				g2d.drawLine(this.winPositon[1]*cell_size + mid + pos[0], this.winPositon[0]*cell_size + mid + pos[1],
						this.winPositon[3]*cell_size + mid + pos[0], this.winPositon[2]*cell_size + mid + pos[1]);
			}

			this.g.setColor(Color.white);
			this.g.setFont(new Font("Ariel", Font.BOLD, 12));
			this.g.drawString("Draws", 230, 420);
			this.g.drawString(players[0].getName(), stringPositon(players[0].getName(), 100, 4), 420);
			this.g.drawString(players[1].getName(), stringPositon(players[1].getName(), 400, 4), 420);
			this.g.setFont(new Font("Ariel", Font.BOLD, 20));
			this.g.drawString(players[0].getWins()+"", stringPositon(players[0].getWins()+"", 100, 5), 440);
			this.g.drawString(players[0].getDraws()+"", stringPositon(players[0].getDraws()+"", 250, 5), 440);
			this.g.drawString(players[1].getWins()+"", stringPositon(players[1].getWins()+"", 400, 5), 440);

			this.g.setFont(new Font("Ariel", Font.BOLD, 12));
			if(this.state != GameState.NewGame){
				String turn = players[currentplayer].getName()+"'s turn";
				this.g.drawString(turn, stringPositon(turn, 250, 3), 80);
			} else {
				String turn = "Click for new Game";
				this.g.drawString("Click for new Game", stringPositon(turn, 250, 3), 80);
			}
		}
	}
	
	public void clearGame(){
		this.changeState(GameState.Clear);
		this.turn = 1;
		board.clear();
	}
	
	private static int stringPositon(String s, int x, int size){
		return x-(s.length()*size);
	}
	
	public void initNewGame(int playerfirst){
		this.turn = 1;
		board.clear();
		this.players[0].clearScore();
		this.players[1].clearScore();
		firstplayer = playerfirst;
		if(playerfirst != 2){
			startingplayer = playerfirst;
			currentplayer = playerfirst;
		} else {
			Random r = new Random();
			startingplayer = r.nextInt(2);
			currentplayer = startingplayer;
		}
		
		if(players[currentplayer].isAI()){
			this.changeState(GameState.Running);
			Thread aithread = new Thread(this);
			aithread.start();
		} else {
			this.changeState(GameState.Turn);
		}
		
		//this.paint();
	}

	@Override
	public void run() {
		try {
			Thread.sleep(400);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.computerTurn();
	}
}
