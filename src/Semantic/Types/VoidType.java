package Semantic.Types;

import Lexical.Token;

public class VoidType extends PrimitiveType{

    public VoidType(int line) {
        super(new Token("pr_void","void", line));
    }

    public boolean conformsWith(Type other) {
        return other instanceof VoidType;
    }
}
