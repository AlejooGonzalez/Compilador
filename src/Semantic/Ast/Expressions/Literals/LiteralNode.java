package Semantic.Ast.Expressions.Literals;

import Semantic.Ast.Expressions.OperatorNode;
import Semantic.Types.Type;

public abstract class LiteralNode extends OperatorNode {
    abstract public Type check();
}
