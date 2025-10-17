package Semantic.Ast.Sentences;

import Exceptions.SemanticException;
import Semantic.Ast.Expressions.ExpressionNode;

public class ReturnNode extends SentenceNode {
    private ExpressionNode exp;

    public ReturnNode(ExpressionNode exp){
        this.exp = exp;
    }

    @Override
    public void check() throws SemanticException {

    }
}
