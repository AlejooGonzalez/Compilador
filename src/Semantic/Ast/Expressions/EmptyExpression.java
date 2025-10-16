package Semantic.Ast.Expressions;

import Semantic.Types.Type;

public class EmptyExpression extends ExpressionNode {

    @Override
    public Type check() {
        //return new UniversalType();
        return null;
    }
}
