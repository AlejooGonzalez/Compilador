package Semantic.Ast.Expressions.Literals;

import Lexical.Token;
import Semantic.Ast.Expressions.ExpressionNode;
import Semantic.Ast.Expressions.OperatorNode;
import Semantic.Types.Type;

import java.util.List;

public class StaticMethodAccessNode extends OperatorNode{
    private Token staticClassToken;
    private Token staticMethodToken;
    private List<ExpressionNode> parameters;

    public  StaticMethodAccessNode(Token staticClassToken, Token staticMethodToken, List<ExpressionNode> parameters) {
        this.staticClassToken = staticClassToken;
        this.staticMethodToken = staticMethodToken;
        this.parameters = parameters;
    }

    @Override
    public Type check() {
        return null;
    }
}
