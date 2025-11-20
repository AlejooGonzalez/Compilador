package Semantic.Ast.Sentences;

import Exceptions.SemanticException;
import Lexical.Token;
import Main.MainSemantic;
import Semantic.Ast.Expressions.ExpressionNode;
import Semantic.Types.BooleanType;

public class WhileNode extends SentenceNode{
    private SentenceNode whileBody;
    private ExpressionNode expression;
    private Token token;

    public WhileNode(Token token, ExpressionNode expression, SentenceNode whileBody){
        this.expression = expression;
        this.whileBody = whileBody;
        this.token = token;
    }

    @Override
    public void check() throws SemanticException {
        booleanCondition();
        whileBody.check();
    }

    @Override
    public void generate() {
        int whileCounter = MainSemantic.ST.getIfWhileCounter();
        String whileBegging = generateWhileBeggingLabel(whileCounter);
        String whileEnding = generateWhileEndingLabel(whileCounter);
        MainSemantic.ST.getInstructionsList().add(whileBegging + ": NOP");
        expression.generate();
        MainSemantic.ST.getInstructionsList().add("BF " +  whileEnding);
        whileBody.generate();
        MainSemantic.ST.getInstructionsList().add("JUMP " + whileBegging);
        MainSemantic.ST.getInstructionsList().add(whileEnding + ": NOP");
    }

    public void booleanCondition() throws SemanticException {
        if((expression.check() != null) && !(expression.check().itsCompatible(new BooleanType(token.getLineNumber())))){
            throw new SemanticException("La condicion del while no es de tipo booleano", token, token.getLineNumber());
        }
    }

    public String generateWhileBeggingLabel(int whileCounter){
        return "lbl_BeggingWhile"+whileCounter;
    }

    public String generateWhileEndingLabel(int whileCounter){
        return  "lbl_EndWhile"+whileCounter;
    }
}
