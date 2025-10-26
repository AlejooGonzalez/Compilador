package Semantic.Ast.Chained;

import Exceptions.SemanticException;
import Lexical.Token;
import Semantic.Types.Type;

public abstract class ChainedNode {
    abstract public Type check(Type leftType, Token leftToken) throws SemanticException;
    abstract public void setChaining(ChainedNode chaining);
}
