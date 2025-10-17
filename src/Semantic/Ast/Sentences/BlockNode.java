package Semantic.Ast.Sentences;

import Exceptions.SemanticException;

import java.util.ArrayList;
import java.util.List;

public class BlockNode extends SentenceNode {
    private List<SentenceNode> sentences;

    public BlockNode() {
        this.sentences = new ArrayList<>();
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
