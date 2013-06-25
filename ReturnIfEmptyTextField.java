import javax.swing.JTextField;


public class ReturnIfEmptyTextField extends JTextField {
	
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
