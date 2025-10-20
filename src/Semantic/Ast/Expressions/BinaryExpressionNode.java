package Semantic.Ast.Expressions;

import Exceptions.SemanticException;
import Lexical.Token;
import Semantic.Types.BooleanType;
import Semantic.Types.IntType;
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

    @Override
    public Type check() throws SemanticException {
        Type leftType = leftSide.check();
        Type rightType = rightSide.check();
                if (leftType.itsCompatible("int") && rightType.itsCompatible("int")) {
                    if ((operator.getLexeme().equals("+") || operator.getLexeme().equals("-") || operator.getLexeme().equals("*") || operator.getLexeme().equals("/") || operator.getLexeme().equals("%"))) {
                        return new IntType(operator.getLineNumber());
                    }
                    if (operator.getLexeme().equals("<") || operator.getLexeme().equals(">") || operator.getLexeme().equals("<=") || operator.getLexeme().equals(">=")) {
                        return new BooleanType(operator.getLineNumber());
                    }
                }
                if (leftType.itsCompatible("boolean") && rightType.itsCompatible("boolean")) {
                    if (operator.getLexeme().equals("&&") || operator.getLexeme().equals("||")) {
                        return new BooleanType(operator.getLineNumber());
                    }
                }
            else {
                    if (leftType.conformsWith(rightType) || rightType.conformsWith(leftType)) { //CHECK ReferenceType?
                        if (operator.getLexeme().equals("==") || operator.getLexeme().equals("!=")) {
                            return new BooleanType(operator.getLineNumber());
                        }
                    }
            }
            throw new SemanticException("Expresion Binaria no valida", operator, operator.getLineNumber());
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

