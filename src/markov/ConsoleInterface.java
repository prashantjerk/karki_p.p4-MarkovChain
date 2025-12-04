// CS242 - Fall 2025
// @author Prashant Karki

package markov;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;


public class ConsoleInterface {

    public static void main(String[] args) throws IOException {
        // Validate command line arguments
        if (args.length < 4) {
            System.err.println("Error: Insufficient Arguments!!");
            System.exit(1);
        }

        try {
            // Parse command line arguments
            Path corpusPath = Path.of(args[0]);
            int seed = Integer.parseInt(args[1]);
            int n = Integer.parseInt(args[2]);
            int numSentences = Integer.parseInt(args[3]);

            // Use NGramSalad for all cases (including n=1)
            // This ensures corpus sentences are rejected
            generateWithNGrams(corpusPath, seed, n, numSentences);

        } catch (NumberFormatException e) {
            System.err.println("Error: Seed, n, and number of sentences must be integers");
            System.exit(1);
        } catch (InvalidInputException e) {
            System.err.println("Error: Invalid input to Markov chain: " + e.getMessage());
            System.exit(1);
        } catch (InsufficientMarkovChainException e) {
            System.err.println("Error: Insufficient Markov chain: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }

    // generates sentences using ngrams
    private static void generateWithNGrams(Path corpusPath, int seed, int n, int numSentences)
            throws IOException, InvalidInputException, InsufficientMarkovChainException {

        // Create the NGramSalad generator
        NGramSalad generator = new NGramSalad(n, seed);

        // Parse the corpus into sentences
        List<String> sentences = LexicalParser.breakFileIntoSentences(corpusPath);

        // Add each sentence's tokens to the Markov chain
        for (String sentence : sentences) {
            List<String> tokens = LexicalParser.tokenizeSentence(sentence);
            if (!tokens.isEmpty()) {
                generator.addOrderedTokens(tokens);
            }
        }

        // Generate and print the requested number of sentences
        for (int i = 0; i < numSentences; i++) {
            String generatedSentence = generator.generateSentence();
            System.out.println(generatedSentence);
        }
    }
}