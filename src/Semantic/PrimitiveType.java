package Semantic;

import Lexical.Token;

public class PrimitiveType implements Type{
    private Token token;
    private String name;

    public PrimitiveType(Token token){
        this.token = token;
        name = token.getLexeme();
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
}