// CS242 - Fall 2025
// @author Prashant Karki

package markov;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class LexicalParser {
    // converts file into sentences
    public static List<String> breakFileIntoSentences(Path path) throws IOException {
        // try to see if the path exists
        List<String> sentences = new ArrayList<>();

        // Read the entire file into a string
        String content = Files.readString(path);

        Pattern sentencePattern = Pattern.compile("[^.?!]+[.?!]");
        Matcher matcher = sentencePattern.matcher(content);

        while (matcher.find()) {
            String sentence = matcher.group();
            if (!tokenizeSentence(sentence).isEmpty()) {
                sentences.add(sentence);
            }        }

        return sentences;
    }

    // returns the list of tokens in the given sentence `input`
    public static List<String> tokenizeSentence(String input) {
        List<String> tokens = new ArrayList<>();

        // regex pattern to match tokens:
        Pattern tokenPattern = Pattern.compile("\\S*\\w\\S*");
        Matcher matcher = tokenPattern.matcher(input);

        // Find all tokens
        while (matcher.find()) {
            tokens.add(matcher.group());
        }

        return tokens;
    }
}
