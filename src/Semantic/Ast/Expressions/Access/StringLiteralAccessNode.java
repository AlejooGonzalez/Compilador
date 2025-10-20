package Semantic.Ast.Expressions.Access;

import Lexical.Token;
import Semantic.Ast.Expressions.OperatorNode;
import Semantic.Types.Type;

public class StringLiteralAccessNode extends OperatorNode {
    private Token token;

    public StringLiteralAccessNode(Token token) {
        this.token = token;
    }

    @Override
    public Type check() {
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
