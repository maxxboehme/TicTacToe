/*********************
 * Author: Maxx Boehme
 */

import java.awt.*;
import java.util.concurrent.locks.*;


public class Board {
	
	private final int ROWS = 3;
	private final int COLUMS = 3;
	private final int SIZE;
	private final int Cell_SIZE;
	private int[] pos;
	private Cell[][] board;
	private Graphics g;
	private Lock m;

	Board(Graphics g, int posx, int posy, int size) {
		board = new Cell[ROWS][COLUMS];
		for(int i = 0; i < ROWS; i++){
			for(int j = 0; j < COLUMS; j++){
				board[i][j] = new Cell();
			}
		}
		
		pos = new int[2];
		pos[0] = posx;
		pos[1] = posy;
		this.SIZE = size;
		this.Cell_SIZE = this.SIZE / this.ROWS;
		this.g = g;
		m = new ReentrantLock();
		this.clear();
	}
	
	public int[] getPosition(){
		return this.pos;
	}
	
	public int getCellSize() {
		return this.Cell_SIZE;
	}
	
	public Cell getCell(int x, int y){
		return board[x][y];
	}
	
	public Token getCellType(int x, int y){
		return board[x][y].getType();
	}
	
	/*public int[] hasWon(Player p, int x, int y){
		Token t = p.getType();
		int[] result = new int[4];
		int mid = (this.SIZE / this.ROWS) / 2;
		m.lock();
		if(board[x][0].getType() == t && board[x][1].getType() == t && board[x][2].getType() == t){
			g.setColor(Color.white);
			g.drawLine(mid+pos[0], x*this.Cell_SIZE + mid + pos[1], mid+pos[0]+(2 * this.Cell_SIZE), x*this.Cell_SIZE + mid + pos[1]);
			result[0] = result[2] = x;
			result[1] = 0;
			result[3] = 2;
		} else if(board[0][y].getType() == t && board[1][y].getType() == t && board[2][y].getType() == t){
			result[0] = 0;
			result[1] = result[3] = y;
			result[2] = 2;
		} else if(board[0][0].getType() == t && board[1][1].getType() == t && board[2][2].getType() == t){
			result[0] = result[1] = 0;
			result[1] = result[2] = 2;
		} else if(board[0][2].getType() == t && board[1][1].getType() == t && board[2][0].getType() == t){
			result = true;
		}
		m.unlock();
		return result;
	}*/
	
