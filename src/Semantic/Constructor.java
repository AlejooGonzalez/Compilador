package Semantic;

import Lexical.Token;

import java.util.HashMap;
import java.util.HashSet;

public class Constructor{
    private Token token;
    private HashMap<String, Parameter> parameters;

    public Constructor(Token token) {
        this.token = token;
        parameters = new HashMap<>();
    }

    /*
    public void setParameters(HashMap<String, Parameter> parameters) {
        this.parameters = parameters;
    }

    public Parameter getParameter(String parameterName){
        return parameters.get(parameterName);
    }

    public void setParameter(String parameterName, Parameter parameter){
        parameters.put(parameterName, parameter);
    } */

    public String getName(){
        return token.getLexeme();
    }

    public int getLine(){
        return token.getLineNumber();
    }

    public HashMap<String, Parameter> getParameters() {
        return parameters;
    }



}
