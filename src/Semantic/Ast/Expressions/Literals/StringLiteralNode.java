package Semantic.Ast.Expressions.Literals;

import Lexical.Token;
import Semantic.Ast.Chained.ChainedNode;
import Semantic.Ast.Expressions.Access.AccessNode;
import Semantic.Types.ReferenceType;
import Semantic.Types.Type;

public class StringLiteralNode extends AccessNode {
    private Token token;

    public StringLiteralNode(Token token) {
        this.token = token;
    }

    @Override
    public Type check() {
        return new ReferenceType(new Token("stringLiteral","stringLiteral", token.getLineNumber()));
    }

    @Override
    public void setChaining(ChainedNode chaining) { }

    @Override
    public int getLine() {
        return token.getLineNumber();
    }

    @Override
    public Token getToken() {
        return token;
    }
}
