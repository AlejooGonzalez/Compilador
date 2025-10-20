package Semantic.Ast.Sentences;

import Exceptions.SemanticException;
import Lexical.Token;
import Main.MainSemantic;
import Semantic.Ast.Expressions.ExpressionNode;
import Semantic.Types.Type;

public class ReturnNode extends SentenceNode {
    private ExpressionNode exp;
    private Token token;

    public ReturnNode(Token token, ExpressionNode exp){
        this.token = token;
        this.exp = exp;
    }

    @Override
    public void check() throws SemanticException {
        Type methodReturn = MainSemantic.ST.getCurrentMethod().getReturnType();
        if(!methodReturn.equals(exp.check())){
           throw new SemanticException("El retorno del metodo no coincide", token, token.getLineNumber());
        }
        if (methodReturn.getToken().getLexeme().equals("void") && exp != null) {
            throw new SemanticException("El método 'void' no debe devolver un valor", token, token.getLineNumber());
        }
    }
}
