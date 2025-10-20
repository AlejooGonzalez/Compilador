package Semantic.Ast.Sentences;

import Exceptions.SemanticException;
import Lexical.Token;
import Semantic.Ast.Expressions.ExpressionNode;
import Semantic.Types.Type;

public class AssignationNode extends SentenceNode {
    private ExpressionNode leftExpression;
    private ExpressionNode rightExpression;
    private Token token;

    public AssignationNode(ExpressionNode leftExpression, ExpressionNode rightExpression, Token token) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
        this.token = token;
    }

    @Override
    public void check() throws SemanticException {
        Type leftType = leftExpression.check();
        Type rightType = rightExpression.check();

        if(!leftType.equals(rightType)){
            throw new SemanticException("Tipos incompatibles en la asignacion", token, token.getLineNumber());
        }
    }
}
