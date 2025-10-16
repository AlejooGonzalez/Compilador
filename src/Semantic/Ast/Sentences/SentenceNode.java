package Semantic.Ast.Sentences;

import Exceptions.SemanticException;

public abstract class SentenceNode {
    abstract public void check() throws SemanticException;
}
