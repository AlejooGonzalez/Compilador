package Semantic.Ast.Expressions.Literals;

import Lexical.Token;
import Semantic.Ast.Expressions.ExpressionNode;
import Semantic.Types.CharType;
import Semantic.Types.Type;

public class CharLiteralNode extends ExpressionNode {
    Token token;

    public CharLiteralNode(Token token) {
        this.token = token;
    }
    @Override
    public Type check() {
        return new CharType(0);
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
