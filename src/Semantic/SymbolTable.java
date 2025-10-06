package Semantic;

import Exceptions.SemanticException;
import Exceptions.SyntacticException;
import Lexical.Token;

import java.util.HashMap;

public class SymbolTable {
     public static HashMap<String, ConcreteClass> classes;
     private static Method currentMethod;
     private static ConcreteClass currentClass;
     private static Constructor currentConstructor;

    public SymbolTable() throws SyntacticException, SemanticException {
        classes = new HashMap<>();

        //Object class
        ConcreteClass objectClass = new ConcreteClass(new Token("idClase","Object",0));
        Method debugPrint = new Method(new Token("idMetVar","debugPrint",0),new Token("pr_static","static",0), new PrimitiveType(new Token("pr_void", "void", 0)));
        debugPrint.addParameters(new Parameter(new Token("idMetVar","i",0), new PrimitiveType(new Token("pr_int","int",0))));
        objectClass.addMethod(debugPrint);
        classes.put("Object",objectClass);

        //String class
        ConcreteClass stringClass = new ConcreteClass(new Token("idClase", "String", 0));
        classes.put("String", stringClass);

        //System class
        ConcreteClass systemClass = new ConcreteClass(new Token("idClase", "System", 0));

        Method read = new Method(new Token("idMetVar", "read", 0), new Token("pr_static", "static", 0), new PrimitiveType(new Token("pr_int", "int", 0)));
        systemClass.addMethod(read);

        // static void printB(boolean b)
        Method printB = new Method(new Token("idMetVar", "printB", 0), new Token("pr_static", "static", 0), new PrimitiveType(new Token("pr_void", "void", 0)));
        printB.addParameters(new Parameter(new Token("idMetVar", "b", 0), new PrimitiveType(new Token("pr_boolean", "boolean", 0))));
        systemClass.addMethod(printB);

        // static void printC(char c)
        Method printC = new Method(new Token("idMetVar", "printC", 0), new Token("pr_static", "static", 0), new PrimitiveType(new Token("pr_void", "void", 0)));
        printC.addParameters(new Parameter(new Token("idMetVar", "c", 0),new PrimitiveType(new Token("pr_char", "char", 0))));
        systemClass.addMethod(printC);

        // static void printI(int i)
        Method printI = new Method(new Token("idMetVar", "printI", 0), new Token("pr_static", "static", 0), new PrimitiveType(new Token("pr_void", "void", 0)));
        printI.addParameters(new Parameter(new Token("idMetVar", "i", 0), new PrimitiveType(new Token("pr_int", "int", 0))));
        systemClass.addMethod(printI);

        // static void printS(String s)
        Method printS = new Method(new Token("idMetVar", "printS", 0), new Token("pr_static", "static", 0), new PrimitiveType(new Token("pr_void", "void", 0)));
        printS.addParameters(new Parameter(new Token("idMetVar", "s", 0), new ReferenceType(new Token("idClase", "String", 0))));
        systemClass.addMethod(printS);

        // static void println()
        Method println = new Method(new Token("idMetVar", "println", 0), new Token("pr_static", "static", 0), new PrimitiveType(new Token("pr_void", "void", 0)));
        systemClass.addMethod(println);

        // static void printBln(boolean b)
        Method printBln = new Method(new Token("idMetVar", "printBln", 0), new Token("pr_static", "static", 0), new PrimitiveType(new Token("pr_void", "void", 0)));
        printBln.addParameters(new Parameter(new Token("idMetVar", "b", 0), new PrimitiveType(new Token("pr_boolean", "boolean", 0))));
        systemClass.addMethod(printBln);

        // static void printCln(char c)
        Method printCln = new Method(new Token("idMetVar", "printCln", 0), new Token("pr_static", "static", 0), new PrimitiveType(new Token("pr_void", "void", 0)));
        printCln.addParameters(new Parameter(new Token("idMetVar", "c", 0), new PrimitiveType(new Token("pr_char", "char", 0))));
        systemClass.addMethod(printCln);

        // static void printIln(int i)
        Method printIln = new Method(new Token("idMetVar", "printIln", 0), new Token("pr_static", "static", 0), new PrimitiveType(new Token("pr_void", "void", 0)));
        printIln.addParameters(new Parameter(new Token("idMetVar", "i", 0), new PrimitiveType(new Token("pr_int", "int", 0)) ));
        systemClass.addMethod(printIln);

        // static void printSln(String s)
        Method printSln = new Method(new Token("idMetVar", "printSln", 0), new Token("pr_static", "static", 0), new PrimitiveType(new Token("pr_void", "void", 0)));
        printSln.addParameters(new Parameter(new Token("idMetVar", "s", 0), new ReferenceType(new Token("idClase", "String", 0))));
        systemClass.addMethod(printSln);

        classes.put("System", systemClass);
    }

    public static void setCurrentClass(ConcreteClass c) throws SyntacticException {
        if(!classes.containsKey(c.getName())){
            currentClass = c;
        } else {
            throw new SyntacticException("ERROR",c.getToken());
        }
    }

    public static ConcreteClass getCurrentClass() {
        return currentClass;
    }

    public static void setCurrentMethod(Method m) {
        currentMethod = m;
    }

    public static Method getCurrentMethod() {
        return currentMethod;
    }

    public static void setCurrentConstructor(Constructor cons) {
        currentConstructor = cons;
    }

    public static Constructor getCurrentConstructor() {
        return currentConstructor;
    }


    public static void insertClass(String lexeme, ConcreteClass currentClass) {
        classes.put(lexeme, currentClass);
    }
}
