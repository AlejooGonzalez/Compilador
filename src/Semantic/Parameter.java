package Semantic;

import Exceptions.SemanticException;
import Lexical.Token;
import Main.MainSemantic;

public class Parameter {
    private Token token;
    private Type type;

    public Parameter(Token token, Type type) {
        this.token = token;
        this.type = type;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public String getName() {
        return token.getLexeme();
    }

    public void itIsWellStated() throws SemanticException {
        if (!type.isPrimitive()) {
            Token typeToken = type.getToken();
            if (MainSemantic.ST.itIsAnExistingClass(typeToken) == null) {
                throw new SemanticException("El tipo de retorno " + typeToken.getLexeme() + " no está declarado", typeToken, typeToken.getLineNumber());
            }
        }
    }
}

