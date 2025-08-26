package Lexical;

import java.io.IOException;
import Exceptions.LexicalException;
import SourceManager.SourceManagerImplementation;

public class LexicalAnalyzer {
    String lexema;
    char caracterActual;
    SourceManagerImplementation sourceManager;

    public LexicalAnalyzer(SourceManagerImplementation sourceManager) throws IOException {
        this.sourceManager = sourceManager;
        actualizarCaracterActual();
    }

    public Token proximoToken() throws IOException, LexicalException {
        lexema = "";
        return e0();
    }

    private void actualizarCaracterActual() throws IOException {
        caracterActual = sourceManager.getNextChar();
    }

    private Token e0() throws IOException, LexicalException {

        if (Character.isDigit(caracterActual)) {
            actualizarLexema();
            actualizarCaracterActual();
            return e20_digit();
        } else {
            if (Character.isUpperCase(caracterActual)) {
                actualizarLexema();
                actualizarCaracterActual();
                return e21_capitalLetter();
            } else {
                if (Character.isLowerCase(caracterActual)) {
                    actualizarLexema();
                    actualizarCaracterActual();
                    return e21_lowerLetter();
                }
            }
        }

        switch(caracterActual) {
            //Espacios en blanco
            case ' ':
            case '\n':
            case '\r':
            case '\t': {
                actualizarCaracterActual();
                return e0();
            }

            //Operadores
            case '>': {
                actualizarLexema();
                actualizarCaracterActual();
                return e1();
            }

            case '<': {
                actualizarLexema();
                actualizarCaracterActual();
                return e2();
            }

            case '!': {
                actualizarLexema();
                actualizarCaracterActual();
                return e3();
            }

            case '=': {
                actualizarLexema();
                actualizarCaracterActual();
                return e4();
            }

            case '&': {
                actualizarLexema();
                actualizarCaracterActual();
                return e5();
            }

            case '|': {
                actualizarLexema();
                actualizarCaracterActual();
                return e6();
            }

            case '%': {
                actualizarLexema();
                actualizarCaracterActual();
                return e7();
            }

            case '+': {
                actualizarLexema();
                actualizarCaracterActual();
                return e8();
            }

            case '-': {
                actualizarLexema();
                actualizarCaracterActual();
                return e9();
            }

            case '*': {
                actualizarLexema();
                actualizarCaracterActual();
                return e10();
            }

            /*case '/':{
                //actualizarLexema();
                //actualizarCaracterActual();
                return e11();
                }*/

            //Puntuacion
            case '(': {
                actualizarLexema();
                actualizarCaracterActual();
                return e12();
            }

            case ')': {
                actualizarLexema();
                actualizarCaracterActual();
                return e13();
            }

            case '{': {
                actualizarLexema();
                actualizarCaracterActual();
                return e14();
            }

            case '}': {
                actualizarLexema();
                actualizarCaracterActual();
                return e15();
            }

            case ';': {
                actualizarLexema();
                actualizarCaracterActual();
                return e16();
            }

            case ',': {
                actualizarLexema();
                actualizarCaracterActual();
                return e17();
            }

            case '.': {
                actualizarLexema();
                actualizarCaracterActual();
                return e18();
            }

            case ':': {
                actualizarLexema();
                actualizarCaracterActual();
                return e19();
            }
        }

            if(sourceManager.esEOF(caracterActual)){
                return e22_EOF();
            } else {
                actualizarLexema();
                throw new LexicalException(sourceManager.getColumnNumber(), sourceManager.getLineNumber(), lexema, sourceManager);
            }
        }

        private Token e1() throws IOException{
            if(caracterActual == '='){
                actualizarLexema();
                actualizarCaracterActual();
                return new Token("op_mayorIgual",lexema, sourceManager.getLineNumber());
            } else {
                return new Token("op_mayor", lexema, sourceManager.getLineNumber());
            }
        }

        private Token e2() throws IOException{
            if(caracterActual == '='){
                actualizarLexema();
                actualizarCaracterActual();
                return new Token("op_menorIgual",lexema, sourceManager.getLineNumber());
            } else {
                return new Token("op_menor", lexema, sourceManager.getLineNumber());
            }
        }

        private Token e3() throws IOException{
            if(caracterActual == '='){
                actualizarLexema();
                actualizarCaracterActual();
                return new Token("op_distinto",lexema, sourceManager.getLineNumber());
            } else {
                return new Token("op_negacion", lexema, sourceManager.getLineNumber());
            }
        }

        private Token e4() throws IOException{
            if(caracterActual == '='){
                actualizarLexema();
                actualizarCaracterActual();
                return new Token("op_igual",lexema, sourceManager.getLineNumber());
            } else {
                return new Token("op_asignacion", lexema, sourceManager.getLineNumber());
            }
        }

