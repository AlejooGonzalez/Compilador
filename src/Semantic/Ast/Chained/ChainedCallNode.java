package Semantic.Ast.Chained;

import Exceptions.SemanticException;
import Lexical.Token;
import Semantic.Ast.Expressions.ExpressionNode;
import Semantic.Types.Type;

import java.util.List;

public class ChainedCallNode extends ChainedNode {
    private Token token;
    private ChainedNode chaining;
    private List<ExpressionNode> arguments;

    public ChainedCallNode(Token token) {
        this.token = token;
    }

    /*
    @Override
    public Type check(Type t) throws SemanticException {
        if(t.isPrimitive()){
            throw new SemanticException("El encadenado debe ser de tipo referencia", token, token.getLineNumber());
        } else {
            if(!token.getTokenType().conformsWith(t)){
                throw new SemanticException("El encadenado debe ser de de mismo tipo/heredado", token, token.getLineNumber());
            }
        }
    } */

    @Override
    public Type check(Type t) throws SemanticException {
        return null;
    }

    @Override
    public void setChaining(ChainedNode chaining) {
        this.chaining = chaining;
    }

    public void setArgumentList(List<ExpressionNode> arguments) {
        this.arguments = arguments;
    }
}
