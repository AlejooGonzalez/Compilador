package Semantic.Ast.Expressions.Access;

import Lexical.Token;
import Semantic.Ast.Chained.ChainedNode;
import Semantic.Types.Type;

public class VarAccessNode extends AccessNode {
    private Token token;
    private ChainedNode chaining;

    public VarAccessNode(Token token) {
        this.token = token;
    }

    @Override
    public Type check() {
        return null;
    }

    @Override
    public int getLine() {
        return 0;
    }

    @Override
    public Token getToken() {
        return null;
    }

    public void setChaining(ChainedNode chaining) {
        this.chaining = chaining;
    }
}