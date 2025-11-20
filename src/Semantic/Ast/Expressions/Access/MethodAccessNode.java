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
    private boolean isLeftSideOfAssign= false;

    public MethodAccessNode(Token tokenIdMetVar, List<ExpressionNode> currentParamList) {
        this.tokenIdMetVar = tokenIdMetVar;
        this.currentParamList = currentParamList;
    }

    @Override
    public Type check() throws SemanticException {
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

    @Override
    public void generate() {
        Method m = MainSemantic.ST.getCurrentClass().itsAnExisistingMethod(tokenIdMetVar);
        if (currentParamList != null) {
            for (ExpressionNode exp : currentParamList) {
                exp.generate();
            }
        }
        MainSemantic.ST.getInstructionsList().add("LOAD 3");
        int offset = m.getOffset();
        MainSemantic.ST.getInstructionsList().add("LOAD 3");
        MainSemantic.ST.getInstructionsList().add("LOADREF 0");
        MainSemantic.ST.getInstructionsList().add("LOADREF " + offset + "  ; cargo dirección del método dinámico");
        MainSemantic.ST.getInstructionsList().add("CALL; llamada dinámica");

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

    public boolean itsAnStaticMethod() {
        if (MainSemantic.ST.getCurrentMethod().getModifier() != null) {
            if (MainSemantic.ST.getCurrentMethod().getModifier().getLexeme().equals("static")) {
                return true;
            }
        }
        return false;
    }
}
