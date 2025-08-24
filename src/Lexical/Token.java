package Lexical;

public class Token{

    String tipoToken;
    String lexema;
    int nroLinea;

    public Token(String tipoToken, String lexema, int nroLinea){
        this.tipoToken = tipoToken;
        this.lexema = lexema;
        this.nroLinea = nroLinea;
    }

    public String getTipoToken(){
        return tipoToken;
    }

    public String getLexema(){
        return lexema;
    }

    public int getNroLinea(){
        return nroLinea;
    }

    public void setTipoToken(String token){
        return tipoToken = token;
    }

    public void setLexema(String lex){
        return lexema = lex;
    }

    public void setNroLinea(int line){
        return nroLinea = line;
    }
}