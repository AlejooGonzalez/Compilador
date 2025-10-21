package Semantic.Ast.Sentences;

import Exceptions.SemanticException;
import Lexical.Token;
import Semantic.Ast.Expressions.ExpressionNode;

public class AssignCallNode extends SentenceNode{
    Token token;
    ExpressionNode expressionNode;

    public AssignCallNode(Token token, ExpressionNode expressionNode) {
        this.token = token;
        this.expressionNode = expressionNode;
    }

    @Override
    public void check() throws SemanticException {

    }
}
