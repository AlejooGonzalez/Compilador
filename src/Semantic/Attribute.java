package Semantic;
import Exceptions.SemanticException;
import Lexical.Token;
import Main.MainSemantic;
import Semantic.Types.Type;

public class Attribute {
    private Token token;
    private Type type;
    private int offset;
    private boolean inherited = false;

    public Attribute(Token token,  Type type) {
        this.token = token;
        this.type = type;
    }

    public Token getToken(){
        return token;
    }

    public String getLexeme(){
        return token.getLexeme();
    }

    public void itIsWellStated() throws SemanticException {
        if (!type.isPrimitive()) {
            Token typeToken = type.getToken();
            if (MainSemantic.ST.itIsAnExistingClass(typeToken) == null) {
                throw new SemanticException("El tipo " + typeToken.getLexeme() + " no está declarado", typeToken, typeToken.getLineNumber());
            }
        }
    }

    public Type getType() {
        return type;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getOffset() {
        return offset;
    }

    public void markAsInherited() {
        inherited = true;
    }

    public boolean isInherited() {
        return inherited;
    }
}
