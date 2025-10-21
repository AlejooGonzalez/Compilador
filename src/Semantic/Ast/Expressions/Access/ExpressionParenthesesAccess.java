package Semantic.Ast.Expressions.Access;

import Lexical.Token;
import Semantic.Ast.Chained.ChainedNode;
import Semantic.Ast.Expressions.ExpressionNode;
import Semantic.Types.Type;

public class ExpressionParenthesesAccess extends AccessNode {
    ExpressionNode expression;
    private ChainedNode chaining;

    public ExpressionParenthesesAccess(ExpressionNode expression) {
        this.expression = expression;
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
