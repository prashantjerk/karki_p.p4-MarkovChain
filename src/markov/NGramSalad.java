// CS242 - Fall 2025
// @author Prashant Karki

package markov;

import java.util.*;

// this class uses a markov chain of ngrams to generate senteces
public class NGramSalad {

    private final Random random;  // for selecting next tokens
    private int n;      // n in ngram
    private Map<NGram, List<String>> markovChain;
    private NGram startNGram;   // special empty ngram for the start of sentences
    private Set<String> corpusSentences;    // store all sentences from corpus to check for duplis

    // constructs the generator
    public NGramSalad(int n, int seed) {
        this.n = n;
        this.random = new Random(seed);
        this.markovChain = new HashMap<>();
        this.startNGram = new NGram(n);
        this.corpusSentences = new HashSet<>();
        this.markovChain.put(startNGram, new ArrayList<>());
    }

    //Adds the ordered collection of tokens/grams to the NGramSalad Markov chain
    public void addOrderedTokens(Collection<String> tokens) throws InvalidInputException {
        if (tokens == null || tokens.isEmpty()) {
            throw new InvalidInputException("Token list must contain at least one token");
        }

        String sentence = String.join(" ", tokens);
        corpusSentences.add(sentence);
        List<String> tokenList = new ArrayList<>(tokens);
        NGram currentNGram = startNGram;

        for (String token : tokenList) {
            if (!markovChain.containsKey(currentNGram)) {
                markovChain.put(currentNGram, new ArrayList<>());
            }
            markovChain.get(currentNGram).add(token);
            currentNGram = currentNGram.add(token);
        }

        if (!markovChain.containsKey(currentNGram)) {
            markovChain.put(currentNGram, new ArrayList<>());
        }
    }

    // the method traverses the ngram chain to generate a sentence
    // if a sentence generated was in the corpus, it discards it and tries to generate a new sentence
    public String generateSentence() throws InsufficientMarkovChainException {
        String result = null;
        if (markovChain.get(startNGram).isEmpty()) {
            throw new InsufficientMarkovChainException("Markov chain is empty");
        }

        String generatedSentence = null;
        int attempts = 0;
        int maxAttempts = 100;
        boolean foundNonCorpusSentence = false;

        while (!foundNonCorpusSentence && attempts < maxAttempts) {
            generatedSentence = generateOneSentence();
            if (!corpusSentences.contains(generatedSentence)) {
                foundNonCorpusSentence = true;
            }
            attempts++;
        }

        if (!foundNonCorpusSentence) {
            throw new InsufficientMarkovChainException("Could not generate a non-corpus sentence after " + maxAttempts + " attempts");
        }

        result = generatedSentence;
        return result;
    }

    // generates single sentence by traversing markov chain
    private String generateOneSentence() {
        String result = null;
        List<String> tokens = new ArrayList<>();
        NGram currentNGram = startNGram;
        boolean hasSuccessors = true;
        while (hasSuccessors) {
            List<String> successors = markovChain.get(currentNGram);
            if (successors.isEmpty()) {
                hasSuccessors = false;
            } else {
                String nextToken = selectRandomToken(successors);
                tokens.add(nextToken);
                currentNGram = currentNGram.add(nextToken);
            }
        }
        result = String.join(" ", tokens);
        return result;
    }

    // selects a random token from the list using the lottery algo
    private String selectRandomToken(List<String> tokens) {
        String result = null;
        List<String> sortedTokens = new ArrayList<>(tokens);
        Collections.sort(sortedTokens);
        Map<String, Integer> weights = new LinkedHashMap<>();
        for (String token : sortedTokens) {
            weights.put(token, weights.getOrDefault(token, 0) + 1);
        }
        int totalWeight = sortedTokens.size();
        int lotteryTicket = random.nextInt(totalWeight);
        int currentPosition = 0;
        String winner = null;
        for (Map.Entry<String, Integer> entry : weights.entrySet()) {
            currentPosition += entry.getValue();
            if (lotteryTicket < currentPosition && winner == null) {
                winner = entry.getKey();
            }
        }
        result = winner;
        return result;
    }
}