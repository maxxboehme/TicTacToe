/**
 * @author Maxx Boehme
 * @version 1
 *
 * Class used to group Player information.
 */

import java.awt.Color;

public class Player {
	
	private ComputerAI ai;
	private boolean isAI;
	private Token type;
	private int wins;
	private int draws;
	private int losses;
	private String name;
	private Color c;
	
	Player(Token t, boolean ai, Color c, String name){
		type = t;
		wins = draws = losses = 0;
		this.c = c;
		this.isAI = ai;
		this.ai = new ComputerAI();
		this.name = name;
	}
	Player(Token t, Color c, String name){
		this(t, false, c, name);
	}
	
	Player(Token t, Color c){
		this(t, false, c, null);
	}
	
	Player(){
		this(Token.Empty, false, null, null);
	}
	
	public void setColor(Color c){
		this.c = c;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public Color getColor(){
		return this.c;
	}

	public Token getType(){
		return type;
	}
	
	public void setType(Token t){
		type = t;
	}
	
	public void setAILevel(AILevel l){
		this.ai.setLevel(l);
	}
	public void setAI(boolean ai){
		this.isAI = ai;
	}
	
	public boolean isAI(){
		return this.isAI;
	}
	
	public ComputerAI getAI(){
		return ai;
	}
	
	public int getWins(){
		return wins;
	}
	
	public int getLosses(){
		return losses;
	}
	
	public int getDraws(){
		return draws;
	}
	
	public void win(){
		wins++;
	}
	
	public void lost(){
		losses++;
	}
	
	public void draw(){
		draws++;
	}
	
	public void clearScore() {
		this.wins = 0;
		this.losses = 0;
		this.draws = 0;
	}
}