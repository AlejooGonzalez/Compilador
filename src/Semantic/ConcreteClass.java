package Semantic;

import Exceptions.SemanticException;
import Lexical.Token;

import java.util.HashMap;

    public class ConcreteClass {
        private Token token;
        private Token inheritance;
        private HashMap<String,Method> methods;
        private HashMap<String,Attribute> attributes;

    public ConcreteClass(Token token) {
        this.token = token;
        this.methods = new HashMap<>();
        this.attributes = new HashMap<>();
    }

    public Token getToken() {
        return token;
    }

    public HashMap<String,Attribute> getAttributes() {
        return attributes;
    }

    public HashMap<String,Method> getMethods() {
        return methods;
    }

    public String getInheritance() {
        return inheritance;
    }


    public void addMethod(Method m) throws SemanticException {
        if(!methods.containsKey(m.getName())) {
            methods.put(m.getName(), m);
        } else {
            throw new SemanticException("Method already exists",m.getToken());
        }
    }

    public void setInheritance(Token inheritance) {
       this.inheritance = inheritance;
    }

    public String getName() {
        return token.getLexeme();
    }
    }
