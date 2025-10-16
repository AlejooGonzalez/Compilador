package Semantic.Ast.Expressions;

import Semantic.Types.Type;

public abstract class ExpressionNode {
    abstract public Type check();
}
