package Semantic.Types;

import Lexical.Token;

public class BooleanType extends PrimitiveType{
    public BooleanType() {
        super(new Token("pr_boolean","boolean", 0));
    }
}
