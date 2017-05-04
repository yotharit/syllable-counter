package syllabus;

public abstract class State {
	public final String VOWEL = "AEIOUaeiou";
	public abstract void handleChar(char c);
	public void enterState( ) { /* default is to do nothing */ }
	public boolean isVowel(char c){
		return (VOWEL.contains(""+c));
	}

	public boolean isVowelOrY(char c){
		return (VOWEL.contains(""+c) || c == 'Y' || c == 'y' );
	}

	public boolean isLetter(char c){
		return Character.isLetter(c);
	}

	public boolean isHyphen(char c){
		return c == '-';
	}
}
