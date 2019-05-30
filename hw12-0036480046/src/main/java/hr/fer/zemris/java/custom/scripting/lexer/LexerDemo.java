package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Demo program for lexer
 * @author Daria Matković
 *
 */
public class LexerDemo {
	
	/**
	 * This method is executed when program is run
	 * @param args takes no args
	 */
	public static void main(String[] args) {
	
		LexerSmart l = new LexerSmart("This is sample text.\n" + 
				"{$ FOR i 1 \"10\" 1 $}\n" + 
				"This is {$= i $}-th time this message is generated.\n" + 
				"{$END$}\n" + 
				"{$FOR i 0 10 2 $}\n" + 
				"sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\n" + 
				"{$ = i \"\\\"testZ\\\\aEscape\" $}" +
				"{$END$}");
		
		for(int i = 0; i < 50; i++) {
			TokenSmart x = l.nextToken();
			if(x == null) {
				System.out.println("x je null");
				break;
			}
			if(x.getType() == TokenSmartType.TAG_OPEN) {
				l.setState(LexerSmartState.TAG);
				//System.out.println("sad se podesio tag");
			}
			if(x.getType() == TokenSmartType.TAG_CLOSE) {
				l.setState(LexerSmartState.BASIC);
				//System.out.println("sad se podesio basic");
			}
			System.out.println(x.getValue());
		}
	}
}
