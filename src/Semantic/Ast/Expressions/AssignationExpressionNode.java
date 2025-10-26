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
        if(!leftSideType.getToken().getLexeme().equals(rightSideType.getToken().getLexeme())){
           throw new SemanticException("Asignacion de distintos tipos no valida",token, token.getLineNumber());
        }
        return leftSideType;
    }

    @Override
    public int getLine() {
        return token.getLineNumber();
    }

    @Override
    public Token getToken() {
        return token;
    }
}