        private Token e5() throws IOException, LexicalException {
            if(caracterActual == '&'){
                actualizarLexema();
                actualizarCaracterActual();
                return new Token("op_and",lexema, sourceManager.getLineNumber());
            } else {
                throw new LexicalException(sourceManager.getColumnNumber(),sourceManager.getLineNumber(), lexema, sourceManager);
            }
        }

        private Token e6() throws IOException{
            if(caracterActual == '|'){
                actualizarLexema();
                actualizarCaracterActual();
                return new Token("op_or",lexema, sourceManager.getLineNumber());
            } else {
                return new Token("op_asignacion", lexema, sourceManager.getLineNumber()); //MAL, duda para consultar
            }
        }

        private Token e7() throws IOException{
            return new Token("op_modulo",lexema, sourceManager.getLineNumber());
        }

        private Token e8() throws IOException{
            if(caracterActual == '+'){
                actualizarLexema();
                actualizarCaracterActual();
                return new Token("op_incremento",lexema, sourceManager.getLineNumber());
            } else {
                return new Token("op_suma", lexema, sourceManager.getLineNumber());
            }
        }

        private Token e9() throws IOException{
            if(caracterActual == '-'){
                actualizarLexema();
                actualizarCaracterActual();
                return new Token("op_decremento",lexema, sourceManager.getLineNumber());
            } else {
                return new Token("op_resta", lexema, sourceManager.getLineNumber());
            }
        }

        private Token e10() throws IOException{
            return new Token("op_multiplicacion", lexema, sourceManager.getLineNumber());
        }
        
        /*private Token e11() throws IOException{
            actualizarCaracterActual();
            if(caracterActual == '/'){ //Comentario
                return e11_comentario();
            }
            else {
                if (caracterActual == '*') { //comentarioMultilinea
                    //
                    return e11_comentarioMultiLinea();
                } else {
                    return new Token("op_division", lexema, SourceManager.getLineNumber());
                }
            }
        }*/

        //Puntacion
        private Token e12() throws IOException{
            return new Token("pnt_parentesisIzquierdo", lexema, sourceManager.getLineNumber());
        }

        private Token e13() throws IOException{
            return new Token("pnt_parentesisDerecho", lexema, sourceManager.getLineNumber());
        }

        private Token e14() throws IOException{
            return new Token("pnt_llaveIzquierda", lexema, sourceManager.getLineNumber());
        }

        private Token e15() throws IOException{
            return new Token("pnt_llaveDerecha", lexema, sourceManager.getLineNumber());
        }

        private Token e16() throws IOException{
            return new Token("pnt_puntoYComa", lexema, sourceManager.getLineNumber());
        }

        private Token e17() throws IOException{
            return new Token("pnt_coma", lexema, sourceManager.getLineNumber());
        }

        private Token e18() throws IOException{
            return new Token("pnt_punto", lexema, sourceManager.getLineNumber());
        }

        private Token e19() throws IOException{
            return new Token("pnt_dosPuntos", lexema, sourceManager.getLineNumber());
        }

        private Token e20_digit() throws IOException, LexicalException {
            if (Character.isDigit(caracterActual)) {
                actualizarLexema();
                actualizarCaracterActual();
                return e20_digit();
            } else {
                if(lexema.length() <= 9){
                    return new Token("entero", lexema, sourceManager.getLineNumber());
                } else {
                    throw new LexicalException(sourceManager.getColumnNumber(),sourceManager.getLineNumber(), lexema, sourceManager);
                }
            }
        }

        private Token e21_capitalLetter() throws IOException {
            if (Character.isLetter(caracterActual) || Character.isDigit(caracterActual) || caracterActual == '_') {
                actualizarLexema();
                actualizarCaracterActual();
                return e21_capitalLetter();
            } else {
                return new Token("idClase", lexema, sourceManager.getLineNumber());
            }
        }

        private Token e21_lowerLetter() throws IOException {
            if (Character.isLetter(caracterActual) || Character.isDigit(caracterActual) || caracterActual == '_') {
                actualizarLexema();
                actualizarCaracterActual();
                return e21_lowerLetter();
            } else {
                String esPalabraReservada = ReservedWords.reservedWord(lexema);
                if(esPalabraReservada != null) {
                    return new Token(esPalabraReservada, lexema, sourceManager.getLineNumber());
                } else {
                    return new Token("idClase", lexema, sourceManager.getLineNumber());
                }
            }
        }

        private Token e22_EOF() throws IOException{
            return new Token("EOF", lexema, sourceManager.getLineNumber());
        }

        private void actualizarLexema() {
            lexema += caracterActual;
        }
    }


