package Semantic.Ast.Chained;

import Lexical.Token;
import Semantic.Ast.Expressions.ExpressionNode;
import Semantic.Types.Type;

import java.util.List;

public class ChainedCallNode extends ChainedNode {
    private Token token;
    private ChainedNode cad;
    private ChainedNode chaining;
    private List<ExpressionNode> arguments;

    public ChainedCallNode(Token token) {
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

    public void setArgumentList(List<ExpressionNode> arguments) {
        this.arguments = arguments;
    }
}
