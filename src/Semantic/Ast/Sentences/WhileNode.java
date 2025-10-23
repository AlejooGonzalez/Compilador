package Semantic.Ast.Sentences;

import Exceptions.SemanticException;
import Lexical.Token;
import Semantic.Ast.Expressions.ExpressionNode;
import Semantic.Types.BooleanType;

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
        booleanCondition();
        whileBody.check();
    }

    public void booleanCondition() throws SemanticException {
        if((expression.check() != null) && !(expression.check().getLexeme().equals(new BooleanType(token.getLineNumber()).getLexeme()))){
            throw new SemanticException("La condicion del while no es de tipo booleano", token, token.getLineNumber());
        }
    }
}
