package Semantic.Ast.Expressions;

import Exceptions.SemanticException;
import Lexical.Token;
import Semantic.Types.Type;

public class BinaryExpressionNode extends CompoundExpressionNode {
    private ExpressionNode leftSide;
    private ExpressionNode rightSide;
    private Token operator;

    public BinaryExpressionNode(ExpressionNode leftSide, ExpressionNode rightSide, Token operator) {
        this.leftSide = leftSide;
        this.rightSide = rightSide;
        this.operator = operator;
    }

/*
    @Override
    public Type check() throws SemanticException {
        Type leftType = leftSide.check();
        Type rightType = rightSide.check();
        if(leftType != null && rightType != null) {
            if (leftType.equals(new IntType(operator.getLineNumber())) && rightType.equals(new IntType(operator.getLineNumber()))) {
                if ((operator.getLexeme().equals("+") || operator.getLexeme().equals("-") || operator.getLexeme().equals("*") || operator.getLexeme().equals("/") || operator.getLexeme().equals("%"))) {
                    return new IntType(operator.getLineNumber());
                }
                if (operator.getLexeme().equals("<") || operator.getLexeme().equals(">") || operator.getLexeme().equals("<=") || operator.getLexeme().equals(">=")) {
                    return new BooleanType(operator.getLineNumber());
                }
            }
            if (leftType.equals(new BooleanType(operator.getLineNumber())) && rightType.equals(new BooleanType(operator.getLineNumber()))) {
                if (operator.getLexeme().equals("&&") || operator.getLexeme().equals("||")) {
                    return new BooleanType(operator.getLineNumber());
                }
            } else {
                if (leftType.conformsWith(rightType) || rightType.conformsWith(leftType)) { //CHECK ReferenceType?
                    if (operator.getLexeme().equals("==") || operator.getLexeme().equals("!=")) {
                        return new BooleanType(operator.getLineNumber());
                    }
                }
            }
        }
            throw new SemanticException("Expresion Binaria no valida", operator, operator.getLineNumber());
    } */


    @Override
    public int getLine() {
        return 0;
    }

    @Override
    public Token getToken() {
        return operator;
    }

    @Override
    public Type check() throws SemanticException {
        return null;
    }
}

