package Semantic.Ast.Expressions.Literals;

import Lexical.Token;
import Main.MainSemantic;
import Semantic.Ast.Chained.ChainedNode;
import Semantic.Ast.Expressions.Access.AccessNode;
import Semantic.Types.ReferenceType;
import Semantic.Types.Type;

public class StringLiteralNode extends AccessNode {
    private Token token;

    public StringLiteralNode(Token token) {
        this.token = token;
    }

    @Override
    public Type check() {
        return new ReferenceType(new Token("String","String", token.getLineNumber()));
    }

    @Override
    public void setChaining(ChainedNode chaining) { }

    @Override
    public ChainedNode getChaining() {
        return null;
    }

    @Override
    public void setItsLeftSide(boolean leftSide) { }

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
        int stringNumber = MainSemantic.ST.getNextStringNumber();
        String label = "lbl_string" + stringNumber;
        MainSemantic.ST.getInstructionsList().add(".DATA");
        MainSemantic.ST.getInstructionsList().add(label+": DW "+token.getLexeme()+", 0");
        MainSemantic.ST.getInstructionsList().add(".CODE");
        MainSemantic.ST.getInstructionsList().add("PUSH "+label);
    }
}
