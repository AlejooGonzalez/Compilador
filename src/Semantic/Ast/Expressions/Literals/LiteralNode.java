package Semantic.Ast.Expressions.Literals;

import Semantic.Ast.Expressions.OperandNode;
import Semantic.Types.Type;

public abstract class LiteralNode extends OperandNode {
    abstract public Type check();
    abstract public void generate();
}
