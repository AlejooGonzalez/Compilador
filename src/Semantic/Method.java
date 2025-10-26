package Semantic;

import Exceptions.SemanticException;
import Lexical.Token;
import Main.MainSemantic;
import Semantic.Ast.Expressions.ExpressionNode;
import Semantic.Ast.Sentences.BlockNode;
import Semantic.Types.Type;

import java.util.HashMap;
import java.util.List;
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
        if (parameters.containsKey(p.getLexeme())) {
            throw new SemanticException("El parámetro '" + p.getLexeme() + "' está duplicado en el método '" + this.getLexeme() + "'", p.getToken(), p.getToken().getLineNumber());
        }
        parameters.put(p.getLexeme(), p);
    }

    public String getLexeme() {
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
        if (this.parameters.size() != metFather.getParameters().size()) {
            ret = false;
        }
        var it1 = this.parameters.values().iterator();
        var it2 = metFather.getParameters().values().iterator();
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
        MainSemantic.ST.setCurrentMethod(this);
        if(block != null) {
            block.check();
        }
    }

    public void setBlockNode(BlockNode block) {
        this.block = block;
    }

    public BlockNode getBlockNode() {
        return block;
    }

    public boolean isStaticMethod() {
        if(modifier != null) {
            return modifier.getLexeme().equals("static");
        }
        return false;
    }

    public void sameArguments(List<ExpressionNode> args, Token t) throws SemanticException {
        if (args != null && parameters != null) {
            if (args.size() != parameters.size()) {
                throw new SemanticException("No coinciden la cantidad de parametros con el metodo llamado", t, t.getLineNumber());
            }
            var formalIt = parameters.values().iterator();
            for (ExpressionNode arg : args) {
                Type argType = arg.check();
                Type formalType = formalIt.next().getType();
                if (!formalType.conformsWith(argType)) {
                    throw new SemanticException("No coincide el tipo de parametros con el metodo llamado", t, t.getLineNumber());
                }
            }
        } else {
            if (args != null && parameters.isEmpty()) {
                throw new SemanticException("No coincide el tipo de parametros con el metodo llamado", t, t.getLineNumber());
            } else {
                if(args == null && !parameters.isEmpty()){
                    throw new SemanticException("No coincide el tipo de parametros con el metodo llamado", t, t.getLineNumber());
                }
            }
        }
    }

    public Parameter getParameter(String lexeme) {
        return parameters.get(lexeme);
    }
}
