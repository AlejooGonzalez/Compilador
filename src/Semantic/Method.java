package Semantic;

import Exceptions.SyntacticException;
import Lexical.Token;

import java.util.HashMap;

public class Method {
    private Token token;
    private Token modifier;
    private Type returnType;
    private HashMap<String,Parameter> parameters;
    private boolean hasBody;

    public Method(Token token, Token modifier, Type returnType){
        this.token = token;
        this.modifier = modifier;
        this.returnType = returnType;
        parameters =  new HashMap<>();
    }

    public HashMap<String,Parameter> getParameters() {
        return parameters;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public void addParameters(Parameter parameters) {
            this.parameters.put(parameters.getName(), parameters);
    }

    public String getName() {
        return token.getLexeme();
    }

    public Token getToken() {
        return token;
    }

    public void setHasBlock(boolean hasBlock) {
        this.hasBody = hasBlock;
    }
}
