package Semantic.Types;

import Lexical.Token;

public class NullType extends PrimitiveType {
    public NullType(int line) {
        super(new Token("pr_null","null", line));
    }

    public boolean isSubtypeOf(Type rightType) {
        return rightType instanceof NullType;
    }
}
