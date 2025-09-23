package Syntactic;

import java.util.*;

public class Firsts {

    Map<String, ArrayList<String>> firsts = new HashMap<>();

    public Firsts() {
        firsts.put("tipoPrimitivo", new ArrayList<>(Arrays.asList("pr_boolean", "pr_char", "pr_int")));
        firsts.put("modificadorOpcionalMiembros", new ArrayList<>(Arrays.asList("pr_abstract", "pr_static", "pr_final")));
        firsts.put("constructor", new ArrayList<>(List.of("pr_public")));
        firsts.put("bloque", new ArrayList<>(List.of("pnt_llaveIzquierda")));
        firsts.put("varLocal", new ArrayList<>(List.of("pr_var")));
        firsts.put("return", new ArrayList<>(List.of("pr_return")));
        firsts.put("if", new ArrayList<>(List.of("pr_if")));
        firsts.put("ifAux", new ArrayList<>(List.of("pr_else")));
        firsts.put("while", new ArrayList<>(List.of("pr_while")));
        firsts.put("for", new ArrayList<>(List.of("pr_for")));
        firsts.put("operadorAsignacion", new ArrayList<>(List.of("op_asignacion")));
        firsts.put("operadorBinario", new ArrayList<>(Arrays.asList("op_or", "op_and", "op_igual", "op_distinto", "op_menor", "op_mayor", "op_menorIgual", "op_mayorIgual", "op_suma", "op_resta", "op_multiplicacion", "op_division", "op_modulo")));
        firsts.put("operadorUnario", new ArrayList<>(Arrays.asList("op_suma", "op_incremento", "op_resta", "op_decremento", "op_negacion")));
        firsts.put("primitivo", new ArrayList<>(Arrays.asList("pr_true", "pr_false", "intLiteral", "charLiteral", "pr_null")));
        firsts.put("accesoVarMetodo", new ArrayList<>(List.of("idMetVar")));
        firsts.put("llamadaConstructor", new ArrayList<>(List.of("pr_new")));
        firsts.put("expresionParentizada", new ArrayList<>(List.of("pnt_parentesisIzquierdo")));
        firsts.put("llamadaMetodoEstatico", new ArrayList<>(List.of("idClase")));
        firsts.put("argsActuales", new ArrayList<>(List.of("pnt_parentesisIzquierdo")));
        firsts.put("herenciaOpcional", new ArrayList<>(Arrays.asList("pr_extends", "€")));
        firsts.put("argsFormales", new ArrayList<>(List.of("pnt_parentesisIzquierdo")));
        firsts.put("forEach", new ArrayList<>(List.of("pnt_dosPuntos")));
        firsts.put("forEstandar", new ArrayList<>(List.of("pnt_puntoYComa")));
        firsts.put("declaracionVar", new ArrayList<>(List.of("pr_var")));

        firsts.put("tipo", new ArrayList<>(concat(List.of("idClase"), firsts.get("tipoPrimitivo"))));
        firsts.put("modificadorOpcional", new ArrayList<>(concat(List.of("€"), firsts.get("modificadorOpcionalMiembros"))));
        firsts.put("argFormal", new ArrayList<>(firsts.get("tipo")));
        firsts.put("listaArgsFormales", new ArrayList<>(firsts.get("argFormal")));
        firsts.put("primario", new ArrayList<>(concat(Arrays.asList("pr_this", "stringLiteral"), firsts.get("accesoVarMetodo"), firsts.get("llamadaConstructor"), firsts.get("llamadaMetodoEstatico"), firsts.get("expresionParentizada"))));
        firsts.put("referencia", new ArrayList<>(firsts.get("primario")));
        firsts.put("operando", new ArrayList<>(concat(firsts.get("primitivo"), firsts.get("referencia"))));
        firsts.put("expresionBasica", new ArrayList<>(concat(firsts.get("operadorUnario"), firsts.get("operando"))));
        firsts.put("expresionCompuesta", new ArrayList<>(firsts.get("expresionBasica")));
        firsts.put("expresion", new ArrayList<>(firsts.get("expresionCompuesta")));

        firsts.put("listaArgsFormalesOpcional", new ArrayList<>(concat(List.of("€"), firsts.get("listaArgsFormales"))));
        firsts.put("atributosInicializados", new ArrayList<>(concat(List.of("op_asignacion"), List.of("€"))));
        firsts.put("listaArgsFormalesTerminal", new ArrayList<>(concat(List.of("pnt_coma"), List.of("€"))));
        firsts.put("bloqueOpcional", new ArrayList<>(concat(List.of("pnt_puntoYComa"), firsts.get("bloque"))));
        firsts.put("expresionAux", new ArrayList<>(concat(List.of("€"), firsts.get("operadorAsignacion"))));
        firsts.put("expresionCompuestaTerminal", new ArrayList<>(concat(List.of("€", "op_ternario"), firsts.get("operadorBinario"))));
        firsts.put("expresionOpcional", new ArrayList<>(concat(List.of("€"), firsts.get("expresion"))));
        firsts.put("listaExpsAux", new ArrayList<>(concat(List.of("€"), firsts.get("expresion"))));
        firsts.put("listaExpsOpcional", new ArrayList<>(concat(List.of("€"), firsts.get("expresion"))));
        firsts.put("encadenadoVarMetodo", new ArrayList<>(concat(List.of("€"), firsts.get("argsActuales"))));
        firsts.put("argsAux", new ArrayList<>(concat(List.of("€"), firsts.get("argsActuales"))));

        firsts.put("asignacionLlamada", new ArrayList<>(firsts.get("expresion")));
        firsts.put("miembro", new ArrayList<>(concat(List.of("pr_void"), firsts.get("constructor"), firsts.get("tipo"), firsts.get("modificadorOpcionalMiembros"))));
        firsts.put("miembroMetodo", new ArrayList<>(concat(List.of("pnt_puntoYComa"), firsts.get("argsFormales"))));
        firsts.put("sentencia", new ArrayList<>(concat(List.of("pnt_puntoYComa"), firsts.get("asignacionLlamada"), firsts.get("varLocal"), firsts.get("return"), firsts.get("if"), firsts.get("while"), firsts.get("bloque"), firsts.get("for"))));
        firsts.put("listaSentencias", new ArrayList<>(firsts.get("sentencia")));
        firsts.put("clase", new ArrayList<>(concat(List.of("pr_class"), firsts.get("modificadorOpcional"))));
        firsts.put("interfaz", new ArrayList<>(List.of("pr_interface")));
        firsts.put("listaClases", new ArrayList<>(concat(List.of("€"), firsts.get("clase"))));
        firsts.put("inicial", new ArrayList<>(firsts.get("listaClases")));
        firsts.put("listaMiembros", new ArrayList<>(concat(List.of("€"), firsts.get("miembro"))));
        firsts.put("tipoMetodo", new ArrayList<>(concat(List.of("pr_void"), firsts.get("tipo"))));
        firsts.put("referenciaTerminal", new ArrayList<>(concat(List.of("pnt_punto"), List.of("€"))));
        firsts.put("forAux", new ArrayList<>(concat(firsts.get("varLocal"), firsts.get("expresion"))));
        firsts.put("forTipo", new ArrayList<>(concat(firsts.get("forEstandar"), List.of("pnt_dosPuntos"))));
        firsts.put("miembroInterfaz", new ArrayList<>(concat(List.of("€"), firsts.get("tipoMetodo"))));
        firsts.put("metodoAtributo", new ArrayList<>(concat(List.of("pr_void"), firsts.get("tipo"))));
        firsts.put("metodoModificador", new ArrayList<>(firsts.get("modificadorOpcionalMiembros")));
        firsts.put("argBloque", new ArrayList<>(firsts.get("argsFormales")));
        firsts.put("MiembroAtributoAux", new ArrayList<>(concat(List.of("op_asignacion"), firsts.get("miembroMetodo"))));
    }

