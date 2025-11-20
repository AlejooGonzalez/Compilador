package Semantic;

import Exceptions.SemanticException;
import Exceptions.SyntacticException;
import Lexical.Token;
import Semantic.Ast.Sentences.BlockNode;
import Semantic.Types.PrimitiveType;
import Semantic.Types.ReferenceType;

import java.util.ArrayList;
import java.util.HashMap;

public class SymbolTable {
    private HashMap<String, ConcreteClass> classes;
    private Method currentMethod;
    private ConcreteClass currentClass;
    private Constructor currentConstructor;
    private BlockNode currentBlock;
    private ArrayList<String> instructionsList;
    private int stringNumber = 0;

    public SymbolTable() throws SyntacticException, SemanticException {
        classes = new HashMap<>();
        instructionsList = new ArrayList<>();

        //Object class
        ConcreteClass objectClass = new ConcreteClass(new Token("idClase", "Object", 0),new Token("null", "null", 0));
        Method debugPrint = new Method(new Token("idMetVar", "debugPrint", 0), new Token("pr_static", "static", 0), new PrimitiveType(new Token("pr_void", "void", 0)));
        debugPrint.addParameters(new Parameter(new Token("idMetVar", "i", 0), new PrimitiveType(new Token("pr_int", "int", 0))));
        debugPrint.setHasBlock(true);
        objectClass.addMethod(debugPrint);
        classes.put("Object", objectClass);

        //String class
        ConcreteClass stringClass = new ConcreteClass(new Token("idClase", "String", 0),new Token("null", "null", 0));
        classes.put("String", stringClass);

        //System class
        ConcreteClass systemClass = new ConcreteClass(new Token("idClase", "System", 0),new Token("null", "null", 0));

        Method read = new Method(new Token("idMetVar", "read", 0), new Token("pr_static", "static", 0), new PrimitiveType(new Token("pr_int", "int", 0)));
        read.setHasBlock(true);
        systemClass.addMethod(read);

        // static void printB(boolean b)
        Method printB = new Method(new Token("idMetVar", "printB", 0), new Token("pr_static", "static", 0), new PrimitiveType(new Token("pr_void", "void", 0)));
        printB.addParameters(new Parameter(new Token("idMetVar", "b", 0), new PrimitiveType(new Token("pr_boolean", "boolean", 0))));
        printB.setHasBlock(true);
        systemClass.addMethod(printB);

        // static void printC(char c)
        Method printC = new Method(new Token("idMetVar", "printC", 0), new Token("pr_static", "static", 0), new PrimitiveType(new Token("pr_void", "void", 0)));
        printC.addParameters(new Parameter(new Token("idMetVar", "c", 0), new PrimitiveType(new Token("pr_char", "char", 0))));
        printC.setHasBlock(true);
        systemClass.addMethod(printC);

        // static void printI(int i)
        Method printI = new Method(new Token("idMetVar", "printI", 0), new Token("pr_static", "static", 0), new PrimitiveType(new Token("pr_void", "void", 0)));
        printI.addParameters(new Parameter(new Token("idMetVar", "i", 0), new PrimitiveType(new Token("pr_int", "int", 0))));
        printI.setHasBlock(true);
        systemClass.addMethod(printI);

        // static void printS(String s)
        Method printS = new Method(new Token("idMetVar", "printS", 0), new Token("pr_static", "static", 0), new PrimitiveType(new Token("pr_void", "void", 0)));
        printS.addParameters(new Parameter(new Token("idMetVar", "s", 0), new ReferenceType(new Token("idClase", "String", 0))));
        printS.setHasBlock(true);
        systemClass.addMethod(printS);

        // static void println()
        Method println = new Method(new Token("idMetVar", "println", 0), new Token("pr_static", "static", 0), new PrimitiveType(new Token("pr_void", "void", 0)));
        println.setHasBlock(true);
        systemClass.addMethod(println);

        // static void printBln(boolean b)
        Method printBln = new Method(new Token("idMetVar", "printBln", 0), new Token("pr_static", "static", 0), new PrimitiveType(new Token("pr_void", "void", 0)));
        printBln.addParameters(new Parameter(new Token("idMetVar", "b", 0), new PrimitiveType(new Token("pr_boolean", "boolean", 0))));
        printBln.setHasBlock(true);
        systemClass.addMethod(printBln);

        // static void printCln(char c)
        Method printCln = new Method(new Token("idMetVar", "printCln", 0), new Token("pr_static", "static", 0), new PrimitiveType(new Token("pr_void", "void", 0)));
        printCln.addParameters(new Parameter(new Token("idMetVar", "c", 0), new PrimitiveType(new Token("pr_char", "char", 0))));
        printCln.setHasBlock(true);
        systemClass.addMethod(printCln);

        // static void printIln(int i)
        Method printIln = new Method(new Token("idMetVar", "printIln", 0), new Token("pr_static", "static", 0), new PrimitiveType(new Token("pr_void", "void", 0)));
        printIln.addParameters(new Parameter(new Token("idMetVar", "i", 0), new PrimitiveType(new Token("pr_int", "int", 0))));
        printIln.setHasBlock(true);
        systemClass.addMethod(printIln);

        // static void printSln(String s)
        Method printSln = new Method(new Token("idMetVar", "printSln", 0), new Token("pr_static", "static", 0), new PrimitiveType(new Token("pr_void", "void", 0)));
        printSln.addParameters(new Parameter(new Token("idMetVar", "s", 0), new ReferenceType(new Token("idClase", "String", 0))));
        printSln.setHasBlock(true);
        systemClass.addMethod(printSln);

        classes.put("System", systemClass);
    }

