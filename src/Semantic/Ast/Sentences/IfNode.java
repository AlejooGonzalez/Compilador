package Semantic.Ast.Sentences;

import Exceptions.SemanticException;
import Lexical.Token;
import Main.MainSemantic;
import Semantic.Ast.Expressions.ExpressionNode;
import Semantic.Types.BooleanType;

public class IfNode extends SentenceNode{
    private ExpressionNode condition;
    private SentenceNode ifBody;
    private SentenceNode elseBody;
    private Token ifToken;

    private int labelNumber = 0;

    public IfNode(ExpressionNode condition,SentenceNode ifBody,SentenceNode elseBody, Token ifToken){
        this.condition = condition;
        this.ifBody = ifBody;
        this.elseBody = elseBody;
        this.ifToken = ifToken;
    }

    @Override
    public void check() throws SemanticException {
        if(!booleanCondition()){
            throw new SemanticException("La condicion del If debe ser un booleano", ifToken, ifToken.getLineNumber());
        }
        ifBody.check();
        elseBody.check();
    }

    @Override
    public void generate() {
        int ifCounter = MainSemantic.ST.getIfWhileCounter();
        String labelIf = generateIfLabel(ifCounter);
        String labelElse = generateElseLabel(ifCounter);
        if(elseBody != null){
            condition.generate();
            MainSemantic.ST.getInstructionsList().add("BF "+labelElse);
            ifBody.generate();
            MainSemantic.ST.getInstructionsList().add("JUMP "+labelIf);
            MainSemantic.ST.getInstructionsList().add(labelElse + ": NOP");
            elseBody.generate();
            MainSemantic.ST.getInstructionsList().add(labelIf + ": NOP");
        } else {
            condition.generate();
            MainSemantic.ST.getInstructionsList().add("BF "+labelIf);
            ifBody.generate();
            MainSemantic.ST.getInstructionsList().add(labelIf + ": NOP");
        }
    }

    public boolean booleanCondition() throws SemanticException {
        return condition.check().getLexeme().equals(new BooleanType(ifToken.getLineNumber()).getLexeme());
    }

    public String generateIfLabel(int num) {
        return "lbl_finif" + num;
    }

    public String generateElseLabel(int num) {
        return "lbl_else" + num;
    }
}

