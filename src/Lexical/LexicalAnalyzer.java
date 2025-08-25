package Lexical;

import java.io.IOException;
import SourceManager.SourceManagerImplementation;

public class LexicalAnalyzer {
    String lexema;
    char caracterActual;
    SourceManagerImplementation SourceManager;

    public LexicalAnalyzer(SourceManagerImplementation sourceManager) throws IOException {
        this.SourceManager = sourceManager;
        actualizarCaracterActual();
        
    }

    public Token proximoToken() throws IOException{
        lexema = "";
        return e0();
    }

    private void actualizarCaracterActual() throws IOException {
        caracterActual = SourceManager.getNextChar();
    }

    private Token e0() throws IOException{
        switch(caracterActual){

            //Espacios en blanco
            case ' ':
            case '\n':
            case '\t': {
                actualizarCaracterActual();
                return e0();
            }

            //Operadores
            case '>':{
                actualizarLexema();
                actualizarCaracterActual();
                return e1();
                }

            case '<':{
                actualizarLexema();
                actualizarCaracterActual();
                return e2();
                }

            case '!':{
                actualizarLexema();
                actualizarCaracterActual();
                return e3();
                }

            case '=':{
                actualizarLexema();
                actualizarCaracterActual();
                return e4();
                }

            case '&':{
                actualizarLexema();
                actualizarCaracterActual();
                return e5();
                }

            case '|':{
                actualizarLexema();
                actualizarCaracterActual();
                return e6();
                }

            case '%':{
                actualizarLexema();
                actualizarCaracterActual();
                return e7();
                }

            case '+':{
                actualizarLexema();
                actualizarCaracterActual();
                return e8();
                }

            case '-':{
                actualizarLexema();
                actualizarCaracterActual();
                return e9();
                }

            case '*':{
                actualizarLexema();
                actualizarCaracterActual();
                return e10();
                }

            case '/':{
                actualizarLexema();
                actualizarCaracterActual();
                return e11();
                }

            //Puntuacion
            case '(':{
                actualizarLexema();
                actualizarCaracterActual();
                return e12();
                }

            case ')':{
                actualizarLexema();
                actualizarCaracterActual();
                return e13();
                }

            case '{':{
                actualizarLexema();
                actualizarCaracterActual();
                return e14();
                }

            case '}':{
                actualizarLexema();
                actualizarCaracterActual();
                return e15();
                }

            case ';':{
                actualizarLexema();
                actualizarCaracterActual();
                return e16();
                }

            case ',':{
                actualizarLexema();
                actualizarCaracterActual();
                return e17();
                }

            case '.':{
                actualizarLexema();
                actualizarCaracterActual();
                return e18();
                }

            case ':':{
                actualizarLexema();
                actualizarCaracterActual();
                return e19();
                }
            }

        return null;
        }

        private Token e1() throws IOException{
            if(caracterActual == '='){
                actualizarLexema();
                actualizarCaracterActual();
                return new Token("op_mayorIgual",lexema, SourceManager.getLineNumber());
            } else {
                return new Token("op_mayor", lexema, SourceManager.getLineNumber());
            }
        }

        private Token e2() throws IOException{
            if(caracterActual == '='){
                actualizarLexema();
                actualizarCaracterActual();
                return new Token("op_menorIgual",lexema, SourceManager.getLineNumber());
            } else {
                return new Token("op_menor", lexema, SourceManager.getLineNumber());
            }
        }

        private Token e3() throws IOException{
            if(caracterActual == '='){
                actualizarLexema();
                actualizarCaracterActual();
                return new Token("op_distinto",lexema, SourceManager.getLineNumber());
            } else {
                return new Token("op_negacion", lexema, SourceManager.getLineNumber());
            }
        }

        private Token e4() throws IOException{
            if(caracterActual == '='){
                actualizarLexema();
                actualizarCaracterActual();
                return new Token("op_igual",lexema, SourceManager.getLineNumber());
            } else {
                return new Token("op_asignacion", lexema, SourceManager.getLineNumber());
            }
        }

        private Token e5() throws IOException{
            if(caracterActual == '&'){
                actualizarLexema();
                actualizarCaracterActual();
                return new Token("op_and",lexema, SourceManager.getLineNumber());
            } else {
                return new Token("op_asignacion", lexema, SourceManager.getLineNumber()); //MAL, duda para consultar
            }
        }

        private Token e6() throws IOException{
            if(caracterActual == '|'){
                actualizarLexema();
                actualizarCaracterActual();
                return new Token("op_or",lexema, SourceManager.getLineNumber());
            } else {
                return new Token("op_asignacion", lexema, SourceManager.getLineNumber()); //MAL, duda para consultar
            }
        }

        private Token e7() throws IOException{
            return new Token("op_modulo",lexema, SourceManager.getLineNumber());
        }

        private Token e8() throws IOException{
            if(caracterActual == '+'){
                actualizarLexema();
                actualizarCaracterActual();
                return new Token("op_incremento",lexema, SourceManager.getLineNumber());
            } else {
                return new Token("op_suma", lexema, SourceManager.getLineNumber()); 
            }
        }

        private Token e9() throws IOException{
            if(caracterActual == '-'){
                actualizarLexema();
                actualizarCaracterActual();
                return new Token("op_decremento",lexema, SourceManager.getLineNumber());
            } else {
                return new Token("op_resta", lexema, SourceManager.getLineNumber()); 
            }
        }

        private Token e10() throws IOException{
            return new Token("op_multiplicacion", lexema, SourceManager.getLineNumber()); 
        }
        
        //private Token e11() throws IOException{
        //    return new Token("op_multiplicacion", lexema, SourceManager.getLineNumber());
        //}

        //Puntacion
        private Token e12() throws IOException{
            return new Token("pnt_parentesisIzquierdo", lexema, SourceManager.getLineNumber()); 
        }

        private Token e13() throws IOException{
            return new Token("pnt_parentesisDerecho", lexema, SourceManager.getLineNumber()); 
        }

        private Token e14() throws IOException{
            return new Token("pnt_llaveIzquierda", lexema, SourceManager.getLineNumber()); 
        }

        private Token e15() throws IOException{
            return new Token("pnt_llaveDerecha", lexema, SourceManager.getLineNumber());
        }

        private Token e16() throws IOException{
            return new Token("pnt_puntoYComa", lexema, SourceManager.getLineNumber()); 
        }

        private Token e17() throws IOException{
            return new Token("pnt_coma", lexema, SourceManager.getLineNumber()); 
        }

        private Token e18() throws IOException{
            return new Token("pnt_punto", lexema, SourceManager.getLineNumber()); 
        }

        private Token e19() throws IOException{
            return new Token("pnt_dosPuntos", lexema, SourceManager.getLineNumber()); 
        }

        
        private void actualizarLexema() {
            lexema += caracterActual;
        }
    }


