// CS242 - Fall 2025
// @author: Prashant Karki

package markov;

import java.util.*;

// uses a Markov chain of unigrams/tokens to generate sentences
// in the style of a given corpus
public class UnigramSalad {

    // Random number generator for selecting next tokens
    private Random random;

    // The Markov chain: maps from a token to a list of tokens that follow it
    // Key: current token (or special START marker)
    // Value: list of all tokens that have followed this token in the corpus
    private Map<String, List<String>> markovChain;

    // Special marker for the start of a sentence
    private static final String START = "<START>";

    // constructs a unigram Markov chain text generator
    public UnigramSalad(int seed) {
        this.random = new Random(seed);
        this.markovChain = new HashMap<>();
        // Initialize the start node
        this.markovChain.put(START, new ArrayList<>());
    }

    // Adds the ordered collection of tokens/grams to the Markov chain.
    public void addOrderedTokens(Collection<String> tokens) throws InvalidInputException {
        // list must contain at least one token.
        if (tokens == null || tokens.isEmpty()) {
            throw new InvalidInputException("Token list must contain at least one token");
        }

        // Convert to list for easy indexing
        List<String> tokenList = new ArrayList<>(tokens);

        // Add edge from START to the first token
        markovChain.get(START).add(tokenList.get(0));

        // Add edges between consecutive tokens
        for (int i = 0; i < tokenList.size() - 1; i++) {
            String currentToken = tokenList.get(i);
            String nextToken = tokenList.get(i + 1);

            // Add current token to chain if not present
            if (!markovChain.containsKey(currentToken)) {
                markovChain.put(currentToken, new ArrayList<>());
            }

            // Add the next token as a successor
            markovChain.get(currentToken).add(nextToken);
        }

        // The last token has no successors, but needs to be in the map
        String lastToken = tokenList.get(tokenList.size() - 1);
        if (!markovChain.containsKey(lastToken)) {
            markovChain.put(lastToken, new ArrayList<>());
        }
    }

    // The method traverses the unigram/token Markov chain to generate a sentence
    // The sentence generated may also appear in the corpus
    public String generateSentence() throws InsufficientMarkovChainException {
        // Check if chain has any content
        if (markovChain.get(START).isEmpty()) {
            throw new InsufficientMarkovChainException("Markov chain is empty");
        }

        StringBuilder sentence = new StringBuilder();
        String currentToken = START;
        boolean hasSuccessors = true;

        // Traverse the chain until we reach a token with no successors
        while (hasSuccessors) {
            List<String> successors = markovChain.get(currentToken);

            // Check if there are successors
            if (successors.isEmpty()) {
                hasSuccessors = false;
            } else {
                // Select next token using lottery algorithm
                String nextToken = selectRandomToken(successors);

                // Add token to sentence
                if (!sentence.isEmpty()) {
                    sentence.append(" ");
                }
                sentence.append(nextToken);

                // Move to next token
                currentToken = nextToken;
            }
        }

        return sentence.toString();
    }

    // Selects a random token from the list using the lottery algorithm.
    // Each token has equal probability in unigram model, but we track
    // frequency by having duplicates in the list.
    private String selectRandomToken(List<String> tokens) {
        // Sort the tokens alphabetically for deterministic lottery
        List<String> sortedTokens = new ArrayList<>(tokens);
        Collections.sort(sortedTokens);

        // Count occurrences of each unique token (edge weights)
        Map<String, Integer> weights = new LinkedHashMap<>();
        for (String token : sortedTokens) {
            weights.put(token, weights.getOrDefault(token, 0) + 1);
        }

        // Calculate total weight
        int totalWeight = sortedTokens.size();

        // Generate random lottery ticket
        int lotteryTicket = random.nextInt(totalWeight);

        // Find the winner using the lottery algorithm
        int currentPosition = 0;
        String winner = null;
        for (Map.Entry<String, Integer> entry : weights.entrySet()) {
            currentPosition += entry.getValue();
            if (lotteryTicket < currentPosition) {
                winner = entry.getKey();
                break;
            }
        }

        return winner;
    }
}