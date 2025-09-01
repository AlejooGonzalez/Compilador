package Exceptions;

import SourceManager.SourceManager;

public class LexicalException extends Exception{
    private final int columnNumber;
    private final int rowNumber;
    private final String lexeme;
    private final SourceManager sourceManager;
    private final String errorMsg;

    public LexicalException(int columnNumber, int rowNumber, String lexeme, SourceManager sourceManager, String errorMsg){
        this.columnNumber = columnNumber;
        this.rowNumber = rowNumber;
        this.lexeme = lexeme;
        this.sourceManager = sourceManager;
        this.errorMsg = errorMsg;
    }

    public void elegantError(){
        String actualRow = sourceManager.getCurrentLine();
        System.out.print("Error Léxico en linea "+ rowNumber +", columna "+columnNumber+": "+ lexeme +" - "+errorMsg+"\nDetalle: "+actualRow+"\n        ");
        for(int i = 0; i < columnNumber; i++){
            System.out.print(" ");
        }
        System.out.println("^");
        System.out.println("[Error:"+ lexeme +"|"+ rowNumber +"]\n");
    }
}
