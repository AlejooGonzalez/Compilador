package Semantic.Ast.Sentences;

import Exceptions.SemanticException;
import Lexical.Token;
import Semantic.Ast.Expressions.ExpressionNode;

public class WhileNode extends SentenceNode{
    private SentenceNode whileBody;
    private ExpressionNode expression;
    private Token token;

    public WhileNode(Token token, ExpressionNode expression, SentenceNode whileBody){
        this.expression = expression;
        this.whileBody = whileBody;
        this.token = token;
    }

    @Override
    public void check() throws SemanticException {

    }

    public SentenceNode getWhileBody() {
        return whileBody;
    }
    public ExpressionNode getExpression() {
        return expression;
    }
    public Token getToken() {
        return token;
    }
    public void setWhileBody(SentenceNode whileBody) {
        this.whileBody = whileBody;
    }
    public void setExpression(ExpressionNode expression) {
        this.expression = expression;
    }
    public void setToken(Token token) {
        this.token = token;
    }
}
