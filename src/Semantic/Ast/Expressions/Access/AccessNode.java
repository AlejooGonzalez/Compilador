package Semantic.Ast.Expressions.Access;

import Exceptions.SemanticException;
import Semantic.Ast.Chained.ChainedNode;
import Semantic.Ast.Expressions.OperandNode;
import Semantic.Types.Type;

public abstract class AccessNode extends OperandNode {
    abstract public Type check() throws SemanticException;
    abstract public void setChaining(ChainedNode chaining);
    abstract public ChainedNode getChaining();
    abstract public void setItsLeftSide(boolean leftSide);
}
