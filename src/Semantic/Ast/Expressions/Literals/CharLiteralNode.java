package Semantic.Ast.Expressions.Literals;

import Semantic.Ast.Expressions.ExpressionNode;
import Semantic.Types.CharType;
import Semantic.Types.Type;

public class CharLiteralNode extends ExpressionNode {

    @Override
    public Type check() {
        return new CharType();
    }
}
