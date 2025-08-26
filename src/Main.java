import Exceptions.LexicalException;
import Lexical.LexicalAnalyzer;
import Lexical.Token;
import SourceManager.SourceManagerImplementation;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, LexicalException {
        boolean hayError = false;
        SourceManagerImplementation sourceManager = new SourceManagerImplementation();
        try {
            sourceManager.open(args[0]);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        LexicalAnalyzer lexer = new LexicalAnalyzer(sourceManager);

        //falta el while
            try {
                Token token = lexer.proximoToken();
                System.out.print("(" + token.getTipoToken() + "," + token.getLexema() + "," + token.getNroLinea() + ")");
            } catch (LexicalException e) {
                e.errorElegante();
                hayError = true;
            }

            if(!hayError){
                System.out.println("[SinErrores]");
            }

    }
}
