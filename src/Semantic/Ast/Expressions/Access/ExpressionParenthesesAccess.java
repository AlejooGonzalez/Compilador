package Semantic.Ast.Expressions.Access;

import Exceptions.SemanticException;
import Lexical.Token;
import Semantic.Ast.Chained.ChainedNode;
import Semantic.Ast.Expressions.ExpressionNode;
import Semantic.Types.Type;

public class ExpressionParenthesesAccess extends AccessNode {
    private ExpressionNode expression;
    private ChainedNode chaining;

    public ExpressionParenthesesAccess(ExpressionNode expression) {
        this.expression = expression;
    }

    @Override
    public Type check() throws SemanticException {
        if(chaining == null) {
            return expression.check();
        } else {
            return chaining.check(expression.check(), expression.getToken());
        }
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

    @Override
    public ChainedNode getChaining() {
        return chaining;
    }

    public ExpressionNode getExpression() {
        return expression;
    }
}
