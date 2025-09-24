package Syntactic;

import Exceptions.LexicalException;
import Exceptions.SyntacticException;
import Lexical.LexicalAnalyzer;
import Lexical.Token;

import java.io.IOException;
import java.util.Objects;

public class SyntacticAnalyzer {
    LexicalAnalyzer lexicalAnalyzer;
    Token actualToken;
    Firsts firsts;

    public SyntacticAnalyzer(LexicalAnalyzer lexicalAnalyzer) throws LexicalException, IOException {
        this.lexicalAnalyzer = lexicalAnalyzer;
        firsts = new Firsts();
        nextToken();
    }

    public void start() throws SyntacticException, LexicalException, IOException {
        inicial();
    }

    public void match(String expectedToken) throws LexicalException, SyntacticException, IOException {
        if(expectedToken.equals(actualToken.getTokenType()))
            nextToken();
        else
            throw new SyntacticException(expectedToken, actualToken);
    }

    private void nextToken() throws LexicalException {
        actualToken = lexicalAnalyzer.nextToken();
    }

    private void inicial() throws LexicalException, SyntacticException, IOException {
        listaClasesInterfaces();
        match("EOF");
    }

    private void listaClasesInterfaces() throws LexicalException, SyntacticException, IOException {
        if (firsts.isFirst("clase", actualToken.getTokenType())) {
            clase();
            listaClasesInterfaces();
        } else {
            if (firsts.isFirst("interfaz", actualToken.getTokenType())) {
                interfaz();
                listaClasesInterfaces();
            } else { }
        }
    }

    private void clase() throws LexicalException, SyntacticException, IOException {
            modificadorOpcional();
            match("pr_class");
            match("idClase");
            herenciaOpcional();
            match("pnt_llaveIzquierda");
            listaMiembros();
            match("pnt_llaveDerecha");
    }

    private void interfaz() throws LexicalException, SyntacticException, IOException {
        match("pr_interface");
        match("idClase");
        herenciaOpcionalInterfaz();
        match("pnt_llaveIzquierda");
        listaMiembrosInterfaz();
        match("pnt_llaveDerecha");
    }

    private void listaMiembrosInterfaz() throws LexicalException, SyntacticException, IOException {
        if (firsts.isFirst("miembroInterfaz", actualToken.getTokenType())) {
            miembroInterfaz();
            listaMiembrosInterfaz();
        } else { }
    }

    private void miembroInterfaz() throws LexicalException, SyntacticException, IOException {
        if (firsts.isFirst("tipoMetodo", actualToken.getTokenType())) {
            tipoMetodo();
            match("idMetVar");
            argsFormales();
            match("pnt_puntoYComa");
        }
    }

    private void modificadorOpcional() throws LexicalException, SyntacticException, IOException {
        if (firsts.isFirst("modificadorOpcionalMiembros", actualToken.getTokenType())) {
            modificadorOpcionalMiembros();
        } else { }
    }

    private void herenciaOpcionalInterfaz() throws LexicalException, SyntacticException, IOException {
        if (actualToken.getTokenType().equals("pr_extends")) {
            match("pr_extends");
            match("idClase");
        } else { }
    }

    private void herenciaOpcional() throws LexicalException, SyntacticException, IOException {
        if (actualToken.getTokenType().equals("pr_extends")) {
            match("pr_extends");
            match("idClase");
        } else {
            if(actualToken.getTokenType().equals("pr_implements")){
                match("pr_implements");
                match("idClase");
            } else { }
        }
    }

    private void listaMiembros() throws LexicalException, SyntacticException, IOException {
        if (firsts.isFirst("miembro", actualToken.getTokenType())) {
            miembro();
            listaMiembros();
        } else { }
    }

    private void modificadorOpcionalMiembros() throws LexicalException, SyntacticException, IOException {
        if (actualToken.getTokenType().equals("pr_abstract")) {
            match("pr_abstract");
        } else if (actualToken.getTokenType().equals("pr_static")) {
            match("pr_static");
        } else if (actualToken.getTokenType().equals("pr_final")) {
            match("pr_final");
        } else {
            throw new SyntacticException("modificadorOpcionalMiembros", actualToken);
        }
    }

