package test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import markov.LexicalParser;

/**
 * This class just tests whether your LexicalParser class will compile in my JUnit tests.
 * This class DOES NOT actually test your code.  It just ensures that it compiles.
 * @author Joe Meehean
 *
 */
public class LexicalParserCompilationStub {
	
	private static Path ALICE_FILE_PATH = Paths.get("resources", "alice.txt");

	public static void main(String[] args) {
		try {
			List<String> fileSentences = LexicalParser.breakFileIntoSentences(ALICE_FILE_PATH);
			for(String sentence : fileSentences) {
				List<String> tokens = LexicalParser.tokenizeSentence(sentence);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
