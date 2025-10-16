package Semantic.Ast.Chained;

import Lexical.Token;
import Semantic.Ast.Expressions.ExpressionNode;
import Semantic.Types.Type;
import java.util.List;

public class ChainedVariableNode extends ChainedNode {
    private Token token;
    private List<ExpressionNode> parameters;
    private ChainedNode cad;

    public ChainedVariableNode(Token token, List<ExpressionNode> parameters, ChainedNode cad) {
        this.token = token;
        this.parameters = parameters;
        this.cad = cad;
    }

    @Override
    public Type check(Type t) {
        return null;
    }
}