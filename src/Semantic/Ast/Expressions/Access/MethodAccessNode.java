package Semantic.Ast.Expressions.Access;

import Lexical.Token;
import Semantic.Ast.Chained.ChainedNode;
import Semantic.Ast.Expressions.ExpressionNode;
import Semantic.Types.Type;

import java.util.List;

public class MethodAccessNode extends AccessNode {
    private List<ExpressionNode> currentParamList;
    private Token tokenIdMetVar;
    private ChainedNode chaining;


    public MethodAccessNode(Token tokenIdMetVar, List<ExpressionNode> currentParamList) {
        this.tokenIdMetVar = tokenIdMetVar;
        this.currentParamList = currentParamList;
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
