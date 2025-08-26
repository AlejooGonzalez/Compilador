package Lexical;

import java.util.*;

public class ReservedWords {
        private static final Map<String,String> mapeo = new HashMap<String, String>();
         static {
            mapeo.put("class", "pr_class");
            mapeo.put("interface", "pr_interface");
            mapeo.put("extends", "pr_extends");
            mapeo.put("implements", "pr_implements");
            mapeo.put("public", "pr_public");
            mapeo.put("private", "pr_private");
            mapeo.put("static", "pr_static");
            mapeo.put("void", "pr_void");
            mapeo.put("boolean", "pr_boolean");
            mapeo.put("char", "pr_char");
            mapeo.put("int", "pr_int");
            mapeo.put("if", "pr_if");
            mapeo.put("else", "pr_else");
            mapeo.put("while", "pr_while");
            mapeo.put("return", "pr_return");
            mapeo.put("var", "pr_var");
            mapeo.put("this", "pr_this");
            mapeo.put("new", "pr_new");
            mapeo.put("null", "pr_null");
            mapeo.put("true", "pr_true");
            mapeo.put("false", "pr_false");
    }

    public static String reservedWord(String lexema){
        return mapeo.get(lexema);
    }
}
