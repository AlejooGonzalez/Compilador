package Semantic.Ast.Expressions.Access;

import Exceptions.SemanticException;
import Lexical.Token;
import Main.MainSemantic;
import Semantic.Ast.Chained.ChainedNode;
import Semantic.ConcreteClass;
import Semantic.Method;
import Semantic.Types.ReferenceType;
import Semantic.Types.Type;

public class thisAccessNode extends AccessNode {
    private Token tokenThis;
    private ChainedNode chaining;

    public thisAccessNode(Token tokenThis) {
        this.tokenThis = tokenThis;
    }

    @Override
    public Type check() throws SemanticException {
        Method currentMethod = MainSemantic.ST.getCurrentMethod();
        checkIsStaticMethod(currentMethod);

        ConcreteClass currentClass = MainSemantic.ST.getCurrentClass();
        Type thisType = new ReferenceType(currentClass.getToken());
        if(chaining != null){
            return chaining.check(thisType, tokenThis);
        } else {
            return thisType;
        }
    }

    @Override
    public int getLine() {
        return tokenThis.getLineNumber();
    }

    @Override
    public Token getToken() {
        return tokenThis;
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

    private void checkIsStaticMethod(Method currentMethod) throws SemanticException {
        if(currentMethod.getModifier() != null ) {
            if (currentMethod.getModifier().getLexeme().equals("static")) {
                throw new SemanticException("No se puede utilizar this en un metodo estatico", tokenThis, tokenThis.getLineNumber());
            }
        }
    }
}
