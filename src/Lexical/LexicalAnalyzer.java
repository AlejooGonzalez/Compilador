package Lexical;

import java.util.Objects;
import java.io.IOException;
import Exceptions.LexicalException;
import SourceManager.SourceManagerImplementation;

public class LexicalAnalyzer {
    private String lexeme;
    private char currentCharacter;
    private final SourceManagerImplementation sourceManager;

    public LexicalAnalyzer(SourceManagerImplementation sourceManager) throws IOException {
        this.sourceManager = sourceManager;
        updateCurrentCharacter();
    }

    public Token nextToken() throws IOException, LexicalException {
        lexeme = "";
        return e0();
    }

    private void updateCurrentCharacter() throws IOException {
        currentCharacter = sourceManager.getNextChar();
    }

    private void updateLexeme() {
        lexeme += currentCharacter;
    }

    private Token e0() throws IOException, LexicalException {

        if (Character.isDigit(currentCharacter)) {
            updateLexeme();
            updateCurrentCharacter();
            return e20_digit();
        } else {
            if (Character.isUpperCase(currentCharacter)) {
                updateLexeme();
                updateCurrentCharacter();
                return e21_capitalLetter();
            } else {
                if (Character.isLowerCase(currentCharacter)) {
                    updateLexeme();
                    updateCurrentCharacter();
                    return e21_lowerLetter();
                }
            }
        }

        switch(currentCharacter) {

            //Spaces
            case ' ':
            case '\n':
            case '\r':
            case '\t': {
                updateCurrentCharacter();
                return e0();
            }

            //Operators
            case '>': {
                updateLexeme();
                updateCurrentCharacter();
                return e1();
            }

            case '<': {
                updateLexeme();
                updateCurrentCharacter();
                return e2();
            }

            case '!': {
                updateLexeme();
                updateCurrentCharacter();
                return e3();
            }

            case '=': {
                updateLexeme();
                updateCurrentCharacter();
                return e4();
            }

            case '&': {
                updateLexeme();
                updateCurrentCharacter();
                return e5();
            }

            case '|': {
                updateLexeme();
                updateCurrentCharacter();
                return e6();
            }

            case '%': {
                updateLexeme();
                updateCurrentCharacter();
                return e7();
            }

            case '+': {
                updateLexeme();
                updateCurrentCharacter();
                return e8();
            }

            case '-': {
                updateLexeme();
                updateCurrentCharacter();
                return e9();
            }

            case '*': {
                updateLexeme();
                updateCurrentCharacter();
                return e10();
            }

            case '/':{
                return e11();
                }

            //punctuation
            case '(': {
                updateLexeme();
                updateCurrentCharacter();
                return e12();
            }

            case ')': {
                updateLexeme();
                updateCurrentCharacter();
                return e13();
            }

            case '{': {
                updateLexeme();
                updateCurrentCharacter();
                return e14();
            }

            case '}': {
                updateLexeme();
                updateCurrentCharacter();
                return e15();
            }

            case ';': {
                updateLexeme();
                updateCurrentCharacter();
                return e16();
            }

            case ',': {
                updateLexeme();
                updateCurrentCharacter();
                return e17();
            }

            case '.': {
                updateLexeme();
                updateCurrentCharacter();
                return e18();
            }

            case ':': {
                updateLexeme();
                updateCurrentCharacter();
                return e19();
            }

            case '"':{
                updateLexeme();
                updateCurrentCharacter();
                return e99();
            }
        }

            if(sourceManager.isEOF(currentCharacter)){
                return e22_EOF();
            } else {
                updateLexeme();
                updateCurrentCharacter();
                throw new LexicalException(sourceManager.getColumnNumber(), sourceManager.getLineNumber(), lexeme, sourceManager);
            }
        }

        private Token e99() throws IOException, LexicalException {
            if (currentCharacter == '\n') {
                throw new LexicalException(sourceManager.getColumnNumber(), sourceManager.getLineNumber(), lexeme, sourceManager);
            } else {
                if (Character.isLetterOrDigit(currentCharacter)) {
                    updateLexeme();
                    updateCurrentCharacter();
                    return e99();
                } else {
                    if(currentCharacter == '"') {
                        updateLexeme();
                        updateCurrentCharacter();
                        return new Token("op_StringLiteral", lexeme, sourceManager.getLineNumber());
                    }
                    else {
                        throw new LexicalException(sourceManager.getColumnNumber(), sourceManager.getLineNumber(), lexeme, sourceManager);
                    }
                }
            }
        }

        private Token e1() throws IOException{
            if(currentCharacter == '='){
                updateLexeme();
                updateCurrentCharacter();
                return new Token("op_mayorIgual", lexeme, sourceManager.getLineNumber());
            } else {
                return new Token("op_mayor", lexeme, sourceManager.getLineNumber());
            }
        }

        private Token e2() throws IOException{
            if(currentCharacter == '='){
                updateLexeme();
                updateCurrentCharacter();
                return new Token("op_menorIgual", lexeme, sourceManager.getLineNumber());
            } else {
                return new Token("op_menor", lexeme, sourceManager.getLineNumber());
            }
        }

