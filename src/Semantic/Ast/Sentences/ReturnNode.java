package Semantic.Ast.Sentences;

import Exceptions.SemanticException;
import Lexical.Token;
import Semantic.Ast.Expressions.EmptyExpression;
import Semantic.Ast.Expressions.ExpressionNode;
import Semantic.Types.Type;
import Semantic.Types.VoidType;

public class ReturnNode extends SentenceNode {
    private ExpressionNode exp;
    private Token token;
    private Type returnMethodExpected;

    public ReturnNode(Token token){
        this.token = token;
    }

    public void setOptionalExpression(ExpressionNode exp){
        if(exp != null)
            this.exp = exp;
    }

    public void setReturnExpected(Type returnExpected){
        this.returnMethodExpected = returnExpected;
    }


    @Override
    public void check() throws SemanticException {
        Type expressionType = exp.check();
        if(returnMethodExpected.isPrimitive()) {
            if (voidReturnMethod()) {
                if (!checkReturnVoidMethod(expressionType))
                    throw new SemanticException("Un metodo con retorno de tipo 'void' no debe tener return", token, token.getLineNumber());
            } else {
                if (!checkReturnMethodCoincides(expressionType)) {
                    throw new SemanticException("No coincide el retorno con el tipo de retorno del metodo", token, token.getLineNumber());
                }
            }
        } else {
            if(!expressionType.getLexeme().equals("null")) {
                if (!checkReturnMethodCoincidesClass(expressionType)) {
                    throw new SemanticException("El tipo de retorno no conforma con el tipo del método", token, token.getLineNumber());
                }
            }
        }
    }

    public boolean voidReturnMethod() {
        return returnMethodExpected.itsCompatible(new VoidType(token.getLineNumber()));
    }

    public boolean checkReturnVoidMethod(Type expressionType) {
        return (expressionType.itsCompatible(new EmptyExpression().check()));
    }

    public boolean checkReturnMethodCoincides(Type expressionType) {
        return ((expressionType != null) && returnMethodExpected.getLexeme().equals(expressionType.getLexeme()));
    }

    public boolean checkReturnMethodCoincidesClass(Type expressionType) {
        return returnMethodExpected.conformsWith(expressionType);
    }
}
