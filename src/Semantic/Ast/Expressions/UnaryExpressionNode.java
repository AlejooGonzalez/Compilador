package Semantic.Ast.Expressions;

import Exceptions.SemanticException;
import Lexical.Token;
import Semantic.Types.BooleanType;
import Semantic.Types.IntType;
import Semantic.Types.Type;

public class UnaryExpressionNode extends CompoundExpressionNode {
    private OperandNode rightSide;
    private Token operator;

    public UnaryExpressionNode(OperandNode rightSide, Token operator) {
        this.rightSide = rightSide;
        this.operator = operator;
    }

    @Override
    public Type check() throws SemanticException {
        Type type = rightSide.check();
        if (operator != null) {
            if (opIsAritmetic() && type.getLexeme().equals(new IntType(operator.getLineNumber()).getLexeme())) {
                return type;
            } else {
                if (opIsUnary() && type.getLexeme().equals(new BooleanType(operator.getLineNumber()).getLexeme())) {
                    return type;
                } else {
                    throw new SemanticException("Tipos incompatibles en la expresion unaria", operator, operator.getLineNumber());
                }
            }
        }
        return type;
    }

    @Override
    public int getLine() {
        return 0;
    }

    @Override
    public Token getToken() {
        return operator;
    }

    public boolean opIsAritmetic(){
        return operator.getLexeme().equals("+") || operator.getLexeme().equals("-") || operator.getLexeme().equals("++") || operator.getLexeme().equals("--");
    }

    public boolean opIsUnary(){
        return operator.getLexeme().equals("!");
    }
}
