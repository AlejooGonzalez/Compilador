package Semantic.Types;

import Lexical.Token;

public class PrimitiveType implements Type{
    private Token token;

    public PrimitiveType(Token token){
        this.token = token;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token tokenType) {
        token = tokenType;
    }

    public String getLexeme() {
        return token.getLexeme();
    }

    public boolean isPrimitive(){
        return true;
    }

    @Override
    public boolean conformsWith(Type other) {
        return other instanceof  PrimitiveType;
    }
}