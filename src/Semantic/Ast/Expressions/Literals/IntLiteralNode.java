package Semantic.Ast.Expressions.Literals;

import Lexical.Token;
import Semantic.Ast.Expressions.ExpressionNode;
import Semantic.Ast.Expressions.OperatorNode;
import Semantic.Types.IntType;
import Semantic.Types.Type;

public class IntLiteralNode extends LiteralNode {
    private Token token;

    public IntLiteralNode(Token token) {
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