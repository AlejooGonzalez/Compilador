import Exceptions.LexicalException;
import Lexical.LexicalAnalyzer;
import Lexical.Token;
import SourceManager.SourceManagerImplementation;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws IOException, LexicalException {
        boolean thereIsError = false;
        SourceManagerImplementation sourceManager = new SourceManagerImplementation();
        Token token = new Token("", "", 1);

        try {
            sourceManager.open(args[0]);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        LexicalAnalyzer lexer = new LexicalAnalyzer(sourceManager);

        do {
            try {
                token = lexer.nextToken();
                System.out.println("(" + token.getTokenType() + "," + token.getLexeme() + "," + token.getLineNumber() + ")");
            } catch (LexicalException e) {
                e.errorElegante();
                thereIsError = true;
            }
        } while (!Objects.equals(token.getTokenType(), "EOF"));

        if (!thereIsError) {
            System.out.println("[SinErrores]");
        }
    }
}
