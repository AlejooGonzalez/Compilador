package Semantic.Ast.Expressions.Literals;

import Lexical.Token;
import Semantic.Ast.Expressions.ExpressionNode;
import Semantic.Ast.Expressions.OperatorNode;
import Semantic.Types.Type;

import java.util.List;

public class MethodAccesNode extends OperatorNode {
    private List<ExpressionNode> currentParamList;
    private Token tokenIdMetVar;

    public MethodAccesNode(Token tokenIdMetVar, List<ExpressionNode> currentParamList) {
        this.tokenIdMetVar = tokenIdMetVar;
        this.currentParamList = currentParamList;
    }

    @Override
    public Type check() {
        return null;
    }
}
