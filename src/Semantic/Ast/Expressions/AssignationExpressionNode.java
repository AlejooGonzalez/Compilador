package Semantic.Ast.Expressions;

import Exceptions.SemanticException;
import Lexical.Token;
import Semantic.Ast.Chained.ChainedNode;
import Semantic.Ast.Chained.ChainedVariableNode;
import Semantic.Ast.Expressions.Access.AccessNode;
import Semantic.Ast.Expressions.Access.VarAccessNode;
import Semantic.Ast.Expressions.Literals.LiteralNode;
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
        if (leftSideItsExpOrLiteral()) {
            throw new SemanticException("No se puede asignar a una expresión o literal", token, token.getLineNumber());
        }
        if(!rightSideType.itsCompatible(leftSideType)){
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
        return (leftSide instanceof VarAccessNode || (leftSide instanceof AccessNode access && endsInAttribute(access)));
    }

    public boolean leftSideItsExpOrLiteral(){
        return (leftSide instanceof LiteralNode || leftSide instanceof BinaryExpressionNode);
    }
}
