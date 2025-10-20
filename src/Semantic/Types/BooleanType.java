package Semantic.Types;

import Lexical.Token;

public class BooleanType extends PrimitiveType{
    public BooleanType(int line) {
        super(new Token("pr_boolean","boolean", line));
    }

    public boolean conformsWith(Type other) {
        return other instanceof BooleanType;
    }
}
