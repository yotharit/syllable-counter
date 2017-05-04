package syllabus;

public class SimpleSyllableCounter implements SyllableCounter {

	private enum State {START,CONSONANT,SINGLE_VOWEL,MULTI_VOWEL,HYPHEN,NONWORD}
	private final String VOWEL = "AEIOUaeiou";

	@Override
	public int countSyllables(String word) {
		int syllables = 0;
		char c = ' ';
		State state = State.START;
		for(int i = 0; i<word.length(); i++){
			boolean lastChar = (i == (word.length() - 1));
			c = word.charAt(i);
			if((c == '\'') || (c == ' ')) continue;
			switch(state){
			case START:
				if(isVowelOrY(c)){
					state = State.SINGLE_VOWEL;
					if(lastChar)
						syllables++;
				}
				else if(!isLetter(c) || isHyphen(c))
					state = State.NONWORD;
				else
					state = State.CONSONANT;
				break;

			case NONWORD:
				return 0;

			case SINGLE_VOWEL:
				if(isVowel(c)) {
					state = State.MULTI_VOWEL;
					if(lastChar)
						syllables++;
				}
				else if(isHyphen(c)){
					state = State.HYPHEN;
					syllables++;
				}		
				else if(isLetter(c) && !isVowel(c)) {
					state = State.CONSONANT;
					syllables++;
				}
				else if(isHyphen(c)){
					state = State.CONSONANT;
				}
				break;

			case CONSONANT:
				if(isVowelOrY(c)){
					state = State.SINGLE_VOWEL;
					if(lastChar)
						syllables++;
				}
				else if(isHyphen(c)){
					state = State.HYPHEN;
				}
				else if(isLetter(c)){
					state = State.CONSONANT;
				}
				else
					state = State.NONWORD;
				if(lastChar && (c == 'e' || c == 'E') && syllables > 0)
					syllables--;
				if(lastChar && (c == 'e' || c == 'E') && syllables == 0)
					syllables++;
				break;

			case MULTI_VOWEL:
				if(!isVowel(c) && isLetter(c)){
					state = State.CONSONANT;
					syllables++;
				}
				else if(isVowel(c)){
					state = State.MULTI_VOWEL;
					if(lastChar)
						syllables++;
				}
				else
					state = State.NONWORD;
				if(lastChar && (c == 'e' || c == 'E') && syllables > 0)
					syllables--;
				if(lastChar && (c == 'e' || c == 'E') && syllables == 0)
					syllables++;
				break;

			case HYPHEN:
				if(isVowel(c)){
					state = State.SINGLE_VOWEL;
					if(lastChar)
						syllables++;
				}
				else if(isLetter(c)){
					state = State.CONSONANT;
				}
				else if(isHyphen(c))
					state = State.HYPHEN;
				else
					state = State.NONWORD;
				if(lastChar && (c == 'e' || c == 'E') && syllables>0)
					syllables--;
				break;

			default:
				break;

			}
		}
		return syllables;
	}

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
