package Lexical;

public class Token{
    String tokenType;
    String lexeme;
    int lineNumber;

    public Token(String tokenType, String lexeme, int lineNumber){
        this.tokenType = tokenType;
        this.lexeme = lexeme;
        this.lineNumber = lineNumber;
    }
    public String getTokenType(){
        return tokenType;
    }

    public String getLexeme(){
        return lexeme;
    }

    public int getLineNumber(){
        return lineNumber;
    }
}