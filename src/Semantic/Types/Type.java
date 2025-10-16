package Semantic.Types;

import Exceptions.SemanticException;
import Lexical.Token;

public interface Type {
    public Token getToken();
    public void setToken(Token tokenType);
    public String getName();
    public void setName(String name);
    public boolean isPrimitive();
    void itsCompatible(String prBoolean) throws SemanticException;
}