    private void miembro() throws LexicalException, SyntacticException, IOException {
        if (firsts.isFirst("constructor", actualToken.getTokenType())) {
            constructor();
        } else if (firsts.isFirst("metodoAtributo", actualToken.getTokenType())) {
            metodoAtributo();
        } else if (firsts.isFirst("metodoModificador", actualToken.getTokenType())) {
            metodoModificador();
        } else {
            throw new SyntacticException("miembro", actualToken);
        }
    }

    private void miembroMetodo() throws LexicalException, SyntacticException, IOException {
        if (firsts.isFirst("argBloque", actualToken.getTokenType())) {
            argBloque();
        } else {
            if (Objects.equals(actualToken.getTokenType(), "pnt_puntoYComa")) {
                match("pnt_puntoYComa");
            } else {
                throw new SyntacticException("miembroMetodo", actualToken);
            }
        }
    }

    private void constructor() throws LexicalException, SyntacticException, IOException {
        match("pr_public");
        match("idClase");
        argsFormales();
        bloque();
    }

    private void tipoMetodo() throws LexicalException, SyntacticException, IOException {
        if (firsts.isFirst("tipo", actualToken.getTokenType())) {
            tipo();
        } else {
            if(Objects.equals(actualToken.getTokenType(), "pr_void")) {
                match("pr_void");
            } else {
                throw new SyntacticException("tipoMetodo", actualToken);
            }
        }
    }

    private void tipo() throws LexicalException, SyntacticException, IOException {
        if (firsts.isFirst("tipoPrimitivo", actualToken.getTokenType())) {
            tipoPrimitivo();
        } else if (Objects.equals(actualToken.getTokenType(), "idClase")) {
            match("idClase");
        } else {
            throw new SyntacticException("tipo", actualToken);
        }
    }

    private void tipoPrimitivo() throws LexicalException, SyntacticException, IOException {
        switch (actualToken.getTokenType()) {
            case "pr_boolean" -> match("pr_boolean");
            case "pr_char" -> match("pr_char");
            case "pr_int" -> match("pr_int");
        }
    }

    private void miembroAtributoAux() throws LexicalException, SyntacticException, IOException {
        if (Objects.equals(actualToken.getTokenType(), "op_asignacion")) {
            match("op_asignacion");
            expresionCompuesta();
            match("pnt_puntoYComa");
        } else if (firsts.isFirst("miembroMetodo", actualToken.getTokenType())) {
            miembroMetodo();
        } else {
            throw new SyntacticException("miembroAtributoAux", actualToken);
        }
    }

    private void metodoAtributo() throws LexicalException, SyntacticException, IOException {
        if (firsts.isFirst("tipo", actualToken.getTokenType())) {
            tipo();
            match("idMetVar");
            miembroAtributoAux();
        } else if (actualToken.getTokenType().equals("pr_void")) {
            match("pr_void");
            match("idMetVar");
            argBloque();
        } else {
            throw new SyntacticException("metodoAtributo", actualToken);
        }
    }

    private void metodoModificador() throws LexicalException, SyntacticException, IOException {
        modificadorOpcionalMiembros();
        tipoMetodo();
        match("idMetVar");
        argBloque();
    }

    private void argBloque() throws LexicalException, SyntacticException, IOException {
        argsFormales();
        bloqueOpcional();
    }

    private void argsFormales() throws LexicalException, SyntacticException, IOException {
        if (Objects.equals(actualToken.getTokenType(), "pnt_parentesisIzquierdo")) {
            match("pnt_parentesisIzquierdo");
            listaArgsFormalesOpcional();
            match("pnt_parentesisDerecho");
        } else {
            throw new SyntacticException("argsFormales", actualToken);
        }
    }

    private void listaArgsFormalesOpcional() throws LexicalException, SyntacticException, IOException {
        if (firsts.isFirst("listaArgsFormales", actualToken.getTokenType())) {
            listaArgsFormales();
        } else { }
    }

    private void listaArgsFormales() throws LexicalException, SyntacticException, IOException {
        if (firsts.isFirst("argFormal", actualToken.getTokenType())) {
            argFormal();
            listaArgsFormalesTerminal();
        } else {
            throw new SyntacticException("listaArgsFormales", actualToken);
        }
    }

