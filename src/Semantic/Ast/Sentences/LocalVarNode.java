package Semantic.Ast.Sentences;

import Exceptions.SemanticException;
import Lexical.Token;
import Main.MainSemantic;
import Semantic.Ast.Expressions.ExpressionNode;
import Semantic.Types.NullType;
import Semantic.Types.Type;

public class LocalVarNode extends SentenceNode {
    private Token token;
    private ExpressionNode value;
    private Type type;

    public LocalVarNode(Token token) {
        this.token = token;
    }

    @Override
    public void check() throws SemanticException {
        if(value.check().equals(new NullType(token.getLineNumber()))){
            throw new SemanticException("variable de tipo nulo no valida", token, token.getLineNumber());
        }
        if(MainSemantic.ST.getCurrentMethod().getParameters().containsKey(token.getLexeme())){
            throw new SemanticException("La variable ya fue declarada en los parametros", token, token.getLineNumber());
        }
        if(MainSemantic.ST.getCurrentBlock().getLocalVar(token.getLexeme()) != null){
            throw new SemanticException("Local Var ya declarada", token, token.getLineNumber());
        }
        //FALTA VER SI UNA VARIABLE ESTA EN UN BLOQUE PADRE
        MainSemantic.ST.getCurrentBlock().addLocalVariables(token.getLexeme(), this);
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token nombre) {
        this.token = nombre;
    }

    public void setExpression(ExpressionNode value) {
        this.value = value;
    }

    public Type getTipo() {
        return type;
    }

    public void setTipo(Type type) {
        this.type = type;
    }
}