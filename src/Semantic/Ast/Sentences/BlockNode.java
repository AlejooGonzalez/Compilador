package Semantic.Ast.Sentences;

import Exceptions.SemanticException;
import Main.MainSemantic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockNode extends SentenceNode {
    private List<SentenceNode> sentences;
    private Map<String, LocalVarNode> localVariables;

    public BlockNode() {
        this.sentences = new ArrayList<>();
        localVariables = new HashMap<>();
        MainSemantic.ST.setCurrentBlock(this);
    }

    public void addLocalVariables(String string, LocalVarNode localVarNode) {
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
        for(SentenceNode s: sentences)
            s.check();
    }
}