	public boolean hasWon(Player p, int x, int y){
		Token t = p.getType();
		boolean result =false;
		int mid = (this.SIZE / this.ROWS) / 2;
		m.lock();
		g.setColor(Color.white);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setStroke(new BasicStroke(10, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		int rowbegin = 0;
		int rowend = 0;
		int colbegin = 0;
		int colend = 0;
		if(board[x][0].getType() == t && board[x][1].getType() == t && board[x][2].getType() == t){
			rowbegin = rowend = x;
			colbegin = 0;
			colend = 2;
			result = true;
		} else if(board[0][y].getType() == t && board[1][y].getType() == t && board[2][y].getType() == t){
			colbegin = colend = y;
			rowbegin = 0;
			rowend = 2;
			result = true;
		} else if(board[0][0].getType() == t && board[1][1].getType() == t && board[2][2].getType() == t){
			rowbegin = colbegin = 0;
			rowend = colend = 2;
			result = true;
		} else if(board[0][2].getType() == t && board[1][1].getType() == t && board[2][0].getType() == t){
			rowend = colbegin = 2;
			rowbegin = colend = 0;
			result = true;
		}
		if(result){
			g2d.drawLine(colbegin*this.Cell_SIZE + mid + pos[0], rowbegin*this.Cell_SIZE + mid + pos[1],
					colend*this.Cell_SIZE + mid + pos[0], rowend*this.Cell_SIZE + mid + pos[1]);
		}
		m.unlock();
		return result;
	}
	
	public int[] hasWonArray(Player p, int x, int y){
		Token t = p.getType();
		int[] result = new int[4];
		m.lock();
		g.setColor(Color.white);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setStroke(new BasicStroke(10, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		if(board[x][0].getType() == t && board[x][1].getType() == t && board[x][2].getType() == t){
			result[0] = result[2] = x;
			result[1] = 0;
			result[3] = 2;
		} else if(board[0][y].getType() == t && board[1][y].getType() == t && board[2][y].getType() == t){
			result[1] = result[3] = y;
			result[0] = 0;
			result[2] = 2;
		} else if(board[0][0].getType() == t && board[1][1].getType() == t && board[2][2].getType() == t){
			result[0] = result[1] = 0;
			result[2] = result[3] = 2;
		} else if(board[0][2].getType() == t && board[1][1].getType() == t && board[2][0].getType() == t){
			result[2] = result[1] = 2;
			result[0] = result[3] = 0;
		} else {
			result = null;
		}
		m.unlock();
		return result;
	}
	
	public void clear(){
		m.lock();
		for(int i = 0; i < ROWS; i++){
			for(int j = 0; j < COLUMS; j++){
				this.board[i][j].setType(Token.Empty);
			}
		}
		m.unlock();
	}
	
	public boolean setCell(Player p, int x, int y){
		boolean result = false;
		m.lock();
		if(x >= 0 && x < ROWS && y >= 0 && y < COLUMS){
			board[x][y].set(p.getType(), p.getColor());
			System.out.println("------HELLO-------");
			result = true;
		}
		m.unlock();
		return result;
	}
	
	public boolean setCellifEmpty(Player p, int x, int y){
		boolean result = false;
		m.lock();
		if(x >= 0 && x < ROWS && y >= 0 && y < COLUMS && board[x][y].getType() == Token.Empty){
			board[x][y].set(p.getType(), p.getColor());
			result = true;
		}
		m.unlock();
		return result;
	}
	
	public String toString(){
		String result = "";
		for(int i = 0; i < ROWS; i++){
			for(int j = 0; j < COLUMS; j++){
				result = result.concat(board[i][j]+" ");
			}
			result = result.concat("\n");
		}
		return result;
	}
	
	public void paint()	{
		m.lock();
		g.setColor(Color.LIGHT_GRAY);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		for(int i = 1; i < ROWS; i++){
			int x = this.pos[0] + i * (SIZE/ ROWS);
			
			g.setColor(Color.white);
			g2d.setStroke(new BasicStroke(12, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			g2d.drawLine(x, this.pos[1], x, this.pos[1]+this.SIZE);
		}
		
		for(int i = 1; i < COLUMS; i++){
			int y = this.pos[1] + i *(SIZE/COLUMS);
			g.setColor(Color.white);
			g2d.setStroke(new BasicStroke(12, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			g2d.drawLine(this.pos[0], y, this.pos[0]+this.SIZE, y);

		}
		
		for(int i = 1; i < ROWS; i++){
			int x = this.pos[0] + i * (SIZE/ ROWS);

			g.setColor(Color.black);
			g2d.setStroke(new BasicStroke(8, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			g2d.drawLine(x, this.pos[1], x, this.pos[1]+this.SIZE);
			
		}
		
		for(int i = 1; i < COLUMS; i++){
			int y = this.pos[1] + i *(SIZE/COLUMS);
			
			g.setColor(Color.black);
			g2d.setStroke(new BasicStroke(8, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			g2d.drawLine(this.pos[0], y, this.pos[0]+this.SIZE, y);

		}
		for(int i = 1; i < ROWS; i++){
			int x = this.pos[0] + i * (SIZE/ ROWS);
			
			g.setColor(Color.LIGHT_GRAY);
			g2d.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			g2d.drawLine(x, this.pos[1], x, this.pos[1]+this.SIZE);
		}
		for(int i = 1; i < COLUMS; i++){
			int y = this.pos[1] + i *(SIZE/COLUMS);
			
			g.setColor(Color.LIGHT_GRAY);
			g2d.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			g2d.drawLine(this.pos[0], y, this.pos[0]+this.SIZE, y);
		}
		
		int cell_padding = 20;
		int cell_size = (SIZE/ROWS);
		for(int i = 0; i < ROWS; i++){
			for(int j = 0; j < COLUMS; j++){
				int x = j * cell_size + cell_padding +pos[1];
				int y = i * cell_size + cell_padding + pos[1];
				if(board[i][j].getType() == Token.X){
					g2d.setColor(board[i][j].getColor());
	                int x2 = (j + 1) * cell_size - cell_padding + pos[0];
	                int y2 = (i + 1) * cell_size - cell_padding + pos[1];
	                
	                g2d.setStroke(new BasicStroke(12, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
	                g2d.setColor(Color.white);
	                g2d.drawLine(x, y, x2, y2);
	                g2d.drawLine(x2, y, x, y2);
	                
	                g2d.setStroke(new BasicStroke(8, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
	                g2d.setColor(Color.black);
	                g2d.drawLine(x, y, x2, y2);
	                g2d.drawLine(x2, y, x, y2);
	                
	                g2d.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
	                g2d.setColor(board[i][j].getColor());
	                g2d.drawLine(x, y, x2, y2);
	                g2d.drawLine(x2, y, x, y2);
	                
				} else if(board[i][j].getType() == Token.O){
					g2d.setColor(board[i][j].getColor());
					int symbol_size = cell_size - cell_padding * 2;
					
					g2d.setStroke(new BasicStroke(12, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
	                g2d.setColor(Color.white);
	                g2d.drawOval(x, y, symbol_size, symbol_size);
	                
	                g2d.setStroke(new BasicStroke(8, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
	                g2d.setColor(Color.black);
	                g2d.drawOval(x, y, symbol_size, symbol_size);
	                
	                g2d.setColor(board[i][j].getColor());
	                g2d.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
	                g2d.drawOval(x, y, symbol_size, symbol_size);
				}
			}
		}
		m.unlock();
	}
}
