package Semantic.Ast.Expressions.Literals;

import Semantic.Ast.Expressions.ExpressionNode;
import Semantic.Ast.Expressions.OperatorNode;
import Semantic.Types.Type;

public class ExpressionParenthesesAccess extends OperatorNode {
    ExpressionNode expression;

    public ExpressionParenthesesAccess(ExpressionNode expression) {
        this.expression = expression;
    }

    @Override
    public Type check() {
        return null;
    }
}