    public void setCurrentClass(ConcreteClass c) throws SemanticException {
            currentClass = c;
    }

    public ArrayList<String> getInstructionsList(){
        return instructionsList;
    }

    public HashMap<String,ConcreteClass> getClasses(){
        return classes;
    }

    public ConcreteClass getCurrentClass() {
        return currentClass;
    }

    public void setCurrentMethod(Method m) {
        currentMethod = m;
    }

    public Method getCurrentMethod() {
        return currentMethod;
    }

    public void setCurrentConstructor(Constructor cons) {
        currentConstructor = cons;
    }

    public BlockNode getCurrentBlock() {
        return currentBlock;
    }

    public void setCurrentBlock(BlockNode b) {
        currentBlock = b;
    }

    public void insertClass(ConcreteClass currentClass) throws SemanticException {
            if (classes.containsKey(currentClass.getLexeme())) {
                throw new SemanticException("La clase "+currentClass.getLexeme()+" ya existe", currentClass.getToken(), currentClass.getToken().getLineNumber());
            } else {
                classes.put(currentClass.getLexeme(), currentClass);
            }
    }

    public void itIsWellStated() throws SemanticException {
        for (ConcreteClass c : classes.values()) {
            c.itIsWellStated();
        }
    }

    public void sentenceCheck() throws SemanticException {
        for(ConcreteClass c : classes.values()){
            c.sentenceCheck();
        }
    }

    public void consolidate() throws SemanticException {
        for (ConcreteClass c : classes.values()) {
            c.consolidate();
        }
    }

    public ConcreteClass itIsAnExistingClass(Token className){
        ConcreteClass retorno = null;
        if(classes.get(className.getLexeme())!=null){
            retorno = classes.get(className.getLexeme());
        }
        return retorno;
    }

    public void generate() throws SemanticException {
        instructionsList.add(".CODE");
        instructionsList.add("PUSH Init_main");
        instructionsList.add("CALL");
        instructionsList.add("HALT");
        instructionsList.add("");

        instructionsList.add("simple_heap_init: RET 0 ; Retorna inmediatamente");
        instructionsList.add("");

        instructionsList.add("simple_malloc: LOADFP    ; Inicialización unidad");
        instructionsList.add("LOADSP");
        instructionsList.add("STOREFP ; Finaliza inicialización del RA");
        instructionsList.add("LOADHL	; hl");
        instructionsList.add("DUP	; hl");
        instructionsList.add("PUSH 1	; 1");
        instructionsList.add("ADD	; hl+1");
        instructionsList.add("STORE 4 ; Guarda el resultado (un puntero a la primer celda de la región de memoria)");
        instructionsList.add("LOAD 3	; Carga la cantidad de celdas a alojar (parámetro que debe ser positivo)");
        instructionsList.add("ADD");
        instructionsList.add("STOREHL ; Mueve el heap limit (hl). Expande el heap");
        instructionsList.add("STOREFP");
        instructionsList.add("RET 1	; Retorna eliminando el parámetro)");
        instructionsList.add("");

        generateSystemMethods();

        for(ConcreteClass classes:classes.values()){
            if(itIsNotADefaultClass(classes))
                classes.generate();
        }
    }

