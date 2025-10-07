package Semantic;
import Lexical.Token;

public class Attribute {
    private Token token;
    private Type type;

    public Attribute(Token token,  Type type) {
        this.token = token;
        this.type = type;
    }

    public Token getToken(){
        return token;
    }

    public String getName(){
        return token.getLexeme();
    }
}
