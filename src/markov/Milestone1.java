package markov;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static markov.LexicalParser.tokenizeSentence;
import static markov.LexicalParser.breakFileIntoSentences;

public class Milestone1 {
    public static void main(String[] args) {
        // Check if corpus file path is provided
        if (args.length < 1) {
            System.err.println("Error: java SentenceParser <corpus-file-path>");
            System.exit(1);
        }

        try {
            Path corpusPath = Path.of(args[0]); // get the path

            // call the static method to break the file into sentences
            List<String> sentences = breakFileIntoSentences(corpusPath);

            Set<String> tokens = new HashSet<>();

            // Process each sentence to extract tokens
            for (String sentence : sentences) {
                tokens.addAll(tokenizeSentence(sentence));
            }

            // print only the counts, not the actual sentences/tokens
            System.out.println(sentences.size());
            System.out.println(tokens.size());

        } catch (IOException e) {
            System.err.println("Error reading corpus file: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Error processing corpus: " + e.getMessage());
            System.exit(1);
        }
    }
}