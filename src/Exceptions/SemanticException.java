package Exceptions;

import Lexical.Token;

    public class SemanticException extends Exception {
        private String msg;
        private Token actualToken;
        private int rowNumber;

        public SemanticException(String msg,Token actualToken, int rowNumber) {
            this.actualToken = actualToken;
            this.rowNumber = rowNumber;
            this.msg = msg;
        }

        public void getErrorMessage() {
            System.out.println("Error Semantico en linea "+rowNumber+": "+msg);
            System.out.println("[Error:" + actualToken.getLexeme() + "|" + rowNumber + "]");
        }
    }
