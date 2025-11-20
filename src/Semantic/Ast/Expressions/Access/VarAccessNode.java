package Semantic.Ast.Expressions.Access;

import Exceptions.SemanticException;
import Lexical.Token;
import Main.MainSemantic;
import Semantic.Ast.Chained.ChainedNode;
import Semantic.Ast.Sentences.BlockNode;
import Semantic.Ast.Sentences.LocalVarNode;
import Semantic.Attribute;
import Semantic.Parameter;
import Semantic.Types.Type;

public class VarAccessNode extends AccessNode {
    private Token token;
    private ChainedNode chaining;
    private Type returnType;
    private boolean isLeftSideOfAssign = false;

    private Attribute attribute;
    private Parameter parameter;
    private LocalVarNode localVar;

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
        if (localVar != null) {
            if(!isLeftSideOfAssign || chaining != null){
                MainSemantic.ST.getInstructionsList().add("LOAD " + localVar.getOffset());
            }else{
                MainSemantic.ST.getInstructionsList().add("STORE " + localVar.getOffset());
            }
        }
        else if (parameter != null) {
            if (!isLeftSideOfAssign || chaining != null) {
                MainSemantic.ST.getInstructionsList().add("LOAD " + parameter.getOffset());
            } else {
                MainSemantic.ST.getInstructionsList().add("STORE " + parameter.getOffset());
            }
        } else if (attribute != null) {
            MainSemantic.ST.getInstructionsList().add("LOAD 3");
            if (!isLeftSideOfAssign || chaining != null) {
                MainSemantic.ST.getInstructionsList().add("LOADREF " + attribute.getOffset());
            } else {
                MainSemantic.ST.getInstructionsList().add("SWAP");
                MainSemantic.ST.getInstructionsList().add("STOREREF " + attribute.getOffset());
            }
        }
        if (chaining != null) {
            if(this.isLeftSideOfAssign) {
                chaining.setItsLeftSide(true);
            }
            chaining.generate();
        }
    }

    public void setChaining(ChainedNode chaining) {
        this.chaining = chaining;
    }

    @Override
    public ChainedNode getChaining() {
        return chaining;
    }

    @Override
    public void setItsLeftSide(boolean leftSide) {
        this.isLeftSideOfAssign = leftSide;
    }

    public void varIsParameter(){
        if(MainSemantic.ST.getCurrentMethod().getParameter(token.getLexeme()) != null) {
            returnType = MainSemantic.ST.getCurrentMethod().getParameter(token.getLexeme()).getType();
            parameter = MainSemantic.ST.getCurrentMethod().getParameter(token.getLexeme());
        }
    }

    public void varIsAttribute() throws SemanticException {
        if(MainSemantic.ST.getCurrentClass().getAttribute(token.getLexeme()) != null) {
            if (MainSemantic.ST.getCurrentMethod().getModifier() != null) {
                if (!MainSemantic.ST.getCurrentMethod().getModifier().getLexeme().equals("static")) {
                    returnType = MainSemantic.ST.getCurrentClass().getAttribute(token.getLexeme()).getType();
                    attribute = MainSemantic.ST.getCurrentClass().getAttribute(token.getLexeme());
                } else {
                    throw new SemanticException("No se puede acceder a un atributo de instancia en un metodo estatico", token, token.getLineNumber());
                }
            } else {
                returnType = MainSemantic.ST.getCurrentClass().getAttribute(token.getLexeme()).getType();
                attribute = MainSemantic.ST.getCurrentClass().getAttribute(token.getLexeme());
            }
        }
    }

    public void varIsLocalVar() {
        if(MainSemantic.ST.getCurrentBlock().getLocalVar(token.getLexeme()) != null) {
            returnType = MainSemantic.ST.getCurrentBlock().getLocalVar(token.getLexeme()).getType();
            localVar =  MainSemantic.ST.getCurrentBlock().getLocalVar(token.getLexeme());
        }
        BlockNode parentBlock = MainSemantic.ST.getCurrentBlock().getParent();
        while(parentBlock != null){
            if(parentBlock.getLocalVar(token.getLexeme()) != null){
                returnType =  parentBlock.getLocalVar(token.getLexeme()).getType();
                localVar =   parentBlock.getLocalVar(token.getLexeme());
            }
            parentBlock = parentBlock.getParent();
        }
    }
}