        private Token e3() throws IOException{
            if(currentCharacter == '='){
                updateLexeme();
                updateCurrentCharacter();
                return new Token("op_distinto", lexeme, sourceManager.getLineNumber());
            } else {
                return new Token("op_negacion", lexeme, sourceManager.getLineNumber());
            }
        }

        private Token e4() throws IOException{
            if(currentCharacter == '='){
                updateLexeme();
                updateCurrentCharacter();
                return new Token("op_igual", lexeme, sourceManager.getLineNumber());
            } else {
                return new Token("op_asignacion", lexeme, sourceManager.getLineNumber());
            }
        }

        private Token e5() throws IOException, LexicalException {
            if(currentCharacter == '&'){
                updateLexeme();
                updateCurrentCharacter();
                return new Token("op_and", lexeme, sourceManager.getLineNumber());
            } else {
                throw new LexicalException(sourceManager.getColumnNumber(),sourceManager.getLineNumber(), lexeme, sourceManager);
            }
        }

        private Token e6() throws IOException{
            if(currentCharacter == '|'){
                updateLexeme();
                updateCurrentCharacter();
                return new Token("op_or", lexeme, sourceManager.getLineNumber());
            } else {
                return new Token("op_asignacion", lexeme, sourceManager.getLineNumber()); //MAL, duda para consultar
            }
        }

        private Token e7() {
            return new Token("op_modulo", lexeme, sourceManager.getLineNumber());
        }

        private Token e8() throws IOException{
            if(currentCharacter == '+'){
                updateLexeme();
                updateCurrentCharacter();
                return new Token("op_incremento", lexeme, sourceManager.getLineNumber());
            } else {
                return new Token("op_suma", lexeme, sourceManager.getLineNumber());
            }
        }

        private Token e9() throws IOException{
            if(currentCharacter == '-'){
                updateLexeme();
                updateCurrentCharacter();
                return new Token("op_decremento", lexeme, sourceManager.getLineNumber());
            } else {
                return new Token("op_resta", lexeme, sourceManager.getLineNumber());
            }
        }

        private Token e10(){
            return new Token("op_multiplicacion", lexeme, sourceManager.getLineNumber());
        }
        
        private Token e11() throws IOException, LexicalException {
            updateCurrentCharacter();
            if(currentCharacter == '/'){ //Comentario
                return e11_comentario();
            }
            else {
                /*if (caracterActual == '*') { //comentarioMultilinea
                    //
                    return e11_comentarioMultiLinea();
                } else {*/
                    return new Token("op_division", lexeme, sourceManager.getLineNumber());
                }
            }


        private Token e11_comentario() throws IOException, LexicalException {
            if(currentCharacter != '\n'){
                updateCurrentCharacter();
                return e11_comentario();
            } else{
                return e0();
            }
        }

        //Puntacion
        private Token e12(){
            return new Token("pnt_parentesisIzquierdo", lexeme, sourceManager.getLineNumber());
        }

        private Token e13(){
            return new Token("pnt_parentesisDerecho", lexeme, sourceManager.getLineNumber());
        }

        private Token e14(){
            return new Token("pnt_llaveIzquierda", lexeme, sourceManager.getLineNumber());
        }

        private Token e15(){
            return new Token("pnt_llaveDerecha", lexeme, sourceManager.getLineNumber());
        }

        private Token e16(){
            return new Token("pnt_puntoYComa", lexeme, sourceManager.getLineNumber());
        }

        private Token e17(){
            return new Token("pnt_coma", lexeme, sourceManager.getLineNumber());
        }

        private Token e18(){
            return new Token("pnt_punto", lexeme, sourceManager.getLineNumber());
        }

        private Token e19(){
            return new Token("pnt_dosPuntos", lexeme, sourceManager.getLineNumber());
        }

        private Token e20_digit() throws IOException, LexicalException {
            if (Character.isDigit(currentCharacter)) {
                updateLexeme();
                updateCurrentCharacter();
                return e20_digit();
            } else {
                if(lexeme.length() <= 9){
                    return new Token("entero", lexeme, sourceManager.getLineNumber());
                } else {
                    throw new LexicalException(sourceManager.getColumnNumber(),sourceManager.getLineNumber(), lexeme, sourceManager);
                }
            }
        }

        private Token e21_capitalLetter() throws IOException {
            if (Character.isLetter(currentCharacter) || Character.isDigit(currentCharacter) || currentCharacter == '_') {
                updateLexeme();
                updateCurrentCharacter();
                return e21_capitalLetter();
            } else {
                return new Token("idClase", lexeme, sourceManager.getLineNumber());
            }
        }

        private Token e21_lowerLetter() throws IOException {
            if (Character.isLetter(currentCharacter) || Character.isDigit(currentCharacter) || currentCharacter == '_') {
                updateLexeme();
                updateCurrentCharacter();
                return e21_lowerLetter();
            } else {
                String isReservedWord = ReservedWords.reservedWord(lexeme);
                if(Objects.nonNull(isReservedWord)) {
                    return new Token(isReservedWord, lexeme, sourceManager.getLineNumber());
                } else {
                    return new Token("idMetVar", lexeme, sourceManager.getLineNumber());
                }
            }
        }

        private Token e22_EOF(){
            return new Token("EOF", lexeme, sourceManager.getLineNumber());
        }
    }