    private void listaArgsFormalesTerminal() throws LexicalException, SyntacticException, IOException {
        if (Objects.equals(actualToken.getTokenType(), "pnt_coma")) {
            match("pnt_coma");
            argFormal();
            listaArgsFormalesTerminal();
        } else { }
    }

    private void argFormal() throws LexicalException, SyntacticException, IOException {
        if (firsts.isFirst("tipo", actualToken.getTokenType())) {
            tipo();
            match("idMetVar");
        } else {
            throw new SyntacticException("argFormal", actualToken);
        }
    }

    private void bloqueOpcional() throws LexicalException, SyntacticException, IOException {
        if (firsts.isFirst("bloque", actualToken.getTokenType())){
            bloque();
        } else {
            if(Objects.equals(actualToken.getTokenType(), "pnt_puntoYComa")){
                match("pnt_puntoYComa");
            } else {
                throw new SyntacticException("bloqueOpcional", actualToken);
            }
        }
    }

    private void bloque() throws LexicalException, SyntacticException, IOException {
        if(Objects.equals(actualToken.getTokenType(), "pnt_llaveIzquierda")) {
            match("pnt_llaveIzquierda");
            listaSentencias();
            match("pnt_llaveDerecha");
        }
    }

    private void listaSentencias() throws LexicalException, SyntacticException, IOException {
        if (firsts.isFirst("sentencia", actualToken.getTokenType())) {
            sentencia();
            listaSentencias();
        } else { }
    }

    private void sentencia() throws LexicalException, SyntacticException, IOException {
        if (Objects.equals(actualToken.getTokenType(), "pnt_puntoYComa")) {
            match("pnt_puntoYComa");
        }else if (firsts.isFirst("asignacionLlamada", actualToken.getTokenType())) {
            asignacionLlamada();
            match("pnt_puntoYComa");
        } else if (firsts.isFirst("varLocal", actualToken.getTokenType())) {
            varLocal();
            match("pnt_puntoYComa");
        } else if (firsts.isFirst("return", actualToken.getTokenType())) {
            retorno();
            match("pnt_puntoYComa");
        } else if (firsts.isFirst("if", actualToken.getTokenType())) {
            ifPrincipal();
        } else if (firsts.isFirst("for", actualToken.getTokenType())) {
            forPrincipal();
        } else if (firsts.isFirst("while", actualToken.getTokenType())) {
            whilePrincipal();
        } else if (firsts.isFirst("bloque", actualToken.getTokenType())) {
            bloque();
        }  else {
            throw new SyntacticException("sentencia", actualToken);
        }
    }

    private void asignacionLlamada() throws LexicalException, SyntacticException, IOException {
        if (firsts.isFirst("expresion", actualToken.getTokenType())) {
            expresion();
        } else {
            throw  new SyntacticException("asignacionLlamada", actualToken);
        }
    }

    private void varLocal() throws LexicalException, SyntacticException, IOException {
        match("pr_var");
        match("idMetVar");
        match("op_asignacion");
        expresionCompuesta();
    }

    private void retorno() throws LexicalException, SyntacticException, IOException {
        match("pr_return");
        expresionOpcional();
    }

    private void expresionOpcional() throws LexicalException, SyntacticException, IOException {
        if (firsts.isFirst("expresion", actualToken.getTokenType())) {
            expresion();
        } else { }
    }

    private void ifPrincipal() throws LexicalException, SyntacticException, IOException {
        match("pr_if");
        match("pnt_parentesisIzquierdo");
        expresion();
        match("pnt_parentesisDerecho");
        sentencia();
        ifAux();
    }

    private void ifAux() throws LexicalException, SyntacticException, IOException {
        if (actualToken.getTokenType().equals("pr_else")) {
            match("pr_else");
            sentencia();
        } else { }
    }

    private void whilePrincipal() throws LexicalException, SyntacticException, IOException {
        match("pr_while");
        match("pnt_parentesisIzquierdo");
        expresion();
        match("pnt_parentesisDerecho");
        sentencia();
    }

