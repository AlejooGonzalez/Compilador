package Semantic.Types;

import Lexical.Token;

public class CharType extends PrimitiveType {
    public CharType() {
        super(new Token("pr_char","char", 0));
    }
}
