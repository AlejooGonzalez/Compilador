import Exceptions.LexicalException;
import Exceptions.SyntacticException;
import Lexical.LexicalAnalyzer;
import SourceManager.SourceManagerImplementation;
import Syntactic.SyntacticAnalyzer;
import java.io.IOException;

public class MainSyntactic {
    public static void main(String[] args) {
        SourceManagerImplementation sourceManager = new SourceManagerImplementation();
        LexicalAnalyzer lexer =  null;
        boolean noMistakes = true;

        try {
            sourceManager.open(args[0]);
            lexer = new LexicalAnalyzer(sourceManager);
        } catch (IOException e) { throw new RuntimeException(e);}
        SyntacticAnalyzer syntacticAnalyzer = null;

        try {
            syntacticAnalyzer = new SyntacticAnalyzer(lexer);
        } catch (LexicalException | IOException e) { throw new RuntimeException(e); }

        try {
            syntacticAnalyzer.start();
        } catch (SyntacticException e) { e.getErrorMessage(); noMistakes = false;
        } catch (LexicalException | IOException ignored) { }

        if (noMistakes) {
            System.out.println("[SinErrores]");
        }

        try {
            sourceManager.close();
        } catch (IOException e) { throw new RuntimeException(e);}
    }
}
