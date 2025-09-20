package Syntactic;

import Lexical.Token;

import java.util.*;


public class Firsts {

    Map<String, ArrayList<String>> firsts = new HashMap<>();

    public Firsts() {
        firsts.put("TipoPrimitivo", new ArrayList<>(Arrays.asList("pr_boolean", "pr_char", "pr_int")));
        firsts.put("ModificadorOpcionalMiembros", new ArrayList<>(Arrays.asList("pr_abstract", "pr_static", "pr_final")));
        firsts.put("Constructor", new ArrayList<>(List.of("pr_public")));
        firsts.put("Bloque", new ArrayList<>(List.of("pnt_llaveIzquierda")));
        firsts.put("VarLocal", new ArrayList<>(List.of("pr_var")));
        firsts.put("Return", new ArrayList<>(List.of("pr_return")));
        firsts.put("If", new ArrayList<>(List.of("pr_if")));
        firsts.put("IfAux", new ArrayList<>(List.of("pr_else")));
        firsts.put("While", new ArrayList<>(List.of("pr_while")));
        firsts.put("For", new ArrayList<>(List.of("pr_for")));
        firsts.put("OperadorAsignacion", new ArrayList<>(List.of("op_asignacion")));
        firsts.put("OperadorBinario", new ArrayList<>(Arrays.asList("op_or", "op_and", "op_igual", "op_distinto", "op_menor", "op_mayor", "op_menorIgual", "op_mayorIgual", "op_suma", "op_resta", "op_multiplicacion", "op_division", "op_modulo")));
        firsts.put("OperadorUnario", new ArrayList<>(Arrays.asList("op_suma", "op_incremento", "op_resta", "op_decremento", "op_negacion")));
        firsts.put("Primitivo", new ArrayList<>(Arrays.asList("pr_true", "pr_false", "intLiteral", "charLiteral", "pr_null")));
        firsts.put("AccesoVarMetodo", new ArrayList<>(List.of("idMetVar")));
        firsts.put("LlamadaConstructor", new ArrayList<>(List.of("pr_new")));
        firsts.put("ExpresionParentizada", new ArrayList<>(List.of("pnt_parentesisIzquierdo")));
        firsts.put("LlamadaMetodoEstatico", new ArrayList<>(List.of("idClase")));
        firsts.put("ArgsActuales", new ArrayList<>(List.of("pnt_parentesisIzquierdo")));
        firsts.put("HerenciaOpcional", new ArrayList<>(Arrays.asList("pr_extends", "€")));
        firsts.put("ArgsFormales", new ArrayList<>(List.of("pnt_parentesisIzquierdo")));
        firsts.put("ForIterador", new ArrayList<>(List.of("pnt_dosPuntos")));
        firsts.put("ForEstandar", new ArrayList<>(List.of("pnt_puntoYComa")));

        firsts.put("Tipo", new ArrayList<>(concat(List.of("idClase"), firsts.get("TipoPrimitivo"))));
        firsts.put("ModificadorOpcional", new ArrayList<>(concat(List.of("€"), firsts.get("ModificadorOpcionalMiembros"))));
        firsts.put("ArgFormal", new ArrayList<>(firsts.get("Tipo")));
        firsts.put("ListaArgsFormales", new ArrayList<>(firsts.get("ArgFormal")));
        firsts.put("Primario", new ArrayList<>(concat(Arrays.asList("pr_this", "stringLiteral"), firsts.get("AccesoVarMetodo"), firsts.get("LlamadaConstructor"), firsts.get("LlamadaMetodoEstatico"), firsts.get("ExpresionParentizada"))));
        firsts.put("Referencia", new ArrayList<>(firsts.get("Primario")));
        firsts.put("Operando", new ArrayList<>(concat(firsts.get("Primitivo"), firsts.get("Referencia"))));
        firsts.put("ExpresionBasica", new ArrayList<>(concat(firsts.get("OperadorUnario"), firsts.get("Operando"))));
        firsts.put("ExpresionCompuesta", new ArrayList<>(firsts.get("ExpresionBasica")));
        firsts.put("Expresion", new ArrayList<>(firsts.get("ExpresionCompuesta")));

        firsts.put("ListaArgsFormalesOpcional", new ArrayList<>(concat(List.of("€"), firsts.get("ListaArgsFormales"))));
        firsts.put("ListaArgsFormalesTerminal", new ArrayList<>(concat(List.of("pnt_coma"), List.of("€"))));
        firsts.put("BloqueOpcional", new ArrayList<>(concat(List.of("pnt_puntoYComa"), firsts.get("Bloque"))));
        firsts.put("ExpresionAux", new ArrayList<>(concat(List.of("€"), firsts.get("OperadorAsignacion"))));
        firsts.put("ExpresionCompuestaTerminal", new ArrayList<>(concat(List.of("€", "op_ternario"), firsts.get("OperadorBinario"))));
        firsts.put("ExpresionOpcional", new ArrayList<>(concat(List.of("€"), firsts.get("Expresion"))));
        firsts.put("ListaExps", new ArrayList<>(concat(List.of("€"), firsts.get("Expresion"))));
        firsts.put("ListaExpsOpcional", new ArrayList<>(concat(List.of("€"), firsts.get("Expresion"))));
        firsts.put("EncadenadoVarMetodo", new ArrayList<>(concat(List.of("€"), firsts.get("ArgsActuales"))));
        firsts.put("ArgsAux", new ArrayList<>(concat(List.of("€"), firsts.get("ArgsActuales"))));

        firsts.put("AsignacionLlamada", new ArrayList<>(firsts.get("Expresion")));
        firsts.put("Miembro", new ArrayList<>(concat(List.of("pr_void"), firsts.get("Constructor"), firsts.get("Tipo"), firsts.get("ModificadorOpcionalMiembros"))));
        firsts.put("MiembroMetodo", new ArrayList<>(concat(List.of("pnt_puntoYComa"), firsts.get("ArgsFormales"))));
        firsts.put("Sentencia", new ArrayList<>(concat(List.of("pnt_puntoYComa"), firsts.get("AsignacionLlamada"), firsts.get("VarLocal"), firsts.get("Return"), firsts.get("If"), firsts.get("While"), firsts.get("Bloque"), firsts.get("For"))));
        firsts.put("ListaSentencias", new ArrayList<>(firsts.get("Sentencia")));
        firsts.put("Clase", new ArrayList<>(concat(List.of("pr_class"), firsts.get("ModificadorOpcional"))));
        firsts.put("ListaClases", new ArrayList<>(concat(List.of("€"), firsts.get("Clase"))));
        firsts.put("Inicial", new ArrayList<>(firsts.get("ListaClases")));
        firsts.put("ListaMiembros", new ArrayList<>(concat(List.of("€"), firsts.get("Miembro"))));
        firsts.put("TipoMetodo", new ArrayList<>(concat(List.of("pr_void"), firsts.get("Tipo"))));
        firsts.put("ReferenciaTerminal", new ArrayList<>(concat(List.of("pnt_punto"), List.of("€"))));
        firsts.put("ForAux", new ArrayList<>(concat(firsts.get("VarLocal"), firsts.get("Expresion"))));
        firsts.put("ForTipo", new ArrayList<>(concat(firsts.get("ForEstandar"), List.of("pnt_dosPuntos"))));
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

