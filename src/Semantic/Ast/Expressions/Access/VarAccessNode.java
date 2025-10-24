package Semantic.Ast.Expressions.Access;

import Exceptions.SemanticException;
import Lexical.Token;
import Main.MainSemantic;
import Semantic.Ast.Chained.ChainedNode;
import Semantic.Types.Type;

public class VarAccessNode extends AccessNode {
    private Token token;
    private ChainedNode chaining;
    private Type returnType;

    public VarAccessNode(Token token) {
        this.token = token;
    }

    @Override
    public Type check() throws SemanticException {
        varIsLocalVar();
        varIsParameter();
        varIsAttribute();
        if(returnType == null) {
            throw new SemanticException("No existe la variable instanciada", token, token.getLineNumber());
        }
        if(chaining != null) {
            chaining.check(returnType);
        }
        return returnType;
    }

    @Override
    public int getLine() {
        return 0;
    }

    @Override
    public Token getToken() {
        return null;
    }

    public void setChaining(ChainedNode chaining) {
        this.chaining = chaining;
    }

    public void varIsLocalVar(){
        if(MainSemantic.ST.getCurrentMethod().getBlockNode().getLocalVar(token.getLexeme()) != null) {
            returnType = MainSemantic.ST.getCurrentClass().getAttribute(token.getLexeme()).getType();
        }
    }

    public void varIsParameter(){
        if(MainSemantic.ST.getCurrentMethod().getParameter(token.getLexeme()) != null) {
            returnType = MainSemantic.ST.getCurrentClass().getAttribute(token.getLexeme()).getType();
        }
    }
    public void varIsAttribute() throws SemanticException {
        if(MainSemantic.ST.getCurrentMethod().getParameter(token.getLexeme()) != null) {
            if(!MainSemantic.ST.getCurrentMethod().getModifier().getLexeme().equals("static")) {
                returnType = MainSemantic.ST.getCurrentClass().getAttribute(token.getLexeme()).getType();
            } else {
                throw new SemanticException("No se puede acceder a un atributo de instancia estatico", token, token.getLineNumber());
            }
        }
    }
}