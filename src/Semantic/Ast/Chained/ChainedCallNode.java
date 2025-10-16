package Semantic.Ast.Chained;

import Lexical.Token;
import Semantic.Types.Type;

public class ChainedCallNode extends ChainedNode {
    private Token token;
    private ChainedNode cad;

    public ChainedCallNode(Token token, ChainedNode cad) {
        this.token = token;
        this.cad = cad;
    }

    @Override
    public Type check(Type t) {
        return null;
    }
}
