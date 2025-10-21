package Semantic.Ast.Chained;

import Lexical.Token;
import Semantic.Ast.Expressions.ExpressionNode;
import Semantic.Types.Type;
import java.util.List;

public class ChainedVariableNode extends ChainedNode {
    private Token token;
    private ChainedNode chaining;

    public ChainedVariableNode(Token token) {
        this.token = token;
    }

    @Override
    public Type check(Type t) {
        return null;
    }

    @Override
    public void setChaining(ChainedNode chaining) {
        this.chaining = chaining;
    }
}