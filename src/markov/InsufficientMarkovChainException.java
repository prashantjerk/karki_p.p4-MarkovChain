// Fall 2025 - CS242
//@author: Prashant Karki

package markov;

// Exception that is thrown when the user attempts an operation
// that requires a more robust Markov chain.
public class InsufficientMarkovChainException extends Exception {
    public InsufficientMarkovChainException() {
        super();
    }

    public InsufficientMarkovChainException(String message) {
        super(message);
    }
}