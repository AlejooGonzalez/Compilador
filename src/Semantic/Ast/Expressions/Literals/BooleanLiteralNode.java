package Semantic.Ast.Expressions.Literals;

import Semantic.Ast.Expressions.ExpressionNode;
import Semantic.Types.BooleanType;
import Semantic.Types.Type;

public class BooleanLiteralNode extends ExpressionNode {

    @Override
    public Type check() {
        return new BooleanType();
    }
}
