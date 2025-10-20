package Semantic.Types;

import Lexical.Token;

public class CharType extends PrimitiveType {
    public CharType(int line) {
        super(new Token("pr_char","char", line));
    }

    public boolean conformsWith(Type other) {
        return other instanceof CharType;
    }
}
