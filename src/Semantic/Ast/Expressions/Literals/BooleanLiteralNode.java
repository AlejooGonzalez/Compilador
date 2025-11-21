package Semantic.Ast.Expressions.Literals;

import Lexical.Token;
import Main.MainSemantic;
import Semantic.Types.BooleanType;
import Semantic.Types.Type;

public class BooleanLiteralNode extends LiteralNode {
    Token token;

    public BooleanLiteralNode(Token token) {
        this.token = token;
    }

    @Override
    public Type check() {
        return new BooleanType(token.getLineNumber());
    }

    @Override
    public int getLine() {
        return token.getLineNumber();
    }

    @Override
    public Token getToken() {
        return token;
    }

    @Override
    public void generate() {
        if(token.getLexeme().equals("true")) {
            MainSemantic.ST.getInstructionsList().add("PUSH 1");
        } else {
            MainSemantic.ST.getInstructionsList().add("PUSH 0");
        }
    }
}
