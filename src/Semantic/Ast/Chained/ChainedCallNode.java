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

    public ChainedCallNode(Token token) {
        this.token = token;
    }


    @Override
    public Type check(Type leftSide, Token leftToken) throws SemanticException {
        if(leftSide.isPrimitive()){
            throw new SemanticException("El encadenado debe ser de tipo referencia", leftToken, token.getLineNumber());
        }
        ConcreteClass leftClass = MainSemantic.ST.itIsAnExistingClass(leftSide.getToken());
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
    Method methodAux = MainSemantic.ST.getCurrentClass().itsAnExisistingMethod(token);
    if(methodAux.isStaticMethod()){
        MainSemantic.ST.getInstructionsList().add("POP; Es estatico");
        if(methodAux.getReturnType().getLexeme().equals("void")) {
            MainSemantic.ST.getInstructionsList().add("RMEM 1 ; Reservo lugar para el retorno");
        }
        for(ExpressionNode exp : arguments){
            exp.generate();
        }
        MainSemantic.ST.getInstructionsList().add("PUSH "+ methodAux.getLexeme() + MainSemantic.ST.getCurrentClass());
        MainSemantic.ST.getInstructionsList().add("CALL");
    } else {
        if(!methodAux.getReturnType().getLexeme().equals("void")) {
            MainSemantic.ST.getInstructionsList().add("RMEM 1 ; Lugar para el retorno");
            MainSemantic.ST.getInstructionsList().add("SWAP ; This en tope de la pila");
        }
        for(ExpressionNode exp : arguments){
            exp.generate();
            MainSemantic.ST.getInstructionsList().add("SWAP");
        }
        MainSemantic.ST.getInstructionsList().add("    DUP ; Duplico this");
        MainSemantic.ST.getInstructionsList().add("    LOADREF 0 ; Apila el valor de la VT");
        MainSemantic.ST.getInstructionsList().add("    LOADREF "+ methodAux.getOffset() +" ; Cargo metodo "+ methodAux.getLexeme() + "a la VT");
        MainSemantic.ST.getInstructionsList().add("    CALL ; Llamo metodo");
    }
        if (chaining != null) {
            chaining.generate();
        }
    }

    public void setArgumentList(List<ExpressionNode> arguments) {
        this.arguments = arguments;
    }
}
