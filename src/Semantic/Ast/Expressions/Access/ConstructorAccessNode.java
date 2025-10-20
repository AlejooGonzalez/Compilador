package Semantic.Ast.Expressions.Access;

import Lexical.Token;
import Semantic.Ast.Expressions.ExpressionNode;
import Semantic.Ast.Expressions.OperatorNode;
import Semantic.ConcreteClass;
import Semantic.Types.Type;

import java.util.ArrayList;
import java.util.List;

public class ConstructorAccessNode extends OperatorNode {
    Token token;
    ConcreteClass constructorClass;
    private List<ExpressionNode> arguments;

    public ConstructorAccessNode(Token token) {
        this.token = token;
        arguments = new ArrayList<>();
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

    public void setArguments(List<ExpressionNode> arguments) {
        this.arguments = arguments;
    }
}
