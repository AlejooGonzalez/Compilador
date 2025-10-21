package Semantic.Ast.Expressions.Access;

import Lexical.Token;
import Semantic.Ast.Chained.ChainedNode;
import Semantic.Ast.Expressions.ExpressionNode;
import Semantic.Types.Type;

import java.util.List;

public class StaticMethodAccessNode extends AccessNode{
    private Token staticClassToken;
    private Token staticMethodToken;
    private List<ExpressionNode> parameters;
    private ChainedNode chaining;


    public  StaticMethodAccessNode(Token staticClassToken, Token staticMethodToken, List<ExpressionNode> parameters) {
        this.staticClassToken = staticClassToken;
        this.staticMethodToken = staticMethodToken;
        this.parameters = parameters;
    }

    @Override
    public Type check() {
        return null;
    }

    @Override
    public int getLine() {
        return 0;
    }

    @Override
    public Token getToken() {
        return null;
    }

    public void setChaining(ChainedNode chaining) {
        this.chaining = chaining;
    }
}
