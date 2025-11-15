package Main;

import Exceptions.LexicalException;
import Exceptions.SemanticException;
import Exceptions.SyntacticException;
import Lexical.LexicalAnalyzer;
import Semantic.SymbolTable;
import SourceManager.SourceManagerImplementation;
import Syntactic.SyntacticAnalyzer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainSemantic {
    public static SymbolTable ST;
    static String outputFileName;

    public static void main(String[] args)  {
        SourceManagerImplementation sourceManager = new SourceManagerImplementation();
        LexicalAnalyzer lexer =  null;
        boolean noMistakes = true;

        try {
            ST = new SymbolTable();
        } catch (SyntacticException | SemanticException e) { throw new RuntimeException(e);}

        try {
            sourceManager.open(args[0]);
            outputFileName = args[1];
            lexer = new LexicalAnalyzer(sourceManager);
        } catch (IOException e) { throw new RuntimeException(e);}

        SyntacticAnalyzer syntacticAnalyzer = null;

        try {
            syntacticAnalyzer = new SyntacticAnalyzer(lexer);
        } catch (LexicalException | IOException e) { throw new RuntimeException(e); }

        try {
            syntacticAnalyzer.start();
        } catch (SyntacticException e) { e.getErrorMessage(); noMistakes = false;
        } catch (LexicalException | IOException ignored) {
        } catch (SemanticException e) {
            e.getErrorMessage();
            noMistakes = false;
        }

        if (noMistakes) {
            try {
                ST.itIsWellStated();
                ST.consolidate();
                ST.sentenceCheck();
                generate(outputFileName);
            } catch (SemanticException e) {
                e.getErrorMessage();
                noMistakes = false;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (noMistakes) {
            System.out.println("[SinErrores]");
        }

        try {
            sourceManager.close();
        } catch (IOException e) { throw new RuntimeException(e);}
    }

    private static void generate(String outputFileName) throws IOException, SemanticException {
        File file;
        FileWriter writer;
        BufferedWriter bufferedWriter;
        ST.generate();

        try{
            if(outputFileName == null){
                file = new File("Output_File.txt");
            } else {
                file = new File(outputFileName);
            }
            writer = new FileWriter(file);
            bufferedWriter = new BufferedWriter(writer);

            for(String instruction : ST.getInstructionsList()){
                writer.write(instruction);
                writer.write("\n");
            }
            writer.close();
            bufferedWriter.close();
        } catch (Exception e){
            System.out.println("Error al generar el archivo de salida");
        }
    }
}
