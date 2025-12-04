// CS242 - Fall 2025
// @author Prashant Karki

package markov;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// this immutable class represents ngram
public class NGram implements Comparable<NGram> {

    public static final char DELIMITER = '|';   //  used to separate ngrams in string
    private final List<String> grams;   // the list of grams/tokens in this ngram
    private final int n;    // max size of ngram

    // construct empty ngram with a given n
    public NGram(int n) {
        this.n = n;
        this.grams = new ArrayList<>();
    }

    // for creating ngram with exisiting data
    private NGram(int n, List<String> grams) {
        this.n = n;
        this.grams = new ArrayList<>(grams);
    }

    // preserves the size of ngram and adds a new gram to the end
    public NGram add(String gram) {
        NGram result = null;
        List<String> newGrams = new ArrayList<>(this.grams);
        newGrams.add(gram);
        if (newGrams.size() > n) {
            newGrams.remove(0);
        }
        result = new NGram(n, newGrams);
        return result;
    }

    // number of grams in the ngram
    public int size() {
        int result = grams.size();
        return result;
    }

    // a list of tokens/grams stored in this ngram in order
    public List<String> extractTokens() {
        List<String> result = new ArrayList<>(grams);
        return result;
    }

    // returns the last gram in the ngram
    public String last() {
        String result = null;
        if (!grams.isEmpty()) {
            result = grams.get(grams.size() - 1);
        }
        return result;
    }

    @Override
    public boolean equals(Object otherObject) {
        boolean result = false;
        if (this == otherObject) {
            result = true;
        } else {
            if (otherObject != null && getClass() == otherObject.getClass()) {
                NGram other = (NGram) otherObject;
                if (n == other.n && grams.equals(other.grams)) {
                    result = true;
                }
            }
        }
        return result;
    }

    // compare the ngram alphabetically
    @Override
    public int compareTo(NGram o) {
        int result = 0;
        int minSize = Math.min(this.grams.size(), o.grams.size());
        int i = 0;
        while (i < minSize && result == 0) {
            int comparison = this.grams.get(i).compareTo(o.grams.get(i));
            if (comparison != 0) {
                result = comparison;
            }
            i++;
        }
        if (result == 0) {
            result = Integer.compare(this.grams.size(), o.grams.size());
        }
        return result;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(n, grams);
        return result;
    }

    // creates string representation of ngrams
    @Override
    public String toString() {
        String result = "";
        if (!grams.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            int i = 0;
            while (i < grams.size()) {
                if (i > 0) {
                    sb.append(DELIMITER);
                }
                sb.append(grams.get(i));
                i++;
            }
            result = sb.toString();
        }
        return result;
    }
}