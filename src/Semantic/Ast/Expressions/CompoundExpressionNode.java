package Semantic.Ast.Expressions;

import Exceptions.SemanticException;
import Semantic.Types.Type;

public abstract class CompoundExpressionNode extends ExpressionNode{
    abstract public Type check() throws SemanticException;
}
