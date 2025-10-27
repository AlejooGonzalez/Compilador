package Semantic.Ast.Sentences;

import Exceptions.SemanticException;
import Lexical.Token;
import Semantic.Ast.Chained.ChainedCallNode;
import Semantic.Ast.Chained.ChainedNode;
import Semantic.Ast.Expressions.Access.AccessNode;
import Semantic.Ast.Expressions.Access.MethodAccessNode;
import Semantic.Ast.Expressions.Access.StaticMethodAccessNode;
import Semantic.Ast.Expressions.AssignationExpressionNode;
import Semantic.Ast.Expressions.ExpressionNode;

public class AssignationNode extends SentenceNode{
    Token token;
    ExpressionNode expressionNode;

    public AssignationNode(Token token, ExpressionNode expressionNode) {
        this.token = token;
        this.expressionNode = expressionNode;
    }

    @Override
    public void check() throws SemanticException {
        expressionNode.check();
        if (!sentenceWithEffect()) {
            throw new SemanticException("Expresión no permitida como sentencia (no tiene efecto)", expressionNode.getToken(), token.getLineNumber());
        }
        lastIsMethod();
    }

    private void lastIsMethod() throws SemanticException {
        if (expressionNode instanceof AccessNode access) {
            if (access.getChaining() != null) {
                if (!endsInMethod(access.getChaining())) {
                    throw new SemanticException("Encadenado sin efecto (no termina en método)", token, token.getLineNumber());
                }
            }
        }
    }

        private boolean endsInMethod(ChainedNode node){
            ChainedNode current = node;
            while (current.getChaining() != null) {
                current = current.getChaining();
            }
            return current instanceof ChainedCallNode;
        }

        public boolean sentenceWithEffect(){
            return (expressionNode instanceof AssignationExpressionNode || expressionNode instanceof MethodAccessNode || expressionNode instanceof StaticMethodAccessNode);
        }
    }
