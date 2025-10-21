package Semantic.Ast.Expressions.Access;

import Lexical.Token;
import Semantic.Ast.Chained.ChainedNode;
import Semantic.Types.Type;

public class thisAccessNode extends AccessNode {
    Token tokenThis;
    private ChainedNode chaining;


    public thisAccessNode(Token tokenThis) {
        this.tokenThis = tokenThis;
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
