package Semantic.Ast.Expressions.Literals;

import Lexical.Token;
import Semantic.Types.BooleanType;
import Semantic.Types.Type;

public class BooleanLiteralNode extends LiteralNode {
    Token token;

    public BooleanLiteralNode(Token token) {
        this.token = token;
    }

    @Override
    public Type check() {
        return new BooleanType(token.getLineNumber());
    }

    @Override
    public int getLine() {
        return token.getLineNumber();
    }

    @Override
    public Token getToken() {
        return token;
    }
}
