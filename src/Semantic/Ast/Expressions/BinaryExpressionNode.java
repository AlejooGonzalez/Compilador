package Semantic.Ast.Expressions;

import Lexical.Token;
import Semantic.Types.Type;

public class BinaryExpressionNode extends CompoundExpressionNode {
    private CompoundExpressionNode leftSide;
    private CompoundExpressionNode rightSide;
    private Token operator;

    public BinaryExpressionNode(CompoundExpressionNode leftSide, CompoundExpressionNode rightSide, Token operator) {
        this.leftSide = leftSide;
        this.rightSide = rightSide;
        this.operator = operator;
    }

    @Override
    public Type check() {
        return null;
    }
}