    private void expresion() throws LexicalException, SyntacticException, IOException {
        if (firsts.isFirst("expresionCompuesta", actualToken.getTokenType())) {
            expresionCompuesta();
            expresionAux();
        } else {
            throw new SyntacticException("expresion", actualToken);
        }
    }

    private void expresionAux() throws LexicalException, SyntacticException, IOException {
        if (firsts.isFirst("operadorAsignacion", actualToken.getTokenType())) {
            operadorAsignacion();
            expresionCompuesta();
        } else { }
    }

    private void forPrincipal() throws LexicalException, SyntacticException, IOException {
            match("pr_for");
            match("pnt_parentesisIzquierdo");
            forAux();
            match("pnt_parentesisDerecho");
            sentencia();
    }

    private void forAux() throws LexicalException, SyntacticException, IOException {
        if (firsts.isFirst("declaracionVar", actualToken.getTokenType())) {
            declaracionVar();
            forTipo();
        } else if (firsts.isFirst("expresion", actualToken.getTokenType())) {
            expresion();
            forElemental();
        } else {
            throw new SyntacticException("forAux", actualToken);
        }
    }

    private void declaracionVar() throws LexicalException, SyntacticException, IOException {
        match("pr_var");
        match("idMetVar");
    }

    private void forElemental() throws LexicalException, SyntacticException, IOException {
        match("pnt_puntoYComa");
        expresion();
        match("pnt_puntoYComa");
        expresion();
    }

    private void forTipo() throws LexicalException, SyntacticException, IOException {
        if (Objects.equals(actualToken.getTokenType(), "op_asignacion")) {
            match("op_asignacion");
            expresionCompuesta();
            forElemental();
        } else if (Objects.equals(actualToken.getTokenType(), "pnt_dosPuntos")) {
            forEach();
        } else {
            throw new SyntacticException("forTipo", actualToken);
        }
    }

    private void forEach() throws LexicalException, SyntacticException, IOException {
        match("pnt_dosPuntos");
        expresion();
    }

    private void expresionCompuesta() throws LexicalException, SyntacticException, IOException {
        if (firsts.isFirst("expresionBasica", actualToken.getTokenType())){
            expresionBasica();
            expresionCompuestaTerminal();
        } else {
            throw new SyntacticException("expresionCompuesta", actualToken);
        }
    }

    private void expresionCompuestaTerminal() throws LexicalException, SyntacticException, IOException {
        if (firsts.isFirst("operadorBinario", actualToken.getTokenType())) {
            OperadorBinario();
            expresionBasica();
            expresionCompuestaTerminal();
        } else {
            if (actualToken.getTokenType().equals("op_ternario")) {
                match("op_ternario");
                expresion();
                match("pnt_dosPuntos");
                expresion();
            } else { }
        }
    }

    private void OperadorBinario() throws SyntacticException, LexicalException, IOException {
        switch (actualToken.getTokenType()) {
            case "op_or" -> match("op_or");
            case "op_and" -> match("op_and");
            case "op_igual" -> match("op_igual");
            case "op_distinto" -> match("op_distinto");
            case "op_menor" -> match("op_menor");
            case "op_menorIgual" -> match("op_menorIgual");
            case "op_mayor" -> match("op_mayor");
            case "op_mayorIgual" -> match("op_mayorIgual");
            case "op_suma" -> match("op_suma");
            case "op_resta" -> match("op_resta");
            case "op_multiplicacion" -> match("op_multiplicacion");
            case "op_division" -> match("op_division");
            case "op_modulo" -> match("op_modulo");
            default ->
                    throw new SyntacticException("operadorBinario", actualToken);
        }
    }

    private void operadorAsignacion() throws LexicalException, SyntacticException, IOException {
        match("op_asignacion");
    }

    private void expresionBasica() throws LexicalException, SyntacticException, IOException {
        if (firsts.isFirst("operadorUnario", actualToken.getTokenType())) {
            operadorUnario();
            operando();
        } else {
            if (firsts.isFirst("operando", actualToken.getTokenType())) {
                operando();
            } else {
                throw new SyntacticException("expresionBasica", actualToken);
            }
        }
    }

