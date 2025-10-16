package Semantic.Ast.Expressions.Literals;

import Semantic.Ast.Expressions.ExpressionNode;
import Semantic.Types.IntType;
import Semantic.Types.Type;

public class IntLiteralNode extends ExpressionNode {

    @Override
    public Type check() {
        return new IntType();
    }
}