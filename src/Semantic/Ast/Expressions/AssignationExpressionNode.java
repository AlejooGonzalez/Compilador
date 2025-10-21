package Semantic.Ast.Expressions;

import Lexical.Token;
import Semantic.Types.Type;

public class AssignationExpressionNode extends ExpressionNode {
    private ExpressionNode leftSide;
    private ExpressionNode rightSide;
    private Token token;

    public AssignationExpressionNode(ExpressionNode leftSide, ExpressionNode rightSide, Token token) {
        this.leftSide = leftSide;
        this.rightSide = rightSide;
        this.token = token;
    }

    @Override
    public Type check() { return null; }

    @Override
    public int getLine() {
        return 0;
    }

    @Override
    public Token getToken() {
        return null;
    }
}
