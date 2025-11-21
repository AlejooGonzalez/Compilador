package Semantic.Ast.Chained;

import Exceptions.SemanticException;
import Lexical.Token;
import Main.MainSemantic;
import Semantic.Ast.Expressions.ExpressionNode;
import Semantic.ConcreteClass;
import Semantic.Method;
import Semantic.Types.Type;

import java.util.ArrayList;
import java.util.List;

public class ChainedCallNode extends ChainedNode {
    private Token token;
    private ChainedNode chaining;
    private List<ExpressionNode> arguments;
    private boolean isLeftSideOfAssign= false;
    private ConcreteClass previousClass;

    public ChainedCallNode(Token token) {
        this.token = token;
    }


    @Override
    public Type check(Type leftSide, Token leftToken) throws SemanticException {
        if(leftSide.isPrimitive()){
            throw new SemanticException("El encadenado debe ser de tipo referencia", leftToken, token.getLineNumber());
        }
        ConcreteClass leftClass = MainSemantic.ST.itIsAnExistingClass(leftSide.getToken());
        previousClass = leftClass;
        if (leftClass == null) {
            throw new SemanticException("Clase del metodo anterior no declarada", leftToken, token.getLineNumber());
        }
        Method method = leftClass.itsAnExisistingMethod(token);
        if (method == null) {
            throw new SemanticException("El método no existe en la clase", token, token.getLineNumber());
        } else {
            if(method.isStaticMethod()) {
                throw new SemanticException("No se puede invocar un método estático desde una instancia", token, token.getLineNumber());
            }
        }
        method.sameArguments(arguments, token);
        Type returnType = method.getReturnType();
        if (chaining != null) {
            return chaining.check(returnType, token);
        }
        return returnType;
    }

    @Override
    public void setChaining(ChainedNode chaining) {
        this.chaining = chaining;
    }

    @Override
    public ChainedNode getChaining() {
        return chaining;
    }

    @Override
    public void generate() {
        Method methodAux = previousClass.itsAnExisistingMethod(token);
        if(methodAux.isStaticMethod()) {
            generateStaticMethodCode(methodAux);
        } else {
            generateDynamicMethodCode(methodAux);
        }
        if (chaining != null) {
            if (isLeftSideOfAssign) {
                chaining.setItsLeftSide(true);
            }
            chaining.generate();
        }
    }

    private void generateStaticMethodCode(Method methodAux){
        MainSemantic.ST.getInstructionsList().add("POP");
        if(!methodAux.getReturnType().getLexeme().equals("void")) {
            MainSemantic.ST.getInstructionsList().add("RMEM 1");
        }
        for(ExpressionNode e : arguments) {
            e.generate();
        }
        MainSemantic.ST.getInstructionsList().add("PUSH " + methodAux.getLabel());
        MainSemantic.ST.getInstructionsList().add("CALL");
    }

    private void generateDynamicMethodCode(Method methodAux){
        if(!methodAux.getReturnType().getLexeme().equals("void")) {
            MainSemantic.ST.getInstructionsList().add("RMEM 1");
            MainSemantic.ST.getInstructionsList().add("SWAP");
        }
        for(ExpressionNode e : arguments) {
            e.generate();
            MainSemantic.ST.getInstructionsList().add("SWAP");
        }
        MainSemantic.ST.getInstructionsList().add("DUP");
        MainSemantic.ST.getInstructionsList().add("LOADREF 0");
        MainSemantic.ST.getInstructionsList().add("LOADREF " + methodAux.getOffset());
        MainSemantic.ST.getInstructionsList().add("CALL");

    }

        //---------------------------------------------
    @Override
    public void setItsLeftSide(boolean leftSide) {
        this.isLeftSideOfAssign = leftSide;
    }


    public void setArgumentList(List<ExpressionNode> arguments) {
        this.arguments = arguments;
    }
}
