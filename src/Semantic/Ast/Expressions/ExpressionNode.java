package Semantic.Ast.Expressions;

import Exceptions.SemanticException;
import Lexical.Token;
import Semantic.Types.Type;

public abstract class ExpressionNode {
    abstract public Type check() throws SemanticException;
    public abstract int getLine();
    public abstract Token getToken();
    public abstract void generate();
}
