package Semantic.Types;

import Exceptions.SemanticException;
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

    @Override
    public boolean itsCompatible(String prBoolean) throws SemanticException {
        if(!prBoolean.equals(token.getLexeme())){
            throw new SemanticException("Asignacion de distinto tipo", token, token.getLineNumber());
        } else {
            return true;
        }
    }

    @Override
    public boolean conformsWith(Type other) {
        return false;
    }


}
