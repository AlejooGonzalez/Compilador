package Exceptions;

import Lexical.Token;

public class SyntacticException extends Exception{

    String expectedToken;
    Token actualToken;

    public SyntacticException(String expectedToken, Token actualToken) {
        this.expectedToken = expectedToken;
        this.actualToken = actualToken;
    }

    public void printError() {
        System.out.println("Se esperaba un "+expectedToken+" pero se encontro "+actualToken.getLexeme());
        System.out.println();
        System.out.println("[Error:" + actualToken.getLexeme() + "|" + actualToken.getLineNumber() + "]");
    }
}