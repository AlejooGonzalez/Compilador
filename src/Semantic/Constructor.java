package Semantic;

import Exceptions.SemanticException;
import Lexical.Token;

import java.util.HashMap;

public class Constructor {
    private Token token;
    private HashMap<String, Parameter> parameters;

    public Constructor(Token token) {
        this.token = token;
        parameters = new HashMap<>();
    }

    public String getLexeme() {
        return token.getLexeme();
    }

    public HashMap<String, Parameter> getParameters() {
        return parameters;
    }

    public Token getToken() {
        return token;
    }

    public void addParameter(Parameter param) throws SemanticException {
        if (parameters.get(param.getLexeme()) == null) {
            parameters.put(param.getLexeme(), param);
        } else {
            throw new SemanticException("El parametro "+param.getToken()+" ya existe", param.getToken(), param.getToken().getLineNumber());
        }
    }

    public void itIsWellStated() throws SemanticException {
        for(Parameter p : parameters.values()){
            p.itIsWellStated();
        }
    }
}