    public void generateSystemMethods(){

        instructionsList.add("Object_debugPrint: LOADFP ; Apila el valor del registro fp");
        instructionsList.add("LOADSP");
        instructionsList.add("STOREFP");
        instructionsList.add("LOAD 3");
        instructionsList.add("IPRINT");
        instructionsList.add("STOREFP");
        instructionsList.add("RET 1");
        instructionsList.add("");

        instructionsList.add("System_read: LOADFP");
        instructionsList.add("LOADFP");
        instructionsList.add("LOADSP");
        instructionsList.add("STOREFP");
        instructionsList.add("READ");
        instructionsList.add("STORE 3");
        instructionsList.add("STOREFP");
        instructionsList.add("RET 0");
        instructionsList.add("");

        instructionsList.add("System_printB: LOADFP");
        instructionsList.add("LOADSP");
        instructionsList.add("STOREFP");
        instructionsList.add("LOAD 3 ");
        instructionsList.add("BPRINT ");
        instructionsList.add("STOREFP");
        instructionsList.add("RET 1");
        instructionsList.add("");

        instructionsList.add("System_printC: LOADFP");
        instructionsList.add("LOADSP");
        instructionsList.add("STOREFP");
        instructionsList.add("LOAD 3 ");
        instructionsList.add("CPRINT ");
        instructionsList.add("STOREFP");
        instructionsList.add("RET 1");
        instructionsList.add("");

        instructionsList.add("System_printI: LOADFP");
        instructionsList.add("LOADSP");
        instructionsList.add("STOREFP");
        instructionsList.add("LOAD 3 ");
        instructionsList.add("IPRINT ");
        instructionsList.add("STOREFP");
        instructionsList.add("RET 1");
        instructionsList.add("");

        instructionsList.add("System_printS: LOADFP");
        instructionsList.add("LOADSP");
        instructionsList.add("STOREFP");
        instructionsList.add("LOAD 3 ");
        instructionsList.add("SPRINT ");
        instructionsList.add("STOREFP");
        instructionsList.add("RET 1");
        instructionsList.add("");

        instructionsList.add("System_println: LOADFP");
        instructionsList.add("LOADSP");
        instructionsList.add("STOREFP");
        instructionsList.add("PRNLN");
        instructionsList.add("STOREFP");
        instructionsList.add("RET 0");
        instructionsList.add("");

        instructionsList.add("System_printBln: LOADFP");
        instructionsList.add("LOADSP");
        instructionsList.add("STOREFP");
        instructionsList.add("LOAD 3 ");
        instructionsList.add("BPRINT ");
        instructionsList.add("PRNLN");
        instructionsList.add("STOREFP");
        instructionsList.add("RET 1");
        instructionsList.add("");

        instructionsList.add("System_printCln: LOADFP");
        instructionsList.add("LOADSP");
        instructionsList.add("STOREFP");
        instructionsList.add("LOAD 3 ");
        instructionsList.add("CPRINT ");
        instructionsList.add("PRNLN");
        instructionsList.add("STOREFP");
        instructionsList.add("RET 1");
        instructionsList.add("");

        instructionsList.add("System_printIln: LOADFP");
        instructionsList.add("LOADSP");
        instructionsList.add("STOREFP");
        instructionsList.add("LOAD 3 ");
        instructionsList.add("IPRINT ");
        instructionsList.add("PRNLN");
        instructionsList.add("STOREFP");
        instructionsList.add("RET 1");
        instructionsList.add("");

        instructionsList.add("System_printSln: LOADFP");
        instructionsList.add("LOADSP");
        instructionsList.add("STOREFP");
        instructionsList.add("LOAD 3 ");
        instructionsList.add("SPRINT ");
        instructionsList.add("PRNLN");
        instructionsList.add("STOREFP");
        instructionsList.add("RET 1");
        instructionsList.add("");
    }

    private boolean itIsNotADefaultClass(ConcreteClass classes){
        return !(classes.getToken().getLexeme().equals("String") || classes.getToken().getLexeme().equals("Object") || classes.getToken().getLexeme().equals("System"));
    }

    public int getNextStringNumber(){
        stringNumber++;
        return stringNumber;
    }
}
