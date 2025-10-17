package Semantic.Ast.Expressions.Literals;

import Lexical.Token;
import Semantic.Ast.Expressions.ExpressionNode;
import Semantic.Types.IntType;
import Semantic.Types.Type;

public class IntLiteralNode extends ExpressionNode {
    private Token token;

    public IntLiteralNode(Token token) {
        this.token = token;
    }

    public Token getToken() {
        return token;
    }

    public Type getType() {
        return new IntType();
    }

    @Override
    public Type check() {
        return new IntType();
    }
}