package Semantic.Types;

import Lexical.Token;

public class IntType extends PrimitiveType{

    public IntType(int line) {
        super(new Token("pr_int","int", line));
    }

    public boolean conformsWith(Type other) {
        return other instanceof IntType;
    }
}
