package Semantic.Ast.Expressions.Access;

import Exceptions.SemanticException;
import Lexical.Token;
import Main.MainSemantic;
import Semantic.Ast.Chained.ChainedNode;
import Semantic.Ast.Expressions.ExpressionNode;
import Semantic.ConcreteClass;
import Semantic.Method;
import Semantic.Types.Type;

import java.util.List;

public class StaticMethodAccessNode extends AccessNode{
    private Token staticClassToken;
    private Token staticMethodToken;
    private List<ExpressionNode> parameters;
    private ChainedNode chaining;
    private boolean isLeftSideOfAssign= false;

    public StaticMethodAccessNode(Token staticClassToken, Token staticMethodToken, List<ExpressionNode> parameters) {
        this.staticClassToken = staticClassToken;
        this.staticMethodToken = staticMethodToken;
        this.parameters = parameters;
    }

    @Override
    public Type check() throws SemanticException {
        ConcreteClass concreteClass = MainSemantic.ST.itIsAnExistingClass(staticClassToken);
        checkConcreteClassIsNull(concreteClass);

        Method method = concreteClass.itsAnExisistingMethod(staticMethodToken);
        checkMethodExistsInStaticClass(method);
        checkIfMethodIsStatic(method);
        method.sameArguments(parameters, staticClassToken);

        if(chaining != null){
            return chaining.check(method.getReturnType(),staticMethodToken);
        }
        return method.getReturnType();
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

    public ChainedNode getChaining() {
        return chaining;
    }

    @Override
    public void setItsLeftSide(boolean leftSide) {
        this.isLeftSideOfAssign = leftSide;
    }

    private void checkConcreteClassIsNull(ConcreteClass concreteClass) throws SemanticException {
        if(concreteClass == null) {
            throw new SemanticException("La clase estatica no existe", staticClassToken, staticClassToken.getLineNumber());
        }
    }

    private void checkMethodExistsInStaticClass(Method method) throws SemanticException {
        if(method == null){
            throw new SemanticException("El metodo no existe en la clase estatica",  staticMethodToken, staticMethodToken.getLineNumber());
        }
    }

    private void checkIfMethodIsStatic(Method method) throws SemanticException {
        if(!method.isStaticMethod()){
            throw new SemanticException("El metodo no es estatico",  staticMethodToken, staticMethodToken.getLineNumber());
        }
    }

    public void generate() {
        String className = staticClassToken.getLexeme();
        String methodName = staticMethodToken.getLexeme();
        String label = className + "_" + methodName;
        Method met = MainSemantic.ST.itIsAnExistingClass(staticClassToken).itsAnExisistingMethod(staticMethodToken);

        if (met.getReturnType()!=null && !met.getReturnType().getLexeme().equals("void")){
            MainSemantic.ST.getInstructionsList().add("RMEM 1");
        }

        if (parameters != null) {
            for (ExpressionNode expr : parameters) {
                expr.generate();
            }
        }

        MainSemantic.ST.getInstructionsList().add("PUSH " + label);
        MainSemantic.ST.getInstructionsList().add("CALL");

        if (chaining != null) {
            if(this.isLeftSideOfAssign) {
                chaining.setItsLeftSide(true);
            }
            chaining.generate();
        }
    }
}
