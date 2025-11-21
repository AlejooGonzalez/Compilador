package Semantic.Ast.Expressions.Access;

import Exceptions.SemanticException;
import Lexical.Token;
import Main.MainSemantic;
import Semantic.Ast.Chained.ChainedNode;
import Semantic.Ast.Sentences.BlockNode;
import Semantic.Ast.Sentences.LocalVarNode;
import Semantic.Attribute;
import Semantic.ConcreteClass;
import Semantic.Method;
import Semantic.Parameter;
import Semantic.Types.Type;

import java.util.LinkedHashMap;

public class VarAccessNode extends AccessNode {
    private Token token;
    private ChainedNode chaining;
    private Type returnType;
    private boolean isLeftSideOfAssign = false;

    private Attribute attribute= null;
    private Parameter parameter= null;
    private LocalVarNode localVar = null;
    private BlockNode block;

    public VarAccessNode(Token token, BlockNode block) {
        this.token = token;
        this.block = block;
    }

    @Override
    public Type check() throws SemanticException {
        setBlock(MainSemantic.ST.getCurrentBlock());
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

    public void setBlock(BlockNode block) {
        this.block = block;
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
        if(block!=null) {
            if (block.getMethod() != null && block.getMethod().getParameter(token.getLexeme()) != null) {
                returnType = block.getMethod().getParameter(token.getLexeme()).getType();
                parameter = block.getMethod().getParameter(token.getLexeme());
            }
        }
    }

    public void varIsAttribute() throws SemanticException {
        if(block!=null) {
            if (block.getConcreteClass() != null && block.getConcreteClass().getAttribute(token.getLexeme()) != null) {
                if (block.getMethod() != null && block.getMethod().getModifier() != null) {
                    if (!block.getMethod().getModifier().getLexeme().equals("static")) {
                        returnType = block.getConcreteClass().getAttribute(token.getLexeme()).getType();
                        attribute = block.getConcreteClass().getAttribute(token.getLexeme());
                    } else {
                        throw new SemanticException("No se puede acceder a un atributo de instancia en un metodo estatico", token, token.getLineNumber());
                    }
                } else {
                    returnType = block.getConcreteClass().getAttribute(token.getLexeme()).getType();
                    attribute = block.getConcreteClass().getAttribute(token.getLexeme());
                }
            }
        }
    }

    public void varIsLocalVar() {
        if (block != null) {
            if (block.getLocalVar(token.getLexeme()) != null) {
                returnType = block.getLocalVar(token.getLexeme()).getType();
                localVar = block.getLocalVar(token.getLexeme());
            } else {
                BlockNode parentBlock = block.getParent();
                while (parentBlock != null) {
                    if (parentBlock.getLocalVar(token.getLexeme()) != null) {
                        returnType = parentBlock.getLocalVar(token.getLexeme()).getType();
                        localVar = parentBlock.getLocalVar(token.getLexeme());
                    }
                    parentBlock = parentBlock.getParent();
                }
            }
        }
    }
}