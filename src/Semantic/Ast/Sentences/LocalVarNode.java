package Semantic.Ast.Sentences;

import Exceptions.SemanticException;
import Lexical.Token;
import Main.MainSemantic;
import Semantic.Ast.Expressions.ExpressionNode;
import Semantic.Types.Type;

public class LocalVarNode extends SentenceNode {
    private Token token;
    private ExpressionNode expression;
    private Type type;

    public LocalVarNode(Token token) {
        this.token = token;
    }

    @Override
    public void check() throws SemanticException {
        if(MainSemantic.ST.getCurrentMethod().getParameters().containsKey(token.getLexeme())){
            throw new SemanticException("La variable ya fue declarada en los parametros", token, token.getLineNumber());
        }
        if(MainSemantic.ST.getCurrentClass().getAttribute(token.getLexeme()) != null){
            throw new SemanticException("Local Var ya declarada en atributos", token, token.getLineNumber());
        }
        checkLocalVarInParentNode();
        type = expression.check();
        MainSemantic.ST.getCurrentBlock().addLocalVariables(token.getLexeme(), this);
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token nombre) {
        this.token = nombre;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void checkLocalVarInParentNode() throws SemanticException {
        BlockNode parentBlock = MainSemantic.ST.getCurrentBlock().getParent();
        while(parentBlock != null){
            if(parentBlock.getLocalVar(token.getLexeme()) != null){
                throw new SemanticException("Variable declarada en un bloque padre", token, token.getLineNumber());
            }
            parentBlock = parentBlock.getParent();
        }
    }

    public void setExpression(ExpressionNode expression) {
        this.expression = expression;
    }
}