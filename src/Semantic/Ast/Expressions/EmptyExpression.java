package Semantic.Ast.Expressions;

import Lexical.Token;
import Semantic.Types.Type;

public class EmptyExpression extends ExpressionNode {


    @Override
    public Type check() {
        //return new UniversalType();
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
}
