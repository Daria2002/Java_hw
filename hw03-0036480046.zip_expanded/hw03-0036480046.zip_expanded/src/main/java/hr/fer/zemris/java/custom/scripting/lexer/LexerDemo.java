package hr.fer.zemris.java.custom.scripting.lexer;

public class LexerDemo {
	public static void main(String[] args) {
		LexerSmart l = new LexerSmart("Ovo je {$ime $}   test \\\\ i ovo isto \\{} a ovo nije {$nnj$} tu je te");
		
		for(int i = 0; i < 10; i++) {
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
