package Semantic;

import Exceptions.SemanticException;
import Lexical.Token;
import Main.MainSemantic;
import Semantic.Ast.Sentences.BlockNode;
import Semantic.Types.Type;

import java.util.HashMap;
import java.util.Objects;

public class Method {
    private Token token;
    private Token modifier;
    private Type returnType;
    private HashMap<String,Parameter> parameters;
    private boolean hasBody;
    private BlockNode block;

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

    public void addParameters(Parameter p) throws SemanticException {
        if (parameters.containsKey(p.getName())) {
            throw new SemanticException("El parámetro '" + p.getName() + "' está duplicado en el método '" + this.getName() + "'", p.getToken(), p.getToken().getLineNumber());
        }
        parameters.put(p.getName(), p);
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

    public boolean getHasBlock() {
        return hasBody;
    }

    public void itIsWellStated() throws SemanticException {
        if (returnType != null && !returnType.isPrimitive()) {
            Token typeToken = returnType.getToken();
            if (MainSemantic.ST.itIsAnExistingClass(typeToken) == null) {
                throw new SemanticException("El tipo de retorno " + typeToken.getLexeme() + " no está declarado", returnType.getToken(), returnType.getToken().getLineNumber());
            }
        } else {
                if(!checkMethodBody()){
                    throw new SemanticException("El metodo " + token.getLexeme() + " no tiene cuerpo", token, token.getLineNumber());
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

    public Token getModifier() {
        return modifier;
    }

    public Type getReturnType() {
        return returnType;
    }

    public boolean sameParameters(Method metFather) {
        boolean ret = true;
        if (this.parameters.size() != metFather.parameters.size()) {
            ret = false;
        }
        var it1 = this.parameters.values().iterator();
        var it2 = metFather.parameters.values().iterator();
        while (it1.hasNext() && it2.hasNext() && ret) {
            Parameter p1 = it1.next();
            Parameter p2 = it2.next();
            if (!p1.getToken().getTokenType().equals(p2.getToken().getTokenType())) {
                ret = false;
            }
        }
        return ret;
    }

    public boolean checkMethodBody(){
        boolean retorno = true;
        if(modifier != null){
            if (returnType != null && !hasBody && !Objects.equals(modifier.getTokenType(), "pr_abstract")) {
                retorno = false;
            }
        } else {
            if (returnType != null && !hasBody){
                retorno = false;
            }
        }
        return retorno;
    }

    public void sentenceCheck() throws SemanticException {
        if(block != null)
            block.check();
    }

    public void setBlockNode(BlockNode block) {
        this.block = block;
    }
}
