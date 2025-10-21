package Semantic.Ast.Chained;

import Lexical.Token;
import Semantic.Types.Type;

public class ChainedCallNode extends ChainedNode {
    private Token token;
    private ChainedNode cad;
    private ChainedNode chaining;

    public ChainedCallNode(Token token) {
        this.token = token;
    }

    @Override
    public Type check(Type t) {
        return null;
    }

    @Override
    public void setChaining(ChainedNode chaining) {
        this.chaining = chaining;
    }
}
