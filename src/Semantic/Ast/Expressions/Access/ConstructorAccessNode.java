package Semantic.Ast.Expressions.Access;

import Exceptions.SemanticException;
import Lexical.Token;
import Main.MainSemantic;
import Semantic.Ast.Chained.ChainedNode;
import Semantic.Ast.Expressions.ExpressionNode;
import Semantic.ConcreteClass;
import Semantic.Types.ReferenceType;
import Semantic.Types.Type;

import java.util.ArrayList;
import java.util.List;

public class ConstructorAccessNode extends AccessNode {
    private Token classToken;
    private ConcreteClass constructorClass;
    private List<ExpressionNode> arguments;
    private ChainedNode chaining;

    public ConstructorAccessNode(Token classToken) {
        this.classToken = classToken;
        arguments = new ArrayList<>();
    }

    @Override
    public Type check() throws SemanticException {
        if(!existClass()){
            throw new SemanticException("la clase a la cual hace referencia, no existe", classToken, classToken.getLineNumber());
        }
        ReferenceType referenceType = new ReferenceType(classToken);
        if(chaining == null) {
            return referenceType;
        } else {
            return chaining.check(referenceType, classToken);
        }
    }

    @Override
    public int getLine() {
        return classToken.getLineNumber();
    }

    @Override
    public Token getToken() {
        return classToken;
    }

    @Override
    public void generate() {
        int auxClass = 1;
        MainSemantic.ST.getInstructionsList().add("    RMEM 1 ; Reservo puntero");
        if(constructorClass!=null) {
            auxClass = constructorClass.getLastAttributeOffset() + 1;
        }
        MainSemantic.ST.getInstructionsList().add("    PUSH " + auxClass);
        MainSemantic.ST.getInstructionsList().add("    PUSH simple_malloc ; Push direccion de metodo");
        MainSemantic.ST.getInstructionsList().add("    CALL");
        MainSemantic.ST.getInstructionsList().add("    DUP ; Duplico la referencia al objeto");
        MainSemantic.ST.getInstructionsList().add("    PUSH "+ MainSemantic.ST.getCurrentClass().getVTable() +" ; Etiqueta de la VT");
        MainSemantic.ST.getInstructionsList().add("    STOREREF 0 ; Guardo VT en CIR, consumiendo una de las referencias");
        MainSemantic.ST.getInstructionsList().add("    DUP ; Duplico this, para metodo de constructor");
        for (ExpressionNode p : arguments) {
            p.generate();
            MainSemantic.ST.getInstructionsList().add("    SWAP ; Muevo this");
        }
        MainSemantic.ST.getInstructionsList().add("    PUSH Constructor_" + classToken.getLexeme() + " ; Direccion del constructor");
        MainSemantic.ST.getInstructionsList().add("    CALL ; Llama al metodo");

        if (chaining != null)
            chaining.generate();
    }

    public void setArguments(List<ExpressionNode> arguments) {
        this.arguments = arguments;
    }

    public void setChaining(ChainedNode chaining) {
        this.chaining = chaining;
    }

    @Override
    public ChainedNode getChaining() {
        return chaining;
    }

    public boolean existClass(){
        return (MainSemantic.ST.itIsAnExistingClass(classToken) != null);
    }

    public void setConstructorClass(ConcreteClass constructorClass) {
        this.constructorClass = constructorClass;
    }
}
