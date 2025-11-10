package Semantic.Ast.Expressions;

import Lexical.Token;
import Semantic.Types.PrimitiveType;
import Semantic.Types.Type;

public class EmptyExpression extends ExpressionNode {

    @Override
    public Type check() {
        return new PrimitiveType(new Token("universal", "universal",0));
    }

    @Override
    public int getLine() {
        return 0;
    }

    @Override
    public Token getToken() {
        return null;
    }

    public String getLexeme() {
        return "universal";
    }
}
