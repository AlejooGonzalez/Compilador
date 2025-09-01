import Exceptions.LexicalException;
import Lexical.LexicalAnalyzer;
import Lexical.Token;
import SourceManager.SourceManagerImplementation;
import java.io.IOException;
import java.util.Objects;

public class Main {
    public static void main(String[] args){
        boolean thereIsError = false;
        SourceManagerImplementation sourceManager = new SourceManagerImplementation();
        Token token = new Token("", "", 1);
        LexicalAnalyzer lexer = null;

        try {
            sourceManager.open(args[0]);
            lexer = new LexicalAnalyzer(sourceManager);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        do {
            try {
                token = lexer.nextToken();
                System.out.println("(" + token.getTokenType() + "," + token.getLexeme() + "," + token.getLineNumber() + ")");
            } catch (LexicalException e) {
                e.elegantError();
                thereIsError = true;
            }
        } while (!Objects.equals(token.getTokenType(), "EOF"));

        if (!thereIsError) {
            System.out.println("[SinErrores]");
        }
    }
}
