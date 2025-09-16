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
        listaClases();
        match("EOF");
    }

    private void listaClases() throws LexicalException, SyntacticException, IOException {
        if(firsts.isFirst("Clase", actualToken.getTokenType())) {
            clase();
            listaClases();
        } else { }
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

    private void modificadorOpcional() throws LexicalException, SyntacticException, IOException {
        if (firsts.isFirst("ModificadorOpcionalMiembros", actualToken.getTokenType())) {
            modificadorOpcionalMiembros();
        } else { }
    }

    private void herenciaOpcional() throws LexicalException, SyntacticException, IOException {
        if (actualToken.getTokenType().equals("pr_extends")) {
            match("pr_extends");
            match("idClase");
        } else { }
    }

    private void listaMiembros() throws LexicalException, SyntacticException, IOException {
        if (firsts.isFirst("Miembro", actualToken.getTokenType())) {
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
            throw new SyntacticException("abstract | static | final", actualToken);
        }
    }

    private void miembro() throws LexicalException, SyntacticException, IOException {
        if (firsts.isFirst("Constructor", actualToken.getTokenType())) {
            constructor();
        } else if (firsts.isFirst("Tipo", actualToken.getTokenType())) {
            tipo();
            match("idMetVar");
            miembroMetodo();
        } else if (firsts.isFirst("ModificadorOpcionalMiembros", actualToken.getTokenType())) {
            modificadorOpcionalMiembros();
            tipoMetodo();
            match("idMetVar");
            argsFormales();
            bloqueOpcional();
        } else if (actualToken.getTokenType().equals("pr_void")) {
            match("pr_void");
            match("idMetVar");
            argsFormales();
            bloqueOpcional();
        } else {
            throw new SyntacticException("Miembro", actualToken);
        }
    }

    private void miembroMetodo() throws LexicalException, SyntacticException, IOException {
        if (firsts.isFirst("ArgsFormales", actualToken.getTokenType())) {
            argsFormales();
            bloqueOpcional();
        } else {
            if (Objects.equals(actualToken.getTokenType(), "pnt_puntoYComa")) {
                match("pnt_puntoYComa");
            } else {
                throw new SyntacticException("MiembroMetodo", actualToken);
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
        if (firsts.isFirst("Tipo", actualToken.getTokenType())) {
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
        if (firsts.isFirst("TipoPrimitivo", actualToken.getTokenType())) {
            tipoPrimitivo();
        } else if (Objects.equals(actualToken.getTokenType(), "idClase")) {
            match("idClase");
        } else {
            throw new SyntacticException("Tipo", actualToken);
        }
    }

    private void tipoPrimitivo() throws LexicalException, SyntacticException, IOException {
        switch (actualToken.getTokenType()) {
            case "pr_boolean" -> match("pr_boolean");
            case "pr_char" -> match("pr_char");
            case "pr_int" -> match("pr_int");
            default -> { }
        }
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
        if (firsts.isFirst("ListaArgsFormales", actualToken.getTokenType())) {
            listaArgsFormales();
        } else { }
    }

    private void listaArgsFormales() throws LexicalException, SyntacticException, IOException {
        if (firsts.isFirst("ArgFormal", actualToken.getTokenType())) {
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
        if (firsts.isFirst("Tipo", actualToken.getTokenType())) {
            tipo();
            match("idMetVar");
        } else {
            throw new SyntacticException("argFormal", actualToken);
        }
    }

    private void bloqueOpcional() throws LexicalException, SyntacticException, IOException {
        if (firsts.isFirst("Bloque", actualToken.getTokenType())){
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
        if (firsts.isFirst("Sentencia", actualToken.getTokenType())) {
            sentencia();
            listaSentencias();
        } else { }
    }

    private void sentencia() throws LexicalException, SyntacticException, IOException {
        if (Objects.equals(actualToken.getTokenType(), "pnt_puntoYComa")) {
            match("pnt_puntoYComa");
        } else if (firsts.isFirst("VarLocal", actualToken.getTokenType())) {
            varLocal();
            match("pnt_puntoYComa");
        } else if (firsts.isFirst("Return", actualToken.getTokenType())) {
            retorno();
            match("pnt_puntoYComa");
        } else if (firsts.isFirst("If", actualToken.getTokenType())) {
            ifPrincipal();
        } else if (firsts.isFirst("While", actualToken.getTokenType())) {
            whilePrincipal();
        } else if (firsts.isFirst("Bloque", actualToken.getTokenType())) {
            bloque();
        } else if (firsts.isFirst("AsignacionLlamada", actualToken.getTokenType())) {
            asignacionLlamada();
            match("pnt_puntoYComa");
        } else {
            throw new SyntacticException("Sentencia", actualToken);
        }
    }

    private void asignacionLlamada() throws LexicalException, SyntacticException, IOException {
        if (firsts.isFirst("Expresion", actualToken.getTokenType())) {
            expresion();
        } else {
            throw  new SyntacticException("AsignacionLlamada", actualToken);
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
        if (firsts.isFirst("Expresion", actualToken.getTokenType())) {
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
        if (firsts.isFirst("ExpresionCompuesta", actualToken.getTokenType())) {
            expresionCompuesta();
            expresionAux();
        } else {
            throw new SyntacticException("Expresion", actualToken);
        }
    }

    private void expresionAux() throws LexicalException, SyntacticException, IOException {
        if (firsts.isFirst("OperadorAsignacion", actualToken.getTokenType())) {
            operadorAsignacion();
            expresionCompuesta();
        } else { }
    }

    private void operadorAsignacion() throws LexicalException, SyntacticException, IOException {
        match("op_asignacion");
    }

    private void expresionCompuesta() throws LexicalException, SyntacticException, IOException {
        if (firsts.isFirst("ExpresionBasica", actualToken.getTokenType())){
            expresionBasica();
            expresionCompuestaTerminal();
        } else {
            throw new SyntacticException("ExpresionCompuesta", actualToken);
        }
    }

    private void expresionCompuestaTerminal() throws LexicalException, SyntacticException, IOException {
        if (firsts.isFirst("OperadorBinario", actualToken.getTokenType())) {
            OperadorBinario();
            expresionBasica();
            expresionCompuestaTerminal();
        } else { }
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
                    throw new SyntacticException("OperadorBinario", actualToken);
        }
    }


    private void expresionBasica() throws LexicalException, SyntacticException, IOException {
        if (firsts.isFirst("OperadorUnario", actualToken.getTokenType())) {
            operadorUnario();
            operando();
        } else {
            if (firsts.isFirst("Operando", actualToken.getTokenType())) {
                operando();
            } else {
                throw new SyntacticException("ExpresionBasica", actualToken);
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
                    throw new SyntacticException("OperadorUnario", actualToken);
        }
    }

    private void operando() throws LexicalException, SyntacticException, IOException {
        if (firsts.isFirst("Primitivo", actualToken.getTokenType())) {
            primitivo();
        } else {
            if (firsts.isFirst("Referencia", actualToken.getTokenType())) {
                referencia();
            } else {
                throw new SyntacticException("Operando", actualToken);
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
                    throw new SyntacticException("Primitivo", actualToken);
        }
    }

    private void referencia() throws LexicalException, SyntacticException, IOException {
        primario();
        referenciaTerminal();
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
            default -> throw new SyntacticException("Primario", actualToken);
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
        if (firsts.isFirst("ArgsActuales", actualToken.getTokenType())) {
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
        if (Objects.equals(actualToken.getTokenType(), "idClase")) {
            match("idClase");
            match("pnt_punto");
            match("idMetVar");
            argsActuales();
        } else {
            throw new SyntacticException("LlamadaMetodoEstatico", actualToken);
        }
    }

    private void argsActuales() throws LexicalException, SyntacticException, IOException {
        match("pnt_parentesisIzquierdo");
        listaExpsOpcional();
        match("pnt_parentesisDerecho");
    }

    private void listaExpsOpcional() throws LexicalException, SyntacticException, IOException {
        if (firsts.isFirst("Expresion", actualToken.getTokenType())) {
            expresion();
            listaExps();
        } else { }
    }

    private void listaExps() throws LexicalException, SyntacticException, IOException {
        if (Objects.equals(actualToken.getTokenType(), "pnt_coma")) {
            match("pnt_coma");
            expresion();
            listaExps();
        } else { }
    }

    private void encadenadoVarMetodo() throws LexicalException, SyntacticException, IOException {
        if (Objects.equals(actualToken.getTokenType(), "pnt_parentesisIzquierdo")) {
            argsActuales();
        } else { }
    }
}

