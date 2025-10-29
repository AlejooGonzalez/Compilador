package Semantic.Ast.Sentences;

import Exceptions.SemanticException;
import Main.MainSemantic;
import Semantic.ConcreteClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockNode extends SentenceNode {
    private List<SentenceNode> sentences;
    private Map<String, LocalVarNode> localVariables;
    private BlockNode parent;
    private ConcreteClass concreteClass;
    private boolean itsChecked;

    public BlockNode() {
        this.sentences = new ArrayList<>();
        this.localVariables = new HashMap<>();
        this.itsChecked = false;
        concreteClass = MainSemantic.ST.getCurrentClass();
    }

    public void addLocalVariables(String string, LocalVarNode localVarNode) throws SemanticException {
        if(localVariables.containsKey(string)){
            throw new SemanticException("La variable ya fue declarada en el bloque", localVarNode.getToken(), localVarNode.getToken().getLineNumber());
        }
        localVariables.put(string, localVarNode);
    }

    public LocalVarNode getLocalVar(String idVar){
        return localVariables.get(idVar);
    }


    public void addSentence(SentenceNode sentence) {
        sentences.add(sentence);
    }

    public List<SentenceNode> getSentences() {
        return sentences;
    }

    @Override
    public void check() throws SemanticException {
        parent = MainSemantic.ST.getCurrentBlock();
        MainSemantic.ST.setCurrentBlock(this);
        for(SentenceNode s: sentences) {
            s.check();
        }
        itsChecked = true;
        MainSemantic.ST.setCurrentBlock(parent);
    }

    public BlockNode getParent() {
        return parent;
    }

    public boolean getItsChecked() {
        return itsChecked;
    }
}
