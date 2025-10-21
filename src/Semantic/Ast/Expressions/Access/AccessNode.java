package Semantic.Ast.Expressions.Access;

import Semantic.Ast.Chained.ChainedNode;
import Semantic.Ast.Expressions.OperatorNode;
import Semantic.Types.Type;

public abstract class AccessNode extends OperatorNode {
    abstract public Type check();
    abstract public void setChaining(ChainedNode chaining);
}
