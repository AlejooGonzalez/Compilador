package Semantic.Types;

import Exceptions.SemanticException;
import Lexical.Token;

public interface Type {
    public Token getToken();
    public void setToken(Token tokenType);
    public String getLexeme();
    public boolean isPrimitive();
    public boolean conformsWith(Type other);
    public boolean itsCompatible(Type booleanType);
}
