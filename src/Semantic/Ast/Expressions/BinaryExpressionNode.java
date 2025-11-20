package Semantic.Ast.Expressions;

import Exceptions.SemanticException;
import Lexical.Token;
import Main.MainSemantic;
import Semantic.Types.*;

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
                if (!rightType.getLexeme().equals("null") && !leftType.getLexeme().equals("null")) {
                    if (!leftType.isPrimitive() && !rightType.isPrimitive()) {
                        if (rightType.conformsWith(leftType) || leftType.conformsWith(rightType)) {
                            return new BooleanType(operator.getLineNumber());
                        }
                    } else {
                        if ((!rightType.getLexeme().equals("void") && !leftType.getLexeme().equals("void"))) {
                            if ((rightType.conformsWith(leftType) || leftType.conformsWith(rightType))) {
                                return new BooleanType(operator.getLineNumber());
                            }
                        }
                    }
                } else {
                    if (!leftType.isPrimitive() && rightType.getLexeme().equals("null") || !rightType.isPrimitive() && leftType.getLexeme().equals("null")) {
                        return new BooleanType(operator.getLineNumber());
                    } else {
                        if (rightType.getLexeme().equals("null") && leftType.getLexeme().equals("null")) {
                            return new BooleanType(operator.getLineNumber());
                        }
                    }
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

    @Override
    public void generate() {
        leftSide.generate();
        rightSide.generate();
        switch (operator.getLexeme()) {
            case "+"->
                MainSemantic.ST.getInstructionsList().add("ADD");
            case "-"->
                MainSemantic.ST.getInstructionsList().add("SUB");
            case "*"->
                MainSemantic.ST.getInstructionsList().add("MUL");
            case "/"->
                MainSemantic.ST.getInstructionsList().add("DIV");
            case "%"->
                MainSemantic.ST.getInstructionsList().add("MOD");
            case "<"->
                MainSemantic.ST.getInstructionsList().add("LT");
            case ">"->
                MainSemantic.ST.getInstructionsList().add("GT");
            case "<="->
                MainSemantic.ST.getInstructionsList().add("LE");
            case ">="->
                MainSemantic.ST.getInstructionsList().add("GE");
            case "=="->
                MainSemantic.ST.getInstructionsList().add("EQ");
            case "!="->
                MainSemantic.ST.getInstructionsList().add("NE");
            case "&&"->
                MainSemantic.ST.getInstructionsList().add("AND");
            case "||"->
                MainSemantic.ST.getInstructionsList().add("OR");
        }
    }

    public boolean itsInt(Type type){
        return type.itsCompatible(new IntType(operator.getLineNumber()));
    }

    public boolean itsBoolean(Type type){
        return type.itsCompatible(new BooleanType(operator.getLineNumber()));
    }

    public boolean itsReference(Type type){
        return type.itsCompatible(new ReferenceType(leftSide.getToken()));
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

