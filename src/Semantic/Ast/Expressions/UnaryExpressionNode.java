package Semantic.Ast.Expressions;

import Lexical.Token;
import Semantic.Types.Type;

public class UnaryExpressionNode extends CompoundExpressionNode {
    private OperatorNode rightSide;
    private Token operator;

    public UnaryExpressionNode(OperatorNode rightSide, Token operator) {
        this.rightSide = rightSide;
        this.operator = operator;
    }

    @Override
    public Type check() {
        Type type = rightSide.check();
        return type;
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
