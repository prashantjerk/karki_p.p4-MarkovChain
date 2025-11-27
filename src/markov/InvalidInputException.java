// Fall 2025 - CS242
//@author: Prashant Karki

package markov;

// Exception that is thrown when the user attempts to add
// invalid inputs to the Markov chain.
public class InvalidInputException extends Exception {
    public InvalidInputException() {
        super();
    }

    public InvalidInputException(String message) {
        super(message);
    }
}