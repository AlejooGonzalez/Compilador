package Semantic.Types;

import Exceptions.SemanticException;
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

    public String getLexeme() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPrimitive(){
        return true;
    }

    @Override
    public boolean conformsWith(Type other) {
        return other instanceof  PrimitiveType;
    }
}