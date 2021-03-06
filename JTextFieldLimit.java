/**
 * @author Maxx Boehme
 * @version 1
 *
 * Class used to limit the length of strings in a JTextField
 */

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class JTextFieldLimit extends PlainDocument {
	private static final long serialVersionUID = -5920830285791732021L;
	private int limit;
	
	JTextFieldLimit(int limit) {
		super();
		this.limit = limit;
	}

	JTextFieldLimit(int limit, boolean upper) {
		super();
		this.limit = limit;
	}

	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		if (str == null) {
			return;
		}
		
		if ((getLength() + str.length()) <= limit) {
			super.insertString(offset, str, attr);
		}
	}
}