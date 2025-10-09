package Semantic;
import Exceptions.SemanticException;
import Lexical.Token;
import Main.MainSemantic;

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

    public void itIsWellStated() throws SemanticException {
        if (!type.isPrimitive()) {
            Token typeToken = type.getToken();
            if (MainSemantic.ST.existsClass(typeToken) == null) {
                throw new SemanticException("El tipo " + typeToken.getLexeme() + " no está declarado", typeToken, typeToken.getLineNumber());
            }
        }
    }
}
