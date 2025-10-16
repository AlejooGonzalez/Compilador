package Semantic.Types;

import Lexical.Token;

public class ReferenceType implements Type{
    private Token token;
    private String name;

    public ReferenceType(Token token){
        this.token = token;
        this.name = token.getLexeme();
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token tokenType) {
        token = tokenType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPrimitive() {
        return false;
    }
}
