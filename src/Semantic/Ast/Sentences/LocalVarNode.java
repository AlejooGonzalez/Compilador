package Semantic.Ast.Sentences;

import Exceptions.SemanticException;
import Lexical.Token;
import Semantic.Ast.Expressions.ExpressionNode;
import Semantic.Types.Type;

public class LocalVarNode extends SentenceNode {
    Token token;
    ExpressionNode value;
    Type type;

    public LocalVarNode(Token nombre) {
        this.token = nombre;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token nombre) {
        this.token = nombre;
    }

    @Override
    public void check() throws SemanticException {

    }

    public ExpressionNode getExpresion() {
        return value;
    }

    public void setExpresion(ExpressionNode value) {
        this.value = value;
    }

    public Type getTipo() {
        return type;
    }

    public void setTipo(Type type) {
        this.type = type;
    }
}