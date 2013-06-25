import java.awt.Color;


public class Cell {

	private Token t;
	private Color c;
	
	Cell(Token t, Color c) {
		this.t = t;
		this.c = c;
	}
	
	Cell(Color c) {
		this(Token.Empty, c);
	}
	
	Cell(Token t) {
		this(t, Color.black);
	}
	
	Cell() {
		this(Token.Empty, Color.black);
	}
	
	public void setType(Token t){
		this.t = t;
	}
	
	public Token getType(){
		return this.t;
	}
	
	public void setColor(Color c){
		this.c = c;
	}
	
	public Color getColor(){
		return this.c;
	}
	
	public void set(Token t, Color c){
		this.t = t;
		this.c = c;
	}
}
