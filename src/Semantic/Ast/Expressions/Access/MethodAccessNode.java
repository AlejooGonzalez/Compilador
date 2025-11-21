package Semantic.Ast.Expressions.Access;

import Exceptions.SemanticException;
import Lexical.Token;
import Main.MainSemantic;
import Semantic.Ast.Chained.ChainedNode;
import Semantic.Ast.Expressions.ExpressionNode;
import Semantic.Ast.Sentences.BlockNode;
import Semantic.Method;
import Semantic.Types.Type;

import java.util.List;

public class MethodAccessNode extends AccessNode {
    private List<ExpressionNode> currentParamList;
    private Token tokenIdMetVar;
    private ChainedNode chaining;
    private BlockNode block;
    private boolean isLeftSideOfAssign= false;

    public MethodAccessNode(Token tokenIdMetVar, List<ExpressionNode> currentParamList, BlockNode block) {
        this.tokenIdMetVar = tokenIdMetVar;
        this.currentParamList = currentParamList;
        this.block = block;
    }

    @Override
    public Type check() throws SemanticException {
        setBlock(MainSemantic.ST.getCurrentBlock());
        Method method = MainSemantic.ST.getCurrentClass().itsAnExisistingMethod(tokenIdMetVar);
        if (method == null) {
            throw new SemanticException("El metodo no existe", tokenIdMetVar, tokenIdMetVar.getLineNumber());
        } else {
            method.sameArguments(currentParamList, tokenIdMetVar);
        } /*
        if (itsAnStaticMethod()) {
            throw new SemanticException("No se puede acceder a un metodo en un metodo estatico", tokenIdMetVar, tokenIdMetVar.getLineNumber());
        } */
        if (chaining != null) {
            return chaining.check(method.getReturnType(), tokenIdMetVar);
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

    public void setBlock(BlockNode block) {
        this.block = block;
    }

    @Override
    public void generate() {
        Method m = MainSemantic.ST.getCurrentClass().itsAnExisistingMethod(tokenIdMetVar);
            if (m.isStaticMethod()) {
                generateStaticMethod(m);
            } else {
                generateDynamicMethod(m);
            }

            if (chaining != null) {
                if (isLeftSideOfAssign) {
                    chaining.setItsLeftSide(true);
                }
                chaining.generate();
            }
    }

    public void generateDynamicMethod(Method m) {
        MainSemantic.ST.getInstructionsList().add("LOAD 3");
        if(!m.getReturnType().getLexeme().equals("void")){
            MainSemantic.ST.getInstructionsList().add("RMEM 1");
            MainSemantic.ST.getInstructionsList().add("SWAP");
        }
        for(ExpressionNode e : currentParamList) {
            e.generate();
            MainSemantic.ST.getInstructionsList().add("SWAP");
        }
        MainSemantic.ST.getInstructionsList().add("DUP");
        MainSemantic.ST.getInstructionsList().add("LOADREF 0");
        MainSemantic.ST.getInstructionsList().add("LOADREF " + m.getOffset());
        MainSemantic.ST.getInstructionsList().add("CALL");
    }

    public void generateStaticMethod(Method m) {
        if(m.getReturnType() != null && !m.getReturnType().getLexeme().equals("void")){
            MainSemantic.ST.getInstructionsList().add("RMEM 1");
        }
        for (ExpressionNode p : currentParamList){
            p.generate();
        }
        MainSemantic.ST.getInstructionsList().add("PUSH " + m.getLabel());
        MainSemantic.ST.getInstructionsList().add("CALL");
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

    public boolean itsAnStaticMethod() {
        if (MainSemantic.ST.getCurrentMethod().getModifier() != null) {
            if (MainSemantic.ST.getCurrentMethod().getModifier().getLexeme().equals("static")) {
                return true;
            }
        }
        return false;
    }
}
