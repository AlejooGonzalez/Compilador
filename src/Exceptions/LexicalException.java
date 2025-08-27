package Exceptions;

import SourceManager.SourceManager;

public class LexicalException extends Exception{
    private final int columnNumber;
    private final int rowNumber;
    private final String lexeme;
    private final SourceManager sourceManager;

    public LexicalException(int columnNumber, int rowNumber, String lexeme, SourceManager sourceManager){
        this.columnNumber = columnNumber;
        this.rowNumber = rowNumber;
        this.lexeme = lexeme;
        this.sourceManager = sourceManager;
    }

    public void elegantError(){
        String actualRow = sourceManager.getCurrentLine();
        System.out.print("Error Léxico en linea "+ rowNumber +": "+ lexeme +" no es un símbolo valido\nDetalle: "+actualRow+"\n       ");
        for(int i = 0; i < columnNumber; i++){
            System.out.print(" ");
        }
        System.out.println("^");
        System.out.println("Error:"+ lexeme +"|"+ rowNumber +"\n");
    }
}
