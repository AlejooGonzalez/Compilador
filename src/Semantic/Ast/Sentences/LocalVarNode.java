package Semantic.Ast.Sentences;

import Exceptions.SemanticException;
import Lexical.Token;
import Main.MainSemantic;
import Semantic.Ast.Expressions.Access.ExpressionParenthesesAccess;
import Semantic.Ast.Expressions.AssignationExpressionNode;
import Semantic.Ast.Expressions.ExpressionNode;
import Semantic.Types.NullType;
import Semantic.Types.Type;
import Semantic.Types.VoidType;

public class LocalVarNode extends SentenceNode {
    private Token token;
    private ExpressionNode expression;
    private Type type;
    private int offset;

    public LocalVarNode(Token token) {
        this.token = token;
    }

    @Override
    public void check() throws SemanticException {
        if(localVarDeclaratedInParameters()){
            throw new SemanticException("La variable ya fue declarada en los parametros", token, token.getLineNumber());
        }
        /*
        if(localVarDeclaratedInAttributes()){
            throw new SemanticException("Local Var ya declarada en atributos", token, token.getLineNumber());
        } */
        checkLocalVarInParentNode();
        type = expression.check();
        if(localVarNullValued()){
            throw new SemanticException("Una variable no puede tener un valor nulo",  token, token.getLineNumber());
        }
        if(localVarVoidValued()){
            throw new SemanticException("Una variable no puede tener un valor void",  token, token.getLineNumber());
        }
        if (expression instanceof ExpressionParenthesesAccess paramReference){
            if (paramReference.getExpression() instanceof AssignationExpressionNode){
                throw new SemanticException("Expresion invalida a izquierda de asignacion: ", token ,token.getLineNumber());
            }
        }
        MainSemantic.ST.getCurrentBlock().addLocalVariables(token.getLexeme(), this);
    }

    @Override
    public void generate() {
        MainSemantic.ST.getInstructionsList().add("RMEM 1 ; Reserva memoria para la variable local " + token.getLexeme());
        if(expression != null){
            expression.generate();
            MainSemantic.ST.getInstructionsList().add("STORE "+ offset +" ; Almacena  el valor de la expresion del tope de la pila en la variable local");
        }
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
    public int getOffset() {
        return offset;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token nombre) {
        this.token = nombre;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void checkLocalVarInParentNode() throws SemanticException {
        BlockNode parentBlock = MainSemantic.ST.getCurrentBlock().getParent();
        while(parentBlock != null){
            if(parentBlock.getLocalVar(token.getLexeme()) != null){
                throw new SemanticException("Variable declarada en un bloque padre", token, token.getLineNumber());
            }
            parentBlock = parentBlock.getParent();
        }
    }

    public void setExpression(ExpressionNode expression) {
        this.expression = expression;
    }

    public boolean localVarDeclaratedInParameters(){
        return MainSemantic.ST.getCurrentMethod().getParameters().containsKey(token.getLexeme());
    }

    public boolean localVarDeclaratedInAttributes(){
        return MainSemantic.ST.getCurrentClass().getAttribute(token.getLexeme()) != null;
    }

    public boolean localVarNullValued(){
        return type.itsCompatible(new NullType(token.getLineNumber()));
    }

    public boolean localVarVoidValued(){
        return type.itsCompatible(new VoidType(token.getLineNumber()));
    }
}