    private void operadorUnario() throws SyntacticException, LexicalException, IOException {
        switch (actualToken.getTokenType()) {
            case "op_suma" -> match("op_suma");
            case "op_incremento" -> match("op_incremento");
            case "op_resta" -> match("op_resta");
            case "op_decremento" -> match("op_decremento");
            case "op_negacion" -> match("op_negacion");
            default ->
                    throw new SyntacticException("operadorUnario", actualToken);
        }
    }

    private void operando() throws LexicalException, SyntacticException, IOException {
        if (firsts.isFirst("primitivo", actualToken.getTokenType())) {
            primitivo();
        } else {
            if (firsts.isFirst("referencia", actualToken.getTokenType())) {
                referencia();
            } else {
                throw new SyntacticException("operando", actualToken);
            }
        }
    }

    private void primitivo() throws SyntacticException, LexicalException, IOException {
        switch (actualToken.getTokenType()) {
            case "pr_true" -> match("pr_true");
            case "pr_false" -> match("pr_false");
            case "intLiteral" -> match("intLiteral");
            case "charLiteral" -> match("charLiteral");
            case "pr_null" -> match("pr_null");
            default ->
                    throw new SyntacticException("primitivo", actualToken);
        }
    }

    private void referencia() throws LexicalException, SyntacticException, IOException {
        if (firsts.isFirst("primario", actualToken.getTokenType())) {
            primario();
            referenciaTerminal();
        } else {
            throw new SyntacticException("referencia", actualToken);
        }
    }

    private void referenciaTerminal() throws LexicalException, SyntacticException, IOException {
        if (actualToken.getTokenType().equals("pnt_punto")) {
            match("pnt_punto");
            match("idMetVar");
            encadenadoVarMetodo();
            referenciaTerminal();
        } else { }
    }

    private void primario() throws LexicalException, SyntacticException, IOException {
        switch (actualToken.getTokenType()) {
            case "pr_this" -> match("pr_this");
            case "stringLiteral" -> match("stringLiteral");
            case "idMetVar" -> accesoVarMetodo();
            case "pr_new" -> llamadaConstructor();
            case "idClase" -> llamadaMetodoEstatico();
            case "pnt_parentesisIzquierdo" -> expresionParentizada();
            default -> throw new SyntacticException("primario", actualToken);
        }
    }

    private void accesoVarMetodo() throws LexicalException, SyntacticException, IOException {
            if (actualToken.getTokenType().equals("idMetVar")) {
            match("idMetVar");
            argsAux();
        } else {
            throw new SyntacticException("accesoVarMetodo", actualToken);
        }
    }

    private void argsAux() throws LexicalException, SyntacticException, IOException {
        if (firsts.isFirst("argsActuales", actualToken.getTokenType())) {
           argsActuales();
        } else { }
    }

    private void llamadaConstructor() throws LexicalException, SyntacticException, IOException {
        match("pr_new");
        match("idClase");
        argsActuales();
    }

    private void expresionParentizada() throws LexicalException, SyntacticException, IOException {
        match("pnt_parentesisIzquierdo");
        expresion();
        match("pnt_parentesisDerecho");
    }


    private void llamadaMetodoEstatico() throws LexicalException, SyntacticException, IOException {
            match("idClase");
            match("pnt_punto");
            match("idMetVar");
            argsActuales();
    }

    private void argsActuales() throws LexicalException, SyntacticException, IOException {
        match("pnt_parentesisIzquierdo");
        listaExpsOpcional();
        match("pnt_parentesisDerecho");
    }

    private void listaExpsOpcional() throws LexicalException, SyntacticException, IOException {
        if (firsts.isFirst("expresion", actualToken.getTokenType())) {
            expresion();
            listaExpsAux();
        } else { }
    }

    private void listaExpsAux() throws LexicalException, SyntacticException, IOException {
        if (Objects.equals(actualToken.getTokenType(), "pnt_coma")) {
            match("pnt_coma");
            expresion();
            listaExpsAux();
        } else { }
    }

    private void encadenadoVarMetodo() throws LexicalException, SyntacticException, IOException {
        if (firsts.isFirst("argsActuales", actualToken.getTokenType())) {
            argsActuales();
        } else { }
    }
}

