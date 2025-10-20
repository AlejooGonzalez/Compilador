package Semantic.Ast.Expressions.Literals;

import Lexical.Token;
import Semantic.Ast.Expressions.ExpressionNode;
import Semantic.Types.IntType;
import Semantic.Types.Type;

public class NullLiteralNode extends ExpressionNode {
    private Token token;

    public NullLiteralNode(Token token) {
        this.token = token;
    }

    public Token getToken() {
        return token;
    }

    public Type getType() {
        return new IntType(token.getLineNumber());
    }

    @Override
    public Type check() {
        return new IntType(token.getLineNumber());
    }

    @Override
    public int getLine() {
        return 0;
    }
}