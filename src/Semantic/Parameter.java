package Semantic;

import Lexical.Token;

public class Parameter {
    private Token token;
    private Type type;

    public Parameter(Token token, Type type) {
        this.token = token;
        this.type = type;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getName() {
        return token.getLexeme();
    }
}

