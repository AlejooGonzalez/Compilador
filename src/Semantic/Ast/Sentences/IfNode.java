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

    }

    /*
    @Override
    public void check() throws SemanticException {
        if(!condition.check().equals(new BooleanType(ifToken.getLineNumber()))){
            throw new SemanticException("La condicion del If debe ser un booleano", ifToken, ifToken.getLineNumber());
        }
        ifBody.check();
        elseBody.check();
    } */
}
