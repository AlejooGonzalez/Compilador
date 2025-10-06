package Exceptions;

import Lexical.Token;

    public class SemanticException extends Exception {
        String expectedToken;
        Token actualToken;

        public SemanticException(String expectedToken, Token actualToken) {
            this.expectedToken = expectedToken;
            this.actualToken = actualToken;
        }

        public void getErrorMessage() {
            System.out.println("Se esperaba "+expectedToken+" pero se encontro "+actualToken.getLexeme());
            System.out.println("\n[Error:" + actualToken.getLexeme() + "|" + actualToken.getLineNumber() + "]");
        }
    }
