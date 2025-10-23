package Semantic.Ast.Sentences;

import Exceptions.SemanticException;
import Lexical.Token;
import Semantic.Ast.Expressions.ExpressionNode;
import Semantic.Types.BooleanType;

public class IfNode extends SentenceNode{
    private ExpressionNode condition;
    private SentenceNode ifBody;
    private SentenceNode elseBody;
    private Token ifToken;

    public IfNode(ExpressionNode condition,SentenceNode ifBody,SentenceNode elseBody, Token ifToken){
        this.condition = condition;
        this.ifBody = ifBody;
        this.elseBody = elseBody;
        this.ifToken = ifToken;
    }

    @Override
    public void check() throws SemanticException {
        booleanCondition();
        ifBody.check();
        elseBody.check();
    }

    public void booleanCondition() throws SemanticException {
        if(!condition.check().getLexeme().equals(new BooleanType(ifToken.getLineNumber()).getLexeme())){
            throw new SemanticException("La condicion del If debe ser un booleano", ifToken, ifToken.getLineNumber());
        }
    }
}
