package Semantic.Ast.Sentences;

import Exceptions.SemanticException;
import Lexical.Token;
import Semantic.Ast.Expressions.ExpressionNode;
import Semantic.Types.Type;

public class ReturnNode extends SentenceNode {
    private ExpressionNode exp;
    private Token token;
    private Type returnMethodExpected;

    public ReturnNode(Token token){
        this.token = token;
    }

    public void setOptionalExpression(ExpressionNode exp){
        if(exp != null)
            this.exp = exp;
    }

    public void setReturnExpected(Type returnExpected){
        this.returnMethodExpected = returnExpected;
    }


    @Override
    public void check() throws SemanticException {
            if(returnMethodExpected.getLexeme().equals("void")){
               throw new SemanticException("Un metodo con retorno de tipo 'void' no debe tener return", token, token.getLineNumber());
            } else {
                Type expressionType = exp.check();
                if((expressionType != null) && !returnMethodExpected.getLexeme().equals(expressionType.getLexeme())){
                    throw new SemanticException("No coincide el retorno con el tipo de retorno del metodo", token, token.getLineNumber());
                }
            }
            //Falta caso de que es un referenceNode y heredado
    }
}
