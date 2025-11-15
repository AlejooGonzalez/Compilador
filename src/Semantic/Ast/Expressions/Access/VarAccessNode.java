package Semantic.Ast.Expressions.Access;

import Exceptions.SemanticException;
import Lexical.Token;
import Main.MainSemantic;
import Semantic.Ast.Chained.ChainedNode;
import Semantic.Ast.Sentences.BlockNode;
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
            return chaining.check(returnType, token);
        }
        return returnType;
    }

    @Override
    public int getLine() {
        return token.getLineNumber();
    }

    @Override
    public Token getToken() {
        return token;
    }

    @Override
    public void generate() {

    }

    public void setChaining(ChainedNode chaining) {
        this.chaining = chaining;
    }

    @Override
    public ChainedNode getChaining() {
        return chaining;
    }

    public void varIsParameter(){
        if(MainSemantic.ST.getCurrentMethod().getParameter(token.getLexeme()) != null) {
            returnType = MainSemantic.ST.getCurrentMethod().getParameter(token.getLexeme()).getType();
        }
    }

    public void varIsAttribute() throws SemanticException {
        if(MainSemantic.ST.getCurrentClass().getAttribute(token.getLexeme()) != null) {
            if (MainSemantic.ST.getCurrentMethod().getModifier() != null) {
                if (!MainSemantic.ST.getCurrentMethod().getModifier().getLexeme().equals("static")) {
                    returnType = MainSemantic.ST.getCurrentClass().getAttribute(token.getLexeme()).getType();
                } else {
                    throw new SemanticException("No se puede acceder a un atributo de instancia en un metodo estatico", token, token.getLineNumber());
                }
            } else {
                returnType = MainSemantic.ST.getCurrentClass().getAttribute(token.getLexeme()).getType();
            }
        }
    }

    public void varIsLocalVar() {
        if(MainSemantic.ST.getCurrentBlock().getLocalVar(token.getLexeme()) != null) {
            returnType = MainSemantic.ST.getCurrentBlock().getLocalVar(token.getLexeme()).getType();
        }
        BlockNode parentBlock = MainSemantic.ST.getCurrentBlock().getParent();
        while(parentBlock != null){
            if(parentBlock.getLocalVar(token.getLexeme()) != null){
                returnType =  parentBlock.getLocalVar(token.getLexeme()).getType();
            }
            parentBlock = parentBlock.getParent();
        }
    }
}