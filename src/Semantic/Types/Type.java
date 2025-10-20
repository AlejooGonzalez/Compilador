package Semantic.Types;

import Exceptions.SemanticException;
import Lexical.Token;

public interface Type {
    public Token getToken();
    public void setToken(Token tokenType);
    public String getName();
    public boolean isPrimitive();
    public boolean itsCompatible(String string) throws SemanticException;
    public boolean conformsWith(Type other);
}
