package Semantic.Types;

import Exceptions.SemanticException;
import Lexical.Token;
import Semantic.ConcreteClass;

import java.util.HashMap;

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

    public String getLexeme() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPrimitive() {
        return false;
    }

    public boolean itsCompatible(String prBoolean) throws SemanticException {
        if(!prBoolean.equals(token.getLexeme())){
            throw new SemanticException("Asignacion de distinto tipo", token, token.getLineNumber());
        } else {
            return true;
        }
    }

    @Override
    public boolean conformsWith(Type other) {
        if (!(other instanceof ReferenceType))
            return false;
        else {
            String myName = this.getLexeme();
            String otherName = other.getLexeme();

            if (myName.equals("null") || myName.equals(otherName))
                return true;

            HashMap<String, ConcreteClass> classes = Main.MainSemantic.ST.getClasses();
            if (!classes.containsKey(myName) || !classes.containsKey(otherName)) {
                return false;
            } else {
                ConcreteClass current = classes.get(myName);
                if (otherName.equals("Object")) {
                    return true;
                } else {
                    while (current != null) {
                        Token parentToken = current.getInheritance();
                        if (parentToken == null) {
                            return false;
                        } else {
                            String parentName = parentToken.getLexeme();
                            if (parentName.equals(otherName)) {
                                return true;
                            }
                            current = classes.get(parentName);
                        }
                    }
                    return false;
                }
            }
        }
    }
}
