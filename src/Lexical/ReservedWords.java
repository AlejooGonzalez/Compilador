package Lexical;

import java.util.*;

public class ReservedWords {
        private static final Map<String,String> map = new HashMap<>();
         static {
            map.put("class", "pr_class");
            map.put("interface", "pr_interface");
            map.put("extends", "pr_extends");
            map.put("public", "pr_public");
            map.put("static", "pr_static");
            map.put("void", "pr_void");
            map.put("boolean", "pr_boolean");
            map.put("char", "pr_char");
            map.put("int", "pr_int");
            map.put("abstract", "pr_abstract");
            map.put("final", "pr_final");
            map.put("if", "pr_if");
            map.put("else", "pr_else");
            map.put("while", "pr_while");
            map.put("return", "pr_return");
            map.put("var", "pr_var");
            map.put("this", "pr_this");
            map.put("new", "pr_new");
            map.put("null", "pr_null");
            map.put("true", "pr_true");
            map.put("false", "pr_false");
            map.put("for", "pr_for");
            map.put("implements", "pr_implements");
    }

    public static String reservedWord(String lexeme){
        return map.get(lexeme);
    }
}
