package syllabus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;


public class Main {

	public static void main(String[] args) throws IOException {
		final String DICT_URL = "http://se.cpe.ku.ac.th/dictionary.txt";
		int syllablesCount = 0;
		int wordCount =0;
		double times = System.currentTimeMillis();
		URL url = new URL( DICT_URL );
		InputStream input = url.openStream( );
		BufferedReader reader =	new BufferedReader( new InputStreamReader( input ) );
		SimpleSyllableCounter counter = new SimpleSyllableCounter();
		String word;		
		while( (word = reader.readLine()) != null ) {
			syllablesCount += counter.countSyllables(word);
			wordCount++;
			System.out.println(word);
		}		
		input.close();
		System.out.printf("Reading words from %s\nCounted %d syllables in %d words\n	Elapsed time: %.5f sec"
				,url,syllablesCount,wordCount, (System.currentTimeMillis() - times)/1000);

	}
}