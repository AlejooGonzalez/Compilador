package Semantic;

import Exceptions.SemanticException;
import Lexical.Token;
import Semantic.Ast.Sentences.BlockNode;

import java.util.HashMap;

public class Constructor {
    private Token token;
    private HashMap<String, Parameter> parameters;
    private Token classToken;
    private BlockNode block;

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

    public void setClassName(Token classToken) {
        this.classToken = classToken;
    }

    public void addParameter(Parameter param) throws SemanticException {
        if (parameters.get(param.getLexeme()) == null) {
            parameters.put(param.getLexeme(), param);
        } else {
            throw new SemanticException("El parametro "+param.getToken()+" ya existe", param.getToken(), param.getToken().getLineNumber());
        }
    }

    public void itIsWellStated() throws SemanticException {
        if (!token.getLexeme().equals(classToken.getLexeme())){
            throw new SemanticException("Nombre incorrecto en constructor ",token, token.getLineNumber());
        }
        for(Parameter p : parameters.values()){
            p.itIsWellStated();
        }
    }

    public void check() throws SemanticException{
        if(block != null){
            block.check();
        }
    }
}
