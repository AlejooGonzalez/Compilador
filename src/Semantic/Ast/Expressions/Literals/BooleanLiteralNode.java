package Semantic.Ast.Expressions.Literals;

import Lexical.Token;
import Semantic.Ast.Expressions.ExpressionNode;
import Semantic.Ast.Expressions.OperatorNode;
import Semantic.Types.PrimitiveType;
import Semantic.Types.Type;

public class BooleanLiteralNode extends LiteralNode {
    Token token;

    public BooleanLiteralNode(Token token) {
        this.token = token;
    }


    @Override
    public Type check() {
        return new PrimitiveType(token);
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
