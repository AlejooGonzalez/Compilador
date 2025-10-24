package Semantic.Ast.Expressions.Access;

import Exceptions.SemanticException;
import Lexical.Token;
import Main.MainSemantic;
import Semantic.Ast.Chained.ChainedNode;
import Semantic.Ast.Expressions.ExpressionNode;
import Semantic.ConcreteClass;
import Semantic.Types.ReferenceType;
import Semantic.Types.Type;

import java.util.ArrayList;
import java.util.List;

public class ConstructorAccessNode extends AccessNode {
    private Token classToken;
    private ConcreteClass constructorClass;
    private List<ExpressionNode> arguments;
    private ChainedNode chaining;

    public ConstructorAccessNode(Token classToken) {
        this.classToken = classToken;
        arguments = new ArrayList<>();
    }

    @Override
    public Type check() throws SemanticException {
        if(MainSemantic.ST.itIsAnExistingClass(classToken) == null){
            throw new SemanticException("la clase a la cual hace referencia, no existe", classToken, classToken.getLineNumber());
        }
        ReferenceType referenceType = new ReferenceType(classToken);
        if(chaining == null) {
            return referenceType;
        } else {
            return chaining.check(referenceType);
        }
    }

    @Override
    public int getLine() {
        return classToken.getLineNumber();
    }

    @Override
    public Token getToken() {
        return classToken;
    }

    public void setArguments(List<ExpressionNode> arguments) {
        this.arguments = arguments;
    }

    public void setChaining(ChainedNode chaining) {
        this.chaining = chaining;
    }
}
