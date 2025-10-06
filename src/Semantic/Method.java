package Semantic;

import Lexical.Token;

import java.util.HashMap;

public class Method {
    private Token token;
    private Token modifier;
    private Type returnType;
    private HashMap<String,Parameter> parameters;

    public Method(Token token, Token modifier, Type returnType){
        this.token = token;
        this.modifier = modifier;
        this.returnType = returnType;
        parameters =  new HashMap<>();
    }

    /*
    public HashMap<String,Parameter> getParameters() {
        return parameters;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public void setModifier(Token modifier) {
        this.modifier = modifier;
    }

    public void setReturnType(Type returnType) {
        this.returnType = returnType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParameters(HashMap<String,Parameter> parameters) {
        this.parameters = parameters;
    }
     */

    public String getName() {
        return token.getLexeme();
    }

    public int getLine() {
        return token.getLineNumber();
    }

    public Type getReturnType() {
        return returnType;
    }

    public Token getModifier() {
        return modifier;
    }

    public Token getToken() {
        return token;
    }


}
