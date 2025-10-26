package Semantic.Ast.Chained;

import Exceptions.SemanticException;
import Lexical.Token;
import Main.MainSemantic;
import Semantic.Ast.Expressions.ExpressionNode;
import Semantic.ConcreteClass;
import Semantic.Method;
import Semantic.Types.Type;

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
            throw new SemanticException("El método no existe en la clase", leftToken, token.getLineNumber());
        } else {
            if(method.isStaticMethod()) {
                throw new SemanticException("No se puede invocar un método estático desde una instancia", leftToken, token.getLineNumber());
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

    public void setArgumentList(List<ExpressionNode> arguments) {
        this.arguments = arguments;
    }

    public void checkArguments(Method method) throws SemanticException {
        if(method.getParameters() != null && arguments == null){
            throw new SemanticException("Parametros incompatibles", token, token.getLineNumber());
        }
        if(method.getParameters() == null && arguments != null){
            throw new SemanticException("Parametros incompatibles", token, token.getLineNumber());
        }
        if(method.getParameters() != null && arguments != null){
            method.sameArguments(arguments, token);
        }
    }
}
