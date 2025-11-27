// CS242 - Fall 2025
// @author: Prashant Karki

package markov;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public abstract class Milestone2 {
//    args[0] = corpus file path
//    args[1] = seed for random number generator
//    args[2] = number of sentences to generate
    public static void main(String[] args) {
        // Validate command line arguments
        if (args.length < 3) {
            System.err.println("Invalid Number of Arguments");
            System.exit(1);
        }

        try {
            // Parse command line arguments
            Path corpusPath = Path.of(args[0]);
            int seed = Integer.parseInt(args[1]);
            int numSentences = Integer.parseInt(args[2]);

            // Create the UnigramSalad generator
            UnigramSalad generator = new UnigramSalad(seed);

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

        } catch (NumberFormatException e) {
            System.err.println("Error: Seed and number of sentences must be integers");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Error reading corpus file: " + e.getMessage());
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
}