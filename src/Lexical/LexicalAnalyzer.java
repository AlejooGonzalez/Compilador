package Lexical;

import SourceManager.SoruceManagerImplementation;

public class LexicalAnalyzer {
    String lexema;
    char caracterActual;
    SoruceManagerImplementation SourceManager

    public Token proximoToken(){
        lexema = "";
        return e0();
    }

    public void actualizarCaracterActual() {
        caracterActual = SourceManager.getNextChar();
    }

    public Token e0(){
        switch(caracterActual){

            case ' ':
            case '\n':
            case '\t': {
                actualizarCaracterActual();
                return e0();
            }

            case '>':{

                }
            }
        }
    }
}