package Lexical;

import java.io.IOException;

import SourceManager.SourceManagerImplementation;

public class LexicalAnalyzer {
    String lexema;
    char caracterActual;
    SourceManagerImplementation SourceManager;

    public Token proximoToken() throws IOException{
        lexema = "";
        return e0();
    }

    public void actualizarCaracterActual() throws IOException {
        caracterActual = SourceManager.getNextChar();
    }

    public Token e0() throws IOException{
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
        return null;
        }
    }
