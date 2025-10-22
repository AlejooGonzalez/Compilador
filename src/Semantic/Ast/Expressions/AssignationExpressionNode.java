package Semantic.Ast.Expressions;

import Exceptions.SemanticException;
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
    public Type check() throws SemanticException {
        Type leftSideType = leftSide.check();
        Type rightSideType = rightSide.check();
        return leftSideType;
    }

    @Override
    public int getLine() {
        return 0;
    }

    @Override
    public Token getToken() {
        return token;
    }
}
