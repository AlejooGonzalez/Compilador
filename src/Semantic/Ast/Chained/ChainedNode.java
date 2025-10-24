package Semantic.Ast.Chained;

import Exceptions.SemanticException;
import Semantic.Types.Type;

public abstract class ChainedNode {
    abstract public Type check(Type t) throws SemanticException;
    abstract public void setChaining(ChainedNode chaining);
}
