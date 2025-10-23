package Syntactic;

import Exceptions.LexicalException;
import Exceptions.SemanticException;
import Exceptions.SyntacticException;
import Lexical.LexicalAnalyzer;
import Lexical.Token;
import Main.MainSemantic;
import Semantic.*;
import Semantic.Ast.Chained.ChainedCallNode;
import Semantic.Ast.Chained.ChainedNode;
import Semantic.Ast.Chained.ChainedVariableNode;
import Semantic.Ast.Expressions.*;
import Semantic.Ast.Expressions.Access.*;
import Semantic.Ast.Expressions.Literals.*;
import Semantic.Ast.Sentences.*;
import Semantic.Types.PrimitiveType;
import Semantic.Types.ReferenceType;
import Semantic.Types.Type;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    public void start() throws SyntacticException, LexicalException, IOException, SemanticException {
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

    private void inicial() throws LexicalException, SyntacticException, IOException, SemanticException {
        listaClasesInterfaces();
        match("EOF");
    }

    private void listaClasesInterfaces() throws LexicalException, SyntacticException, IOException, SemanticException {
        if (firsts.isFirst("clase", actualToken.getTokenType())) {
            clase();
            listaClasesInterfaces();
        } else {
            if (firsts.isFirst("interfaz", actualToken.getTokenType())) {
                //interfaz();
                listaClasesInterfaces();
            } else { }
        }
    }

    private void clase() throws LexicalException, SyntacticException, IOException, SemanticException {
            Token modificador = modificadorOpcional();
            match("pr_class");
            Token name = actualToken;
            match("idClase");
            ConcreteClass c = new ConcreteClass(name, modificador);
            MainSemantic.ST.setCurrentClass(c);
            Token ancestorName = herenciaOpcional();
            MainSemantic.ST.getCurrentClass().setInheritance(ancestorName);
            match("pnt_llaveIzquierda");
            listaMiembros();
            match("pnt_llaveDerecha");
            MainSemantic.ST.insertClass(MainSemantic.ST.getCurrentClass());
    }

    private Token herenciaOpcional() throws LexicalException, SyntacticException, IOException {
        if(actualToken.getTokenType().equals("pr_extends")){
            match("pr_extends");
            Token nom = actualToken;
            match("idClase");
            return nom;
        } else {
            if(actualToken.getTokenType().equals("pnt_llaveIzquierda")){
                return new Token("idClase", "Object", 0);
            } else {
                throw new SyntacticException(actualToken.getTokenType(), actualToken);
            }
        }
    }

    /*
    private void interfaz() throws LexicalException, SyntacticException, IOException, SemanticException {
        match("pr_interface");
        Token name = actualToken;
        match("idClase");
        ConcreteClass i = new ConcreteClass(name);
        MainSemantic.ST.setCurrentClass(i);
        herenciaOpcionalInterfaz();
        match("pnt_llaveIzquierda");
        listaMiembrosInterfaz();
        match("pnt_llaveDerecha");
        MainSemantic.ST.insertClass(MainSemantic.ST.getCurrentClass());
    }
    */

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

    private Token modificadorOpcional() throws LexicalException, SyntacticException, IOException {
        Token retorno = null;
        if (firsts.isFirst("modificadorOpcionalMiembros", actualToken.getTokenType())) {
            retorno = modificadorOpcionalMiembros();
        } else { }
        return retorno;
    }

    private void herenciaOpcionalInterfaz() throws LexicalException, SyntacticException, IOException {
        if (actualToken.getTokenType().equals("pr_extends")) {
            match("pr_extends");
            match("idClase");
        } else { }
    }

    private void listaMiembros() throws LexicalException, SyntacticException, IOException, SemanticException {
        if (firsts.isFirst("miembro", actualToken.getTokenType())) {
            miembro();
            listaMiembros();
        } else { }
    }

    private Token modificadorOpcionalMiembros() throws LexicalException, SyntacticException, IOException {
        Token retorno = null;
        if (actualToken.getTokenType().equals("pr_abstract")) {
            retorno = actualToken;
            match("pr_abstract");
        } else if (actualToken.getTokenType().equals("pr_static")) {
            retorno = actualToken;
            match("pr_static");
        } else if (actualToken.getTokenType().equals("pr_final")) {
            retorno = actualToken;
            match("pr_final");
        } else {
            throw new SyntacticException("modificadorOpcionalMiembros", actualToken);
        }
        return retorno;
    }

    private void miembro() throws LexicalException, SyntacticException, IOException, SemanticException {
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

    private void miembroMetodo(Token token, Type type) throws LexicalException, SyntacticException, IOException, SemanticException {
        if (firsts.isFirst("argsFormales", actualToken.getTokenType())) {
            Token modifier = modificadorOpcional();
            Method method = new Method(token, modifier ,type);
            MainSemantic.ST.setCurrentMethod(method);
            List<Parameter> params = argsFormales();
            for (Parameter p : params) {
                MainSemantic.ST.getCurrentMethod().addParameters(p);
            }
            bloqueOpcional();
            //MainSemantic.ST.getCurrentMethod().setHasBlock(bloqueOpcional());
            MainSemantic.ST.getCurrentClass().addMethod(method);
        } else {
            if (Objects.equals(actualToken.getTokenType(), "pnt_puntoYComa")) {
                Attribute attribute = new Attribute(token,type);
                match("pnt_puntoYComa");
                MainSemantic.ST.getCurrentClass().addAttributes(attribute);
            } else {
                throw new SyntacticException("miembroMetodo", actualToken);
            }
        }
    }

    private void constructor() throws LexicalException, SyntacticException, IOException, SemanticException {
        match("pr_public");
        Token token = actualToken;
        match("idClase");
        Constructor cons = new Constructor(token);
        MainSemantic.ST.setCurrentConstructor(cons);
        List<Parameter> params = argsFormales();
        for (Parameter p : params) {
            cons.addParameter(p);
        }
        bloque();
        MainSemantic.ST.getCurrentClass().addConstructor(cons);
    }

    private Type tipoMetodo() throws LexicalException, SyntacticException, IOException {
        if (firsts.isFirst("tipo", actualToken.getTokenType())) {
            return tipo();
        } else {
            if(Objects.equals(actualToken.getTokenType(), "pr_void")) {
                Token token = actualToken;
                match("pr_void");
                return new PrimitiveType(token);
            } else {
                throw new SyntacticException("tipoMetodo", actualToken);
            }
        }
    }

    private Type tipo() throws LexicalException, SyntacticException, IOException {
        Type type;
        Token token;
        if (firsts.isFirst("tipoPrimitivo", actualToken.getTokenType())) {
            token =  actualToken;
            tipoPrimitivo();
            type = new PrimitiveType(token);
        } else if (Objects.equals(actualToken.getTokenType(), "idClase")) {
            token =  actualToken;
            match("idClase");
            type = new ReferenceType(token);
        } else {
            throw new SyntacticException("tipo", actualToken);
        }
        return type;
    }

    private void tipoPrimitivo() throws LexicalException, SyntacticException, IOException {
        switch (actualToken.getTokenType()) {
            case "pr_boolean" -> match("pr_boolean");
            case "pr_char" -> match("pr_char");
            case "pr_int" -> match("pr_int");
        }
    }

    private void miembroAtributoAux(Token token, Type type) throws LexicalException, SyntacticException, IOException, SemanticException {
        if (Objects.equals(actualToken.getTokenType(), "op_asignacion")) {
            Attribute attribute = new Attribute(token, type);
            match("op_asignacion");
            expresionCompuesta();
            match("pnt_puntoYComa");
            MainSemantic.ST.getCurrentClass().addAttributes(attribute);
        } else if (firsts.isFirst("miembroMetodo", actualToken.getTokenType())) {
            miembroMetodo(token,type);
        } else {
            throw new SyntacticException("miembroAtributoAux", actualToken);
        }
    }

    private void metodoAtributo() throws LexicalException, SyntacticException, IOException, SemanticException {
        if (firsts.isFirst("tipo", actualToken.getTokenType())) {
            Type type = tipo();
            Token token = actualToken;
            match("idMetVar");
            miembroAtributoAux(token, type);
        } else if (actualToken.getTokenType().equals("pr_void")) {
            Token modifier = modificadorOpcional();
            match("pr_void");
            Token methodName = actualToken;
            match("idMetVar");
            Method method = new Method(methodName,modifier,new PrimitiveType(new Token("pr_void", "void", actualToken.getLineNumber())));
            MainSemantic.ST.setCurrentMethod(method);
            List<Parameter> params = argsFormales();
            for (Parameter p : params) {
                method.addParameters(p);
            }
            bloqueOpcional();
            //MainSemantic.ST.getCurrentMethod().setBlockNode(bloqueOpcional());
            MainSemantic.ST.getCurrentClass().addMethod(method);
        } else {
            throw new SyntacticException("metodoAtributo", actualToken);
        }
    }

    private void metodoModificador() throws LexicalException, SyntacticException, IOException, SemanticException {
        Token modifier = modificadorOpcionalMiembros();
        Type type = tipoMetodo();
        Token name = actualToken;
        match("idMetVar");
        Method method = new Method(name, modifier, type);
        MainSemantic.ST.setCurrentMethod(method);
        List<Parameter> params = argsFormales();
        for (Parameter p : params) {
            method.addParameters(p);
        }
        bloqueOpcional();
        //MainSemantic.ST.getCurrentMethod().setBlockNode(bloqueOpcional());
        MainSemantic.ST.getCurrentClass().addMethod(method);
    }

    private List<Parameter> argsFormales() throws LexicalException, SyntacticException, IOException {
            List<Parameter> args;
            match("pnt_parentesisIzquierdo");
            args = listaArgsFormalesOpcional();
            match("pnt_parentesisDerecho");
            return args;
    }

    private List<Parameter> listaArgsFormalesOpcional() throws LexicalException, SyntacticException, IOException {
        List<Parameter> args = new ArrayList<>();
        if (firsts.isFirst("listaArgsFormales", actualToken.getTokenType())) {
            args = listaArgsFormales();
        }
        return args;
    }

    private List<Parameter> listaArgsFormales() throws LexicalException, SyntacticException, IOException {
            List<Parameter> args = new ArrayList<>();
            if(firsts.isFirst("argFormal", actualToken.getTokenType())) {
                args.add(argFormal());
                args.addAll(listaArgsFormalesTerminal());
            } else {
                throw new SyntacticException("argFormal", actualToken);
            }
            return args;
    }

    private List<Parameter> listaArgsFormalesTerminal() throws LexicalException, SyntacticException, IOException {
        List<Parameter> args = new ArrayList<>();
        if (Objects.equals(actualToken.getTokenType(), "pnt_coma")) {
            match("pnt_coma");
            args.add(argFormal());
            args.addAll(listaArgsFormalesTerminal());
        } else { }
        return args;
    }

    private Parameter argFormal() throws LexicalException, SyntacticException, IOException {
        Parameter parameter = null;
        if (firsts.isFirst("tipo", actualToken.getTokenType())) {
            Type type = tipo();
            Token id = actualToken;
            match("idMetVar");
            parameter = new Parameter(id, type);
        } else {
            throw new SyntacticException("argFormal", actualToken);
        }
        return parameter;
    }

    private void bloqueOpcional() throws LexicalException, SyntacticException, IOException {
        BlockNode block;
        if (firsts.isFirst("bloque", actualToken.getTokenType())){
            block = bloque();
            MainSemantic.ST.getCurrentMethod().setHasBlock(true);
            MainSemantic.ST.getCurrentMethod().setBlockNode(block);
        } else {
            if(Objects.equals(actualToken.getTokenType(), "pnt_puntoYComa")){
                match("pnt_puntoYComa");
                MainSemantic.ST.getCurrentMethod().setHasBlock(false);
                MainSemantic.ST.getCurrentMethod().setBlockNode(new NullBlockNode());
            } else {
                throw new SyntacticException("bloqueOpcional", actualToken);
            }
        }
    }

    private BlockNode bloque() throws LexicalException, SyntacticException, IOException {
        BlockNode block = new BlockNode();
        if(Objects.equals(actualToken.getTokenType(), "pnt_llaveIzquierda")) {
            match("pnt_llaveIzquierda");
            listaSentencias(block);
            match("pnt_llaveDerecha");
            MainSemantic.ST.setCurrentBlock(block);
        }
        return block;
    }

    private void listaSentencias(BlockNode block) throws LexicalException, SyntacticException, IOException {
        if (firsts.isFirst("sentencia", actualToken.getTokenType())) {
            SentenceNode sentence = sentencia();
            block.addSentence(sentence);
            listaSentencias(block);
        } else { }
    }

    private SentenceNode sentencia() throws LexicalException, SyntacticException, IOException {
        if (Objects.equals(actualToken.getTokenType(), "pnt_puntoYComa")) {
            match("pnt_puntoYComa");
            return new EmptySentenceNode();
        } else if (firsts.isFirst("asignacionLlamada", actualToken.getTokenType())) {
            AssignationNode assignationNode = new AssignationNode(actualToken, asignacionLlamada());
            match("pnt_puntoYComa");
            return assignationNode;
        } else if (firsts.isFirst("varLocal", actualToken.getTokenType())) {
            LocalVarNode varNode = varLocal();
            match("pnt_puntoYComa");
            return varNode;
        } else if (firsts.isFirst("return", actualToken.getTokenType())) {
            ReturnNode returnNode = retorno();
            match("pnt_puntoYComa");
            return returnNode;
        } else if (firsts.isFirst("if", actualToken.getTokenType())) {
            IfNode ifNode = ifPrincipal();
            return ifNode;
        } else if (firsts.isFirst("for", actualToken.getTokenType())) {
            forPrincipal();
        } else if (firsts.isFirst("while", actualToken.getTokenType())) {
            WhileNode whileNode = whilePrincipal();
            return whileNode;
        } else if (firsts.isFirst("bloque", actualToken.getTokenType())) {
            BlockNode blockNode = bloque();
            return blockNode;
        }  else {
            throw new SyntacticException("sentencia", actualToken);
        }
        return new EmptySentenceNode();
    }

    private ExpressionNode asignacionLlamada() throws LexicalException, SyntacticException, IOException {
        if (firsts.isFirst("expresion", actualToken.getTokenType())) {
            return expresion();
        } else {
            throw  new SyntacticException("asignacionLlamada", actualToken);
        }
    }

    private LocalVarNode varLocal() throws LexicalException, SyntacticException, IOException {
        LocalVarNode localVar;
        match("pr_var");
        Token token = actualToken;
        match("idMetVar");
        match("op_asignacion");
        localVar = new LocalVarNode(token);
        localVar.setExpression(expresionCompuesta());
        return localVar;
    }

    private ReturnNode retorno() throws LexicalException, SyntacticException, IOException {
        Token token = actualToken;
        match("pr_return");
        ReturnNode rn = new ReturnNode(token);
        rn.setOptionalExpression(expresionOpcional());
        rn.setReturnExpected(MainSemantic.ST.getCurrentMethod().getReturnType());
        return rn;
    }

    private ExpressionNode expresionOpcional() throws LexicalException, SyntacticException, IOException {
        if (firsts.isFirst("expresion", actualToken.getTokenType())) {
            return expresion();
        } else {
            return new EmptyExpression();
        }
    }

    private IfNode ifPrincipal() throws LexicalException, SyntacticException, IOException {
        Token ifToken = actualToken;
        match("pr_if");
        match("pnt_parentesisIzquierdo");
        ExpressionNode exp = expresion();
        match("pnt_parentesisDerecho");
        SentenceNode senIf = sentencia();
        SentenceNode senElse = ifAux();
        return new IfNode(exp, senIf, senElse, ifToken);
    }

    private SentenceNode ifAux() throws LexicalException, SyntacticException, IOException {
        if (actualToken.getTokenType().equals("pr_else")) {
            match("pr_else");
            return sentencia();
        } else {
            return new EmptySentenceNode();
        }
    }

    private WhileNode whilePrincipal() throws LexicalException, SyntacticException, IOException {
        Token tokenWhile = actualToken;
        match("pr_while");
        match("pnt_parentesisIzquierdo");
        ExpressionNode expNode = expresion();
        match("pnt_parentesisDerecho");
        SentenceNode senNode = sentencia();
        return new WhileNode(tokenWhile, expNode, senNode);
    }

    private ExpressionNode expresion() throws LexicalException, SyntacticException, IOException {
        if (firsts.isFirst("expresionCompuesta", actualToken.getTokenType())) {
            ExpressionNode exp = expresionCompuesta();
            return expresionAux(exp);
        } else {
            throw new SyntacticException("expresion", actualToken);
        }
    }

    private ExpressionNode expresionAux(ExpressionNode exp) throws LexicalException, SyntacticException, IOException {
        if (firsts.isFirst("operadorAsignacion", actualToken.getTokenType())) {
            Token opToken = actualToken;
            operadorAsignacion();
            return new AssignationExpressionNode(exp, expresionCompuesta(), opToken);
        } else { }
        return exp;
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

    private ExpressionNode expresionCompuesta() throws LexicalException, SyntacticException, IOException {
        if (firsts.isFirst("expresionBasica", actualToken.getTokenType())){
            ExpressionNode exp = expresionBasica();
            return expresionCompuestaTerminal(exp);
        } else {
            throw new SyntacticException("expresionCompuesta", actualToken);
        }
    }

    private ExpressionNode expresionCompuestaTerminal(ExpressionNode exp) throws LexicalException, SyntacticException, IOException {
        if (firsts.isFirst("operadorBinario", actualToken.getTokenType())) {
            Token operator = actualToken;
            OperadorBinario();
            ExpressionNode basicExp = expresionBasica();
            BinaryExpressionNode binaryExpressionNode = new BinaryExpressionNode(exp, basicExp, operator);
            ExpressionNode retorn = expresionCompuestaTerminal(binaryExpressionNode);
            return retorn;
        } /*else {
            if (actualToken.getTokenType().equals("op_ternario")) {
                match("op_ternario");
                expresion();
                match("pnt_dosPuntos");
                expresion();
            } */ else { }
        return exp;
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

    private ExpressionNode expresionBasica() throws LexicalException, SyntacticException, IOException {
        if (firsts.isFirst("operadorUnario", actualToken.getTokenType())) {
            Token token = actualToken;
            operadorUnario();
            return new UnaryExpressionNode(operando(), token);
        } else {
            if (firsts.isFirst("operando", actualToken.getTokenType())) {
                return operando();
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

    private OperandNode operando() throws LexicalException, SyntacticException, IOException {
        if (firsts.isFirst("primitivo", actualToken.getTokenType())) {
            return primitivo();
        } else {
            if (firsts.isFirst("referencia", actualToken.getTokenType())) {
                return referencia();
            } else {
                throw new SyntacticException("operando", actualToken);
            }
        }
    }

    private LiteralNode primitivo() throws SyntacticException, LexicalException, IOException {
        LiteralNode literal;
        switch (actualToken.getTokenType()) {
            case "pr_true" -> {
                literal = new BooleanLiteralNode(actualToken);
                match("pr_true");
            }
            case "pr_false" -> {
                literal = new BooleanLiteralNode(actualToken);
                match("pr_false");
            }
            case "intLiteral" -> {
                literal =  new IntLiteralNode(actualToken);
                match("intLiteral");
            }
            case "charLiteral" -> {
                literal = new CharLiteralNode(actualToken);
                match("charLiteral");
            }
            case "pr_null" -> {
                literal = new NullLiteralNode(actualToken);
                match("pr_null");
            }
            default ->
                    throw new SyntacticException("primitivo", actualToken);
        }
        return literal;
    }

    private AccessNode referencia() throws LexicalException, SyntacticException, IOException {
        AccessNode node;
        if (firsts.isFirst("primario", actualToken.getTokenType())) {
            node = primario();
            node.setChaining(referenciaTerminal());
            return node;
        } else {
            throw new SyntacticException("referencia", actualToken);
        }
    }

    private ChainedNode referenciaTerminal() throws LexicalException, SyntacticException, IOException {
        if (actualToken.getTokenType().equals("pnt_punto")) {
            match("pnt_punto");
            Token token = actualToken;
            match("idMetVar");
            List<ExpressionNode> opParameters = encadenadoVarMetodo();
            if(opParameters != null){
                ChainedCallNode ret = new ChainedCallNode(token);
                ret.setArgumentList(opParameters);
                ret.setChaining(referenciaTerminal());
                return ret;
            } else {
                ChainedVariableNode ret = new ChainedVariableNode(token);
                ret.setChaining(referenciaTerminal());
                return ret;
            }
        } else {
            return null; //parche
        }
    }

    private AccessNode primario() throws LexicalException, SyntacticException, IOException {
        AccessNode operator;
        switch (actualToken.getTokenType()) {
            case "pr_this" -> {
                    operator = new thisAccessNode(actualToken);
                    match("pr_this");
            }
            case "stringLiteral" -> {
                operator = new StringLiteralNode(actualToken);
                match("stringLiteral");
            }
            case "idMetVar" -> {
                Token tokenIdMetVar = actualToken;
                operator = accesoVarMetodo(tokenIdMetVar);
            }
            case "pr_new" -> {
                operator = llamadaConstructor();
            }
            case "idClase" -> {
                operator =  llamadaMetodoEstatico();
            }
            case "pnt_parentesisIzquierdo" -> {
                operator = expresionParentizada();
            }
            default -> throw new SyntacticException("primario", actualToken);
        }
        return operator;
    }

    private AccessNode accesoVarMetodo(Token tokenIdMetVar) throws LexicalException, SyntacticException, IOException {
            if (actualToken.getTokenType().equals("idMetVar")) {
            match("idMetVar");
            return argsAux(tokenIdMetVar);
        } else {
            throw new SyntacticException("accesoVarMetodo", actualToken);
        }
    }

    private AccessNode argsAux(Token tokenIdMetVar) throws LexicalException, SyntacticException, IOException {
        if (firsts.isFirst("argsActuales", actualToken.getTokenType())) {
            List<ExpressionNode> currentParamList = argsActuales();
            return new MethodAccessNode(tokenIdMetVar, currentParamList);
        } else {
            return new VarAccessNode(tokenIdMetVar);
        }
    }

    private ConstructorAccessNode llamadaConstructor() throws LexicalException, SyntacticException, IOException {
        match("pr_new");
        Token tokenConstructor = actualToken;
        match("idClase");
        ConstructorAccessNode constructorAccess = new ConstructorAccessNode(tokenConstructor);
        constructorAccess.setArguments(argsActuales());
        return constructorAccess;
    }

    private ExpressionParenthesesAccess expresionParentizada() throws LexicalException, SyntacticException, IOException {
        match("pnt_parentesisIzquierdo");
        ExpressionNode expression = expresion();
        match("pnt_parentesisDerecho");
        return new ExpressionParenthesesAccess(expression);
    }

    private StaticMethodAccessNode llamadaMetodoEstatico() throws LexicalException, SyntacticException, IOException {
            Token staticClassToken = actualToken;
            match("idClase");
            match("pnt_punto");
            Token staticMethodToken = actualToken;
            match("idMetVar");
            List<ExpressionNode> parameters = argsActuales();
            return new StaticMethodAccessNode(staticClassToken, staticMethodToken, parameters);
    }

    private List<ExpressionNode> argsActuales() throws LexicalException, SyntacticException, IOException {
        match("pnt_parentesisIzquierdo");
        List<ExpressionNode> currentParamList = listaExpsOpcional();
        match("pnt_parentesisDerecho");
        return currentParamList;
    }

    private  List<ExpressionNode> listaExpsOpcional() throws LexicalException, SyntacticException, IOException {
        List<ExpressionNode> paramList = null;
        if (firsts.isFirst("expresion", actualToken.getTokenType())) {
            ExpressionNode expressionNode = expresion();
                paramList = listaExpsAux();
            if(paramList != null) {
                paramList.add(expressionNode);
                return paramList;
            }
        } else { }
        return paramList;
    }

    private List<ExpressionNode> listaExpsAux() throws LexicalException, SyntacticException, IOException {
        List<ExpressionNode> paramList = null;
        if (Objects.equals(actualToken.getTokenType(), "pnt_coma")) {
            match("pnt_coma");
            ExpressionNode expressionNode = expresion();
            paramList = listaExpsAux();
            if(paramList != null) {
                paramList.add(expressionNode);
                return paramList;
            }
        } else { }
        return paramList;
    }

    private List<ExpressionNode> encadenadoVarMetodo() throws LexicalException, SyntacticException, IOException {
        List<ExpressionNode> ret = null;
        if (firsts.isFirst("argsActuales", actualToken.getTokenType())) {
            ret = argsActuales();
        } else { }
        return ret;
    }
}

