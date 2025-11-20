package Semantic;

import Exceptions.SemanticException;
import Lexical.Token;
import Main.MainSemantic;
import Semantic.Ast.Sentences.BlockNode;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Constructor {
    private Token token;
    private LinkedHashMap<String, Parameter> parameters;
    private Token classToken;
    private BlockNode block;

    public Constructor(Token token) {
        this.token = token;
        parameters = new LinkedHashMap<>();
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

    public void generate(){
        MainSemantic.ST.setCurrentConstructor(this);
        int param = parameters.size() + 1;

        MainSemantic.ST.getInstructionsList().add(getLabel() + ": ");
        MainSemantic.ST.getInstructionsList().add("LOADFP");
        MainSemantic.ST.getInstructionsList().add("LOADSP");
        MainSemantic.ST.getInstructionsList().add("STOREFP");

        if(block != null) {
            block.generate();
        }

        MainSemantic.ST.getInstructionsList().add("STOREFP");
        MainSemantic.ST.getInstructionsList().add("RET " + param);
        MainSemantic.ST.getInstructionsList().add("");
    }

    public String getLabel(){
        return  "Constructor_" + token.getLexeme();
    }
}
