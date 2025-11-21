package Semantic.Ast.Chained;

import Exceptions.SemanticException;
import Lexical.Token;
import Main.MainSemantic;
import Semantic.Attribute;
import Semantic.ConcreteClass;
import Semantic.Types.Type;

public class ChainedVariableNode extends ChainedNode {
    private Token token;
    private ChainedNode chaining;
    private boolean isLeftSideOfAssign= false;
    private Attribute attribute;

    public ChainedVariableNode(Token token) {
        this.token = token;
    }

    @Override
    public Type check(Type leftSide, Token leftToken) throws SemanticException {
        if (leftSide.isPrimitive()) {
            throw new SemanticException("No se puede acceder a atributos de un tipo primitivo", leftToken, token.getLineNumber());
        }
        ConcreteClass leftClass = MainSemantic.ST.itIsAnExistingClass(leftSide.getToken());
        if (leftClass == null) {
            throw new SemanticException("Clase no declarada", leftSide.getToken(), leftToken.getLineNumber());
        }
        attribute = leftClass.itsAnExisistingAttribute(token);
        if (attribute == null) {
            throw new SemanticException("El atributo no existe en la clase", token, token.getLineNumber());
        }
        Type attrType = attribute.getType();
        if (chaining != null) {
            return chaining.check(attrType, token);
        }
        return attrType;
    }

    @Override
    public void setChaining(ChainedNode chaining) {
        this.chaining = chaining;
    }

    @Override
    public ChainedNode getChaining() {
        return chaining;
    }

    @Override
    public void generate() {
        if (!isLeftSideOfAssign || chaining != null){
            MainSemantic.ST.getInstructionsList().add("LOADREF " + attribute.getOffset());
        } else {
            MainSemantic.ST.getInstructionsList().add("SWAP");
            MainSemantic.ST.getInstructionsList().add("STOREREF " + attribute.getOffset());
        }

        if (chaining != null) {
            if (isLeftSideOfAssign) {
                chaining.setItsLeftSide(true);
            }
            chaining.generate();
        }
    }

    @Override
    public void setItsLeftSide(boolean leftSide) {
        this.isLeftSideOfAssign = leftSide;
    }
}
