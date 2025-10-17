package Semantic.Ast.Sentences;

import Exceptions.SemanticException;
import Semantic.Ast.Expressions.ExpressionNode;

public class IfNode extends SentenceNode{
    private ExpressionNode condition;
    private SentenceNode ifBody;
    private SentenceNode elseBody;

    public IfNode(ExpressionNode condition,SentenceNode ifBody,SentenceNode elseBody){
        this.condition = condition;
        this.ifBody = ifBody;
        this.elseBody = elseBody;
    }

    public ExpressionNode getCondition() {
        return condition;
    }

    public SentenceNode getIfBody() {
        return ifBody;
    }

    public SentenceNode getElseBody() {
        return elseBody;
    }

    public void setCondition(ExpressionNode condition) {
        this.condition = condition;
    }

    public void setIfBody(SentenceNode ifBody) {
        this.ifBody = ifBody;
    }

    public void setElseBody(SentenceNode elseBody) {
        this.elseBody = elseBody;
    }

    @Override
    public void check() throws SemanticException {
        condition.check().itsCompatible("pr_boolean");
        ifBody.check();
        elseBody.check();
    }
}
