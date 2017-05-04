package syllabus;

public class WordCounter implements SyllableCounter {
	private final State START = new StartState( );
	private final State SINGLE_VOWEL = new SingleVowelState( );
	private final State CONSONANT = new ConsonantState();
	private final State MULTI_VOWEL = new MultiVowelState();
	private final State NONWORD = new NonWordState();
	private final State HYPHEN = new HyphenState();
	private State state = START;

	private int syllables = 0;
	private boolean lastChar;
	char c = ' ';


	public int countSyllables(String word) {
		for(int i = 0; i<word.length(); i++){
			lastChar = (i == (word.length() - 1));
			c = word.charAt(i);
			if(c == '\'') continue;
			state.handleChar(c);
		}
		return syllables;
	}

	/** change to a new state */
	public void setState( State newstate ) {
		this.state = newstate;
	}

	class StartState extends State {

		@Override
		public void handleChar(char c) {
			if(isVowelOrY(c)){
				if(lastChar)
					syllables++;
				setState(SINGLE_VOWEL);
			}
			else if(!isLetter(c) || isHyphen(c))
				setState(NONWORD);
			else
				setState(CONSONANT);
		}
	}

	class SingleVowelState extends State {

		@Override
		public void handleChar(char c) {
			if(isVowel(c)) {
				if(lastChar)
					syllables++;
				setState(MULTI_VOWEL);
			}
			else if(isHyphen(c)){
				syllables++;

				setState(HYPHEN);
			}		
			else if(isLetter(c) && !isVowel(c)) {
				syllables++;
				setState(CONSONANT);
			}
			else if(isHyphen(c)){
				setState(CONSONANT);
			}
		}

	}

	class MultiVowelState extends State {

		@Override
		public void handleChar(char c) {
			if(!isVowel(c) && isLetter(c)){
				syllables++;
				setState(CONSONANT);
			}
			else if(isVowel(c)){
				if(lastChar)
					syllables++;
				setState(MULTI_VOWEL);	
			}
			else
				setState(NONWORD);
			if(lastChar && (c == 'e' || c == 'E') && syllables > 0)
				syllables--;
			if(lastChar && (c == 'e' || c == 'E') && syllables == 0)
				syllables++;

		}

	}

	class HyphenState extends State {

		@Override
		public void handleChar(char c) {
			if(isVowel(c)){
				if(lastChar)
					syllables++;
				setState(SINGLE_VOWEL);
			}
			else if(isLetter(c)){
				setState(CONSONANT);
			}
			else if(isHyphen(c))
				setState(HYPHEN);
			else
				setState(NONWORD);
			if(lastChar && (c == 'e' || c == 'E') && syllables>0)
				syllables--;
		}

	}

	class ConsonantState extends State {

		@Override
		public void handleChar(char c) {
			if(isVowelOrY(c)){
				if(lastChar)
					syllables++;
				setState(SINGLE_VOWEL);
			}
			else if(isHyphen(c)){
				setState(HYPHEN);
			}
			else if(isLetter(c)){
				setState(CONSONANT);
			}
			else
				setState(NONWORD);
			if(lastChar && (c == 'e' || c == 'E') && syllables > 0)
				syllables--;
			if(lastChar && (c == 'e' || c == 'E') && syllables == 0)
				syllables++;
		}
	}

	class NonWordState extends State {

		@Override
		public void handleChar(char c) {
			syllables = 0;
		}
	}

} 