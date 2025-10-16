package Semantic.Ast.Expressions;

import Lexical.Token;
import Semantic.Types.Type;

public class AsignationExpressionNode extends ExpressionNode {
    private CompoundExpressionNode leftSide;
    private ExpressionNode rightSide;
    private Token token;

    public AsignationExpressionNode(CompoundExpressionNode leftSide, ExpressionNode rightSide, Token token) {
        this.leftSide = leftSide;
        this.rightSide = rightSide;
        this.token = token;
    }

    @Override
    public Type check() {
        return null;
    }
}
