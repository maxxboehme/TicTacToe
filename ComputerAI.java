/**
 * @author Maxx Boehme
 * @version 1
 *
 * Class used to make move of the computer AI.
 */

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class ComputerAI {
	
	private Random r;
	private AILevel level;
	
	ComputerAI(){
		this.r = new Random();
		this.level = AILevel.Hard;
	}
	
	public void setLevel(AILevel l){
		this.level = l;
	}
	
	public Point turn(Board b, Token t, int turn){
		Point p;
		if(this.level == AILevel.Easy){
			p = this.easy(b, t, turn);
		} else if(this.level == AILevel.Medium){
			p = this.medium(b,  t, turn);
		} else {
			p = this.hard(b, t, turn);
		}
		return p;
	}
	
	private Point easy(Board b, Token t, int turn){
        Point p = win(b, t);
		
		if(p==null){
			p = blockWin(b, t);
		}
		
		if(p == null){
			p = this.random(b, t);
		}
		System.out.println("Point: "+p.x+p.y);
		return p;
	}
	
	private Point medium(Board b, Token t, int turn){
        Point p = win(b, t);
		
		if(p==null){
			p = blockWin(b, t);
		}
		
		int choice = r.nextInt(2);
		System.out.println("********************\nr: "+choice);
		if(choice == 1){
			if(turn%2 == 0) {
				if(p == null && turn == 4){
					p = this.defenceDiagnalAttack(b, t);
				}

				if(p == null && turn == 4){
					p = defenceCornerChooser(b, t);
				}

				if(p == null){
					p = defenceMoves(b, t);
				}
			} else {
				if(p == null){
					p = this.chooseRandomCorner(b, t);
				}
			}
		}
		
		if(p == null){
			p = this.random(b, t);
		}
		System.out.println("Point: "+p.x+p.y);
		return p;
	}
	
	private Point hard(Board b, Token t, int turn){
        Point p = win(b, t);
		
		if(p==null){
			p = blockWin(b, t);
		}
		
		if(turn%2 == 0) {
			if(p == null && turn == 4){
				p = this.defenceDiagnalAttack(b, t);
			}

			if(p == null && turn == 4){
				p = defenceCornerChooser(b, t);
			}

			if(p == null){
				p = defenceMoves(b, t);
			}
		} else {
			if(p == null){
				p = this.chooseRandomCorner(b, t);
			}
		}
		
		if(p == null){
			p = this.random(b, t);
		}
		System.out.println("Point: "+p.x+p.y);
		return p;
	}
	
	private boolean equalsOpponit(Cell c, Token t){
		if(c.getType() != t && c.getType()!= Token.Empty){
			return true;
		} else {
			return false;
		}
	}
	
	private Point defenceDiagnalAttack(Board b, Token t){
		if(this.equalsOpponit(b.getCell(0, 0), t) && this.equalsOpponit(b.getCell(2,2), t) || 
				this.equalsOpponit(b.getCell(0, 2), t) && this.equalsOpponit(b.getCell(2,0), t)) {
			ArrayList<Point> opptions = new ArrayList<Point>();
			for(int k = -1; k < 2; k+=2){
				if(1+k >= 0 && 1+k < 3) {
					if(b.getCellType(1, 1+k) == Token.Empty){
						opptions.add(new Point(1, 1+k));
					}
					
					if(b.getCellType(1+k, 1) == Token.Empty){
						opptions.add(new Point(1+k, 1));
					}
				}
			}
			
			if(opptions.size() != 0) {
				return opptions.get(this.r.nextInt(opptions.size()));
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	private Point defenceCornerChooser(Board b, Token t){
		ArrayList<Point> opptions = new ArrayList<Point>();
		System.out.println("HERE");
		int sum;
		for(int i = 0; i < 3; i+=2){
			for(int j = 0; j < 3; j+=2){
				sum = 0;
				System.out.print("Corner("+i+j+"): ");
				if(b.getCellType(i, j) == Token.Empty){
					for(int k = -1; k < 2; k+=2){
						if(j+k >= 0 && j+k < 3) {
							System.out.print(i+", "+(j+k)+" :: ");
							if(b.getCellType(i, j+k) == t){
								sum--;
							} else if(b.getCellType(i, j+k) != Token.Empty){
								sum++;
							}
						}
					}
					
					for(int k = -1; k < 2; k+=2){
						if(i+k >= 0 && i+k < 3) {
							System.out.print((i+k)+", "+(j)+" :: ");
							if(b.getCellType(i+k, j) == t){
								sum--;
							} else if(b.getCellType(i+k, j) != Token.Empty){
								sum++;
							}
						}
					}
					if(sum == 2){
						opptions.add(new Point(i,j));
					}
				}
				System.out.println();
			}
		}
		
		if(opptions.size() != 0) {
			return opptions.get(this.r.nextInt(opptions.size()));
		} else {
			return null;
		}
	}
	
	private Point defenceMoves(Board b, Token t){
		if(b.getCellType(1, 1) == Token.Empty){
			return new Point(1, 1);
		} else {
			return this.chooseRandomCorner(b, t);
		}
	}
	
	private Point chooseRandomCorner(Board b, Token t){
		ArrayList<Point> opptions = new ArrayList<Point>();
		for(int i = 0; i < 3; i+=2){
			for(int j = 0; j < 3; j+=2){
				if(b.getCellType(i, j) == Token.Empty){
					opptions.add(new Point(i,j));
				}
			}
		}
		
		if(opptions.size() != 0) {
			return opptions.get(this.r.nextInt(opptions.size()));
		} else {
			return null;
		}
	}
	
	private Point win(Board b, Token t){
		ArrayList<Point> opptions = new ArrayList<Point>();
		int sum;
		Point emptyPosition;
		for(int i = 0; i < 3; i++){
			sum = 0;
			emptyPosition = null;
			for(int j = 0; j < 3; j++){
				Token celltype = b.getCellType(i, j);
				if(celltype == t){
					sum++;
				} else if(celltype != Token.Empty){
					sum--;
				} else {
					emptyPosition = new Point(i,j);
				}
			}
			if(sum == 2){
				opptions.add(emptyPosition);
			}
		}
		
		for(int i = 0; i < 3; i++){
			sum = 0;
			emptyPosition = null;
			for(int j = 0; j < 3; j++){
				Token celltype = b.getCellType(j, i);
				if(celltype == t){
					sum++;
				} else if(celltype != Token.Empty){
					sum--;
				} else {
					emptyPosition = new Point(j,i);
				}
			}
			if(sum == 2){
				opptions.add(emptyPosition);
			}
		}
		
		sum = 0;
		emptyPosition = null;
		for(int i = 0; i < 3; i++){
			Token celltype = b.getCellType(i, i);
			if(celltype == t){
				sum++;
			} else if(celltype != Token.Empty){
				sum--;
			} else {
				emptyPosition = new Point(i,i);
			}
		}
		if(sum == 2){
			opptions.add(emptyPosition);
		}
		
		sum = 0;
		emptyPosition = null;
		for(int i = 0; i < 3; i++){
			Token celltype = b.getCellType(i, 2-i);
			if(celltype == t){
				sum++;
			} else if(celltype != Token.Empty){
				sum--;
			} else {
				emptyPosition = new Point(i,2-i);
			}
		}
		if(sum == 2){
			opptions.add(emptyPosition);
		}
		
		if(opptions.isEmpty()){
			return null;
		} else {
			return opptions.get(this.r.nextInt(opptions.size()));
		}
	}
	
	Point blockWin(Board b, Token t) {
		ArrayList<Point> opptions = new ArrayList<Point>();
		int sum;
		Point emptyPosition;
		for(int i = 0; i < 3; i++){
			sum = 0;
			emptyPosition = null;
			for(int j = 0; j < 3; j++){
				Token celltype = b.getCellType(i, j);
				if(celltype == t){
					sum--;
				} else if(celltype != Token.Empty){
					sum++;
				} else {
					emptyPosition = new Point(i,j);
				}
			}
			if(sum == 2){
				opptions.add(emptyPosition);
			}
		}
		
		for(int i = 0; i < 3; i++){
			sum = 0;
			emptyPosition = null;
			for(int j = 0; j < 3; j++){
				Token celltype = b.getCellType(j, i);
				if(celltype == t){
					sum--;
				} else if(celltype != Token.Empty){
					sum++;
				} else {
					emptyPosition = new Point(j,i);
				}
			}
			if(sum == 2){
				opptions.add(emptyPosition);
			}
		}
		
		sum = 0;
		emptyPosition = null;
		for(int i = 0; i < 3; i++){
			Token celltype = b.getCellType(i, i);
			if(celltype == t){
				sum--;
			} else if(celltype != Token.Empty){
				sum++;
			} else {
				emptyPosition = new Point(i,i);
			}
		}
		if(sum == 2){
			opptions.add(emptyPosition);
		}
		
		sum = 0;
		emptyPosition = null;
		for(int i = 0; i < 3; i++){
			Token celltype = b.getCellType(i, 2-i);
			if(celltype == t){
				sum--;
			} else if(celltype != Token.Empty){
				sum++;
			} else {
				emptyPosition = new Point(i,2-i);
			}
		}
		if(sum == 2){
			opptions.add(emptyPosition);
		}
		
		if(opptions.isEmpty()){
			return null;
		} else {
			return opptions.get(this.r.nextInt(opptions.size()));
		}
	}
	
	Point random(Board b, Token t) {
		ArrayList<Point> opptions = new ArrayList<Point>();
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				if(b.getCellType(i, j) == Token.Empty){
					opptions.add(new Point(i, j));
				}
			}
		}
		
		return opptions.get(this.r.nextInt(opptions.size()));
	}

}
