package Semantic.Types;

import Lexical.Token;

public class IntType extends PrimitiveType{
    public IntType() {
        super(new Token("pr_int","int", 0));
    }
}
