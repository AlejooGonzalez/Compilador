package Semantic.Ast.Expressions.Access;

import Exceptions.SemanticException;
import Lexical.Token;
import Main.MainSemantic;
import Semantic.Ast.Chained.ChainedNode;
import Semantic.Ast.Expressions.ExpressionNode;
import Semantic.Method;
import Semantic.Types.Type;

import java.util.List;

public class MethodAccessNode extends AccessNode {
    private List<ExpressionNode> currentParamList;
    private Token tokenIdMetVar;
    private ChainedNode chaining;


    public MethodAccessNode(Token tokenIdMetVar, List<ExpressionNode> currentParamList) {
        this.tokenIdMetVar = tokenIdMetVar;
        this.currentParamList = currentParamList;
    }

    @Override
    public Type check() throws SemanticException {
        Method method = MainSemantic.ST.getCurrentClass().itsAnExisistingMethod(tokenIdMetVar); //Consultar si le puedo pasar class por parametro
        if(method == null){
            throw new SemanticException("El metodo no existe", tokenIdMetVar, tokenIdMetVar.getLineNumber());
        } else {
            method.sameArguments(currentParamList);
        }
        if(chaining != null){
            chaining.check(method.getReturnType());
        }
        return method.getReturnType();
    }

    @Override
    public int getLine() {
        return tokenIdMetVar.getLineNumber();
    }

    @Override
    public Token getToken() {
        return tokenIdMetVar;
    }

    public void setChaining(ChainedNode chaining) {
        this.chaining = chaining;
    }
}
