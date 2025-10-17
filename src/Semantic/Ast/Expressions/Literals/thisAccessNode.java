package Semantic.Ast.Expressions.Literals;

import Lexical.Token;
import Semantic.Ast.Expressions.OperatorNode;
import Semantic.Types.Type;

public class thisAccessNode extends OperatorNode {
    Token tokenThis;

    public thisAccessNode(Token tokenThis) {
        this.tokenThis = tokenThis;
    }

    @Override
    public Type check() {
        return null;
    }
}
