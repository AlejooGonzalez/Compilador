package Semantic;

import Exceptions.SemanticException;
import Lexical.Token;
import Main.MainSyntactic;

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

    public void setHasBlock(boolean hasBody) {
        this.hasBody = hasBody;
    }

    public void itIsWellStated() throws SemanticException {
        if (returnType != null && !returnType.isPrimitive()) {
            Token typeToken = returnType.getToken();
            if (MainSyntactic.ST.existsClass(typeToken) == null) {
                throw new SemanticException("El tipo de retorno " + typeToken.getLexeme() + " no está declarado", typeToken, typeToken.getLineNumber());
            }
        }

        for (Parameter p : parameters.values()) {
            p.itIsWellStated();
        }

        if(modifier != null) {
            String modType = modifier.getTokenType();
            if (modType.equals("pr_abstract") && hasBody) {
                throw new SemanticException("Un método abstracto no puede tener cuerpo", token, token.getLineNumber());
            }
        }
    }
}
