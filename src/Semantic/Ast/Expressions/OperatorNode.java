package Semantic.Ast.Expressions;

import Semantic.Types.Type;

public abstract class OperatorNode extends ExpressionNode {
    abstract public Type check();
}