    public boolean isFirst(String production, String token){
        return firsts.get(production).contains(token);
    }

    private List<String> concat(List<String> a, List<String> b) {
        List<String> returnList = new ArrayList<>();
        if (a != null) returnList.addAll(a);
        if (b != null) returnList.addAll(b);
        return returnList;
    }

    private List<String> concat(List<String> a, List<String> b, List<String> c, List<String> d) {
        List<String> returnList = new ArrayList<>();
        if (a != null) returnList.addAll(a);
        if (b != null) returnList.addAll(b);
        if (c != null) returnList.addAll(c);
        if (d != null) returnList.addAll(d);
        return returnList;
    }

    private List<String> concat(List<String> a, List<String> b, List<String> c, List<String> d, List<String> e) {
        List<String> returnList = new ArrayList<>();
        if (a != null) returnList.addAll(a);
        if (b != null) returnList.addAll(b);
        if (c != null) returnList.addAll(c);
        if (d != null) returnList.addAll(d);
        if (e != null) returnList.addAll(e);
        return returnList;
    }

    private List<String> concat(List<String> a, List<String> b, List<String> c, List<String> d, List<String> e, List<String> f, List<String> g,  List<String> h) {
        List<String> returnList = new ArrayList<>();
        if (a != null) returnList.addAll(a);
        if (b != null) returnList.addAll(b);
        if (c != null) returnList.addAll(c);
        if (d != null) returnList.addAll(d);
        if (e != null) returnList.addAll(e);
        if (f != null) returnList.addAll(f);
        if (g != null) returnList.addAll(g);
        if (h != null) returnList.addAll(h);
        return returnList;
    }
}

