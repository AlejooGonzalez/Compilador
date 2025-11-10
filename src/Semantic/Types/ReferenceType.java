package Semantic.Types;

import Exceptions.SemanticException;
import Lexical.Token;
import Semantic.ConcreteClass;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

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

    public boolean isPrimitive() {
        return false;
    }

    public boolean itsCompatible(Type prBoolean) {
        return prBoolean.getLexeme().equals(token.getLexeme());
    }

    @Override
    public boolean conformsWith(Type other) {
        String myName = this.getLexeme();
        String otherName = other.getLexeme();

        if (otherName.equals("null") || myName.equals(otherName)) {
            return true;
        }

        HashMap<String, ConcreteClass> classes = Main.MainSemantic.ST.getClasses();
        if (!classes.containsKey(myName) || !classes.containsKey(otherName)) {
            return false;
        }

        Set<String> visited = new HashSet<>();
        ConcreteClass current = classes.get(otherName);

        while (current != null && !visited.contains(current.getLexeme())) {
            visited.add(current.getLexeme());
            Token parentToken = current.getInheritance();

            if (parentToken == null)
                break;

            if (parentToken.getLexeme().equals(myName))
                return true;

            current = classes.get(parentToken.getLexeme());
        }
        return false;
    }
}
