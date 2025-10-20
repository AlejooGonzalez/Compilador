package Semantic.Ast.Sentences;

import Exceptions.SemanticException;
import Semantic.Ast.Expressions.ExpressionNode;
import Semantic.Types.BooleanType;

public class IfNode extends SentenceNode{
    private ExpressionNode condition;
    private SentenceNode ifBody;
    private SentenceNode elseBody;

    public IfNode(ExpressionNode condition,SentenceNode ifBody,SentenceNode elseBody){
        this.condition = condition;
        this.ifBody = ifBody;
        this.elseBody = elseBody;
    }

    @Override
    public void check() throws SemanticException {
        condition.check().itsCompatible("if"); //Esta bien?
        ifBody.check();
        elseBody.check();
    }
}
