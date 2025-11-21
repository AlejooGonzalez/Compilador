package Semantic.Ast.Expressions;

import Exceptions.SemanticException;
import Lexical.Token;
import Main.MainSemantic;
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
            if (opIsAritmetic() && itsInt(type)) {
                return type;
            } else {
                if (opIsUnary() && itsBoolean(type)) {
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

    @Override
    public void generate() {
        rightSide.generate();
        switch (operator.getLexeme()) {
            case "++" -> {
                MainSemantic.ST.getInstructionsList().add("PUSH 1");
                MainSemantic.ST.getInstructionsList().add("ADD");
            }
            case "+" -> { }
            case "-" -> MainSemantic.ST.getInstructionsList().add("NEG");
            case "--" -> {
                MainSemantic.ST.getInstructionsList().add("PUSH 1");
                MainSemantic.ST.getInstructionsList().add("SUB");
            }
            case "!" -> MainSemantic.ST.getInstructionsList().add("NOT");
        }
    }

    public boolean opIsAritmetic(){
        return operator.getLexeme().equals("+") || operator.getLexeme().equals("-") || operator.getLexeme().equals("++") || operator.getLexeme().equals("--");
    }

    public boolean opIsUnary(){
        return operator.getLexeme().equals("!");
    }

    public boolean itsInt(Type type){
        return type.itsCompatible(new IntType(operator.getLineNumber()));
    }

    public boolean itsBoolean(Type type){
        return type.itsCompatible(new BooleanType(operator.getLineNumber()));
    }
}
