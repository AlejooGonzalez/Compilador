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
        if(leftType != null && rightType != null) {
            if (arithmeticalOperator()) {
                if (itsInt(leftType) && itsInt(rightType)) {
                    return new IntType(operator.getLineNumber());
                }
            }
            if (compareOperator()) {
                if (itsInt(leftType) && itsInt(rightType)) {
                    return new BooleanType(operator.getLineNumber());
                }
            }
            if (logicOperator()) {
                if (itsBoolean(leftType) && itsBoolean(rightType)) {
                    return new BooleanType(operator.getLineNumber());
                }
            }
            if (equalOrDifferentOperator()) {
                if (rightType.conformsWith(leftType) || leftType.conformsWith(rightType) ) {
                    return new BooleanType(operator.getLineNumber());
                }
            }
        }
        throw new SemanticException("Expresion Binaria no valida con el operador", operator, operator.getLineNumber());
    }

    @Override
    public int getLine() {
        return operator.getLineNumber();
    }

    @Override
    public Token getToken() {
        return operator;
    }

    public boolean itsInt(Type type){
        return type.getLexeme().equals(new IntType(operator.getLineNumber()).getLexeme());
    }

    public boolean itsBoolean(Type type){
        return type.getLexeme().equals(new BooleanType(operator.getLineNumber()).getLexeme());
    }

    public boolean arithmeticalOperator(){
        return (operator.getLexeme().equals("+") || operator.getLexeme().equals("-") || operator.getLexeme().equals("*") || operator.getLexeme().equals("/") || operator.getLexeme().equals("%"));
    }

    public boolean compareOperator(){
        return (operator.getLexeme().equals("<") || operator.getLexeme().equals(">") || operator.getLexeme().equals("<=") || operator.getLexeme().equals(">="));
    }

    public boolean logicOperator(){
        return (operator.getLexeme().equals("&&") || operator.getLexeme().equals("||"));
    }

    public boolean equalOrDifferentOperator(){
        return (operator.getLexeme().equals("==") || operator.getLexeme().equals("!="));
    }
}

