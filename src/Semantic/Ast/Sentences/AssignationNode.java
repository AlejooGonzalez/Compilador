package Semantic.Ast.Sentences;

import Exceptions.SemanticException;
import Lexical.Token;
import Main.MainSemantic;
import Semantic.Ast.Chained.ChainedCallNode;
import Semantic.Ast.Chained.ChainedNode;
import Semantic.Ast.Expressions.Access.AccessNode;
import Semantic.Ast.Expressions.Access.ConstructorAccessNode;
import Semantic.Ast.Expressions.Access.MethodAccessNode;
import Semantic.Ast.Expressions.Access.StaticMethodAccessNode;
import Semantic.Ast.Expressions.AssignationExpressionNode;
import Semantic.Ast.Expressions.ExpressionNode;
import Semantic.Types.Type;
import Semantic.Types.VoidType;

public class AssignationNode extends SentenceNode {
    private Token token;
    private ExpressionNode expressionNode;
    private Type expressionType;

    public AssignationNode(Token token, ExpressionNode expressionNode) {
        this.token = token;
        this.expressionNode = expressionNode;
    }

    @Override
    public void check() throws SemanticException {
        expressionType = expressionNode.check();
        if (!sentenceWithEffect()) {
            throw new SemanticException("Expresión no permitida como sentencia (no tiene efecto)", expressionNode.getToken(), token.getLineNumber());
        }
        checkChainingEndsInMethod();
    }

    @Override
    public void generate() {
        expressionNode.generate();
        if(!(expressionType.getLexeme().equals("void"))){
            if(!(expressionNode instanceof AssignationExpressionNode)){
                MainSemantic.ST.getInstructionsList().add("POP");
            }
        }
    }


    private void checkChainingEndsInMethod() throws SemanticException {
        if (expressionNode instanceof AccessNode access) {
            if (access.getChaining() != null) {
                if (!endsInMethod(access.getChaining())) {
                    throw new SemanticException("Encadenado sin efecto (no termina en método)", token, token.getLineNumber());
                }
            }
        }
    }

    private boolean endsInMethod(ChainedNode node) {
        ChainedNode current = node;
        while (current.getChaining() != null) {
            current = current.getChaining();
        }
        return current instanceof ChainedCallNode;
    }

    public boolean sentenceWithEffect() {
        if (expressionNode instanceof AssignationExpressionNode || expressionNode instanceof MethodAccessNode || expressionNode instanceof StaticMethodAccessNode || expressionNode instanceof ConstructorAccessNode) {
            return true;
        }
        if (expressionNode instanceof AccessNode access) {
            return chainingEndsInMethod(access);
        }
        return false;
    }

    private boolean chainingEndsInMethod(AccessNode access) {
        if (access.getChaining() == null) {
            return false;
        }
        ChainedNode current = access.getChaining();
        while (current.getChaining() != null) {
            current = current.getChaining();
        }
        return current instanceof ChainedCallNode;
    }
}
