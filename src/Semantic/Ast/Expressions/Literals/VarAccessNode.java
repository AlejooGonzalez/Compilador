package Semantic.Ast.Expressions.Literals;

import Lexical.Token;
import Semantic.Ast.Expressions.OperatorNode;
import Semantic.Types.Type;

public class VarAccessNode extends OperatorNode {
    private Token token;

    public VarAccessNode(Token token) {
        this.token = token;
    }

    @Override
    public Type check() {
        return null;
    }
}
