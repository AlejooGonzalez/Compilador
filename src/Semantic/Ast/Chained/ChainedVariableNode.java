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
        Attribute attr = leftClass.itsAnExisistingAttribute(token);
        if (attr == null) {
            throw new SemanticException("El atributo no existe en la clase", token, token.getLineNumber());
        }
        Type attrType = attr.getType();
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

    }
}