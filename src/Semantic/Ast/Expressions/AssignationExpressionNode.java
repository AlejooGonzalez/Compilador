package Semantic.Ast.Expressions;

import Exceptions.SemanticException;
import Lexical.Token;
import Semantic.Ast.Chained.ChainedNode;
import Semantic.Ast.Chained.ChainedVariableNode;
import Semantic.Ast.Expressions.Access.AccessNode;
import Semantic.Ast.Expressions.Access.ExpressionParenthesesAccess;
import Semantic.Ast.Expressions.Access.VarAccessNode;
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
        if (!leftSideItsVarOrAttribute()) {
            throw new SemanticException("El lado izquierdo de una asignación debe ser una variable o atributo", token, token.getLineNumber());
        }
        if(leftSideType.isPrimitive() && rightSideType.isPrimitive()){
            if(!rightSideType.itsCompatible(leftSideType)) {
                throw new SemanticException("Asignacion de distintos tipos no valida", token, token.getLineNumber());
            }
        } else {
            if (!rightSideType.itsCompatible(leftSideType)) {
                if (!conformsWithAndNullCheck(leftSideType, rightSideType)) {
                    throw new SemanticException("Asignacion de distintos tipos no valida", token, token.getLineNumber());
                }
            }
        }
        if (rightSide instanceof ExpressionParenthesesAccess paramReference){
            if (paramReference.getExpression() instanceof AssignationExpressionNode){
                throw new SemanticException("Expresion invalida a izquierda de asignacion", token ,token.getLineNumber());
            }
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

    @Override
    public void generate() {

    }

    private boolean endsInAttribute(AccessNode access) {
        if (access.getChaining() == null) {
            return false;
        }
        ChainedNode current = access.getChaining();
        while (current.getChaining() != null) {
            current = current.getChaining();
        }
        return current instanceof ChainedVariableNode;
    }

    public boolean leftSideItsVarOrAttribute() {
        if (leftSide instanceof VarAccessNode v){
            if(v.getChaining() != null) {
                return endsInAttribute(v);
            } else {
                return true;
            }
        } else {
            if (leftSide instanceof AccessNode access)
                return endsInAttribute(access);
        }
        return false;
    }

    public boolean conformsWithAndNullCheck(Type leftType, Type rightType) {
        if(leftType.getLexeme().equals("null") && !rightType.isPrimitive() || rightType.getLexeme().equals("null") && !leftType.isPrimitive()) {
            return true;
        } else {
            return leftType.conformsWith(rightType);
        }
    }
}
