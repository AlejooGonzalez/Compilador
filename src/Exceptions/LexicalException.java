package Exceptions;

import SourceManager.SourceManager;
import SourceManager.SourceManagerImplementation;

public class LexicalException extends Exception{
    private int nroColumna;
    private int nroFila;
    private String lexema;
    private final SourceManager sourceManager;

    public LexicalException(int nroColumna, int nroFila, String lexema, SourceManager sourceManager){
        this.nroColumna = nroColumna;
        this.nroFila = nroFila;
        this.lexema = lexema;
        this.sourceManager = sourceManager;
    }

    public void errorElegante(){
        String lineaActual = sourceManager.getCurrentLine();
        System.out.println("Error Léxico en linea "+nroFila+": "+lexema+" no es un símbolo valido\nDetalle: "+lineaActual+"         \n");
        for(int i = 0; i < nroColumna; i++){
            System.out.print(" ");
        }
        System.out.print("^");
        System.out.println("Error:"+lexema+"|"+nroFila);
    }
}
