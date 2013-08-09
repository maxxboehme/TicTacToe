/**
 * @author Maxx Boehme
 * @version 1
 *
 * Class used to make a JTextField return a specific item as a default
 * if user does not enter any information
 */

import javax.swing.JTextField;

public class ReturnIfEmptyTextField extends JTextField {
	
	private static final long serialVersionUID = -5658937850735390568L;
	private String returnifEmpty;

	ReturnIfEmptyTextField(String s){
		this.returnifEmpty = s;
	}
	
	public String getText(){
		if(super.getText().length() == 0){
			System.out.println(this.returnifEmpty);
			return this.returnifEmpty;
		}	
		return super.getText();	
	}
}
