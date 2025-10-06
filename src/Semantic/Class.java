package Semantic;

import Lexical.Token;

import java.util.HashMap;

public class Class {
    private Token token;
    private String inheritance;
    private HashMap<String,Method> methods;
    private HashMap<String,Attribute> attributes;

    public Class(Token token, String inheritance) {
        this.token = token;
        this.methods = new HashMap<>();
        this.attributes = new HashMap<>();
        this.inheritance = inheritance;
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
}
