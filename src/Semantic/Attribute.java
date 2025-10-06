package Semantic;


import Lexical.Token;

public class Attribute {
    private Token token;
    private Type type;
    private Token visibility;

    public Attribute(Token token,  Type type, Token visibility) {
        this.token = token;
        this.type = type;
        this.visibility = visibility;
    }

    public Token getToken(){
        return token;
    }

    public Type getType(){
        return type;
    }

    public Token getVisibility(){
        return visibility;
    }
}
