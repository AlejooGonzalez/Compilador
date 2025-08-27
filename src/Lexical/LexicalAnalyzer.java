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

        switch (currentCharacter) {

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
                return e1_greaterThan();
            }

            case '<': {
                updateLexeme();
                updateCurrentCharacter();
                return e2_lessThan();
            }

            case '!': {
                updateLexeme();
                updateCurrentCharacter();
                return e3_exclamationMark();
            }

            case '=': {
                updateLexeme();
                updateCurrentCharacter();
                return e4_equals();
            }

            case '&': {
                updateLexeme();
                updateCurrentCharacter();
                return e5_ampersand();
            }

            case '|': {
                updateLexeme();
                updateCurrentCharacter();
                return e6_pipe();
            }

            case '%': {
                updateLexeme();
                updateCurrentCharacter();
                return e7_percentSign();
            }

            case '+': {
                updateLexeme();
                updateCurrentCharacter();
                return e8_plusSign();
            }

            case '-': {
                updateLexeme();
                updateCurrentCharacter();
                return e9_minusSign();
            }

            case '*': {
                updateLexeme();
                updateCurrentCharacter();
                return e10_asterisk();
            }

            case '/': {
                return e11_forwardSlash();
            }

            //punctuation
            case '(': {
                updateLexeme();
                updateCurrentCharacter();
                return e12_leftParenthesis();
            }

            case ')': {
                updateLexeme();
                updateCurrentCharacter();
                return e13_rightParenthesis();
            }

            case '{': {
                updateLexeme();
                updateCurrentCharacter();
                return e14_leftBrace();
            }

            case '}': {
                updateLexeme();
                updateCurrentCharacter();
                return e15_rightBrace();
            }

            case ';': {
                updateLexeme();
                updateCurrentCharacter();
                return e16_semiColon();
            }

            case ',': {
                updateLexeme();
                updateCurrentCharacter();
                return e17_comma();
            }

            case '.': {
                updateLexeme();
                updateCurrentCharacter();
                return e18_period();
            }

            case ':': {
                updateLexeme();
                updateCurrentCharacter();
                return e19_colon();
            }

            case '"': {
                updateLexeme();
                updateCurrentCharacter();
                return e22_doubleQuote();
            }
        /*
            case '\\': {
                updateLexeme();
                updateCurrentCharacter();
                return e25();
            }*/
        }

            if (sourceManager.isEOF(currentCharacter)) {
                return e23_EOF();
            } else {
                updateLexeme();
                updateCurrentCharacter();
                throw new LexicalException(sourceManager.getColumnNumber(), sourceManager.getLineNumber(), lexeme, sourceManager);
            }
        }

        /*
        private Token e25() throws IOException, LexicalException {
            if(currentCharacter == 'u' || currentCharacter == 'U'){
                updateLexeme();
                updateCurrentCharacter();
                return e25_unicode();
            } else {
                throw new LexicalException(sourceManager.getColumnNumber(), sourceManager.getLineNumber(), lexeme, sourceManager);
            }
        }
        */

        /*
        private Token e25_unicode() throws IOException, LexicalException {
            for(int i = 0; i <= 4; i++) {
                if(Character.isDigit(currentCharacter) || currentCharacter=='A' || currentCharacter == 'B' || currentCharacter == 'C' ||currentCharacter == 'D' ||currentCharacter == 'E' || currentCharacter == 'F' || currentCharacter == 'a' || currentCharacter == 'b' || currentCharacter == 'c' || currentCharacter == 'd' || currentCharacter == 'e' || currentCharacter == 'f'){
                    updateLexeme();
                    updateCurrentCharacter();
                } else {
                    updateLexeme();
                    throw new LexicalException(sourceManager.getColumnNumber(), sourceManager.getLineNumber(), lexeme, sourceManager);
                }
            }
            return null;
        } */

        private Token e22_doubleQuote() throws IOException, LexicalException {
            if (currentCharacter == '\n') {
                throw new LexicalException(sourceManager.getColumnNumber(), sourceManager.getLineNumber(), lexeme, sourceManager);
            } else {
                if (Character.isLetterOrDigit(currentCharacter)) {
                    updateLexeme();
                    updateCurrentCharacter();
                    return e22_doubleQuote();
                } else {
                    if (currentCharacter == '"') {
                        updateLexeme();
                        updateCurrentCharacter();
                        return new Token("op_StringLiteral", lexeme, sourceManager.getLineNumber());
                    } /* else {
                        if (currentCharacter == '\\') {
                            updateLexeme();
                            updateCurrentCharacter();
                            return e25_unicode();
                        } else {
                            throw new LexicalException(sourceManager.getColumnNumber(), sourceManager.getLineNumber(), lexeme, sourceManager);
                        }*/
                    }
                }
            return null;
            }


        private Token e1_greaterThan() throws IOException{
            if(currentCharacter == '='){
                updateLexeme();
                updateCurrentCharacter();
                return new Token("op_mayorIgual", lexeme, sourceManager.getLineNumber());
            } else {
                return new Token("op_mayor", lexeme, sourceManager.getLineNumber());
            }
        }

        private Token e2_lessThan() throws IOException{
            if(currentCharacter == '='){
                updateLexeme();
                updateCurrentCharacter();
                return new Token("op_menorIgual", lexeme, sourceManager.getLineNumber());
            } else {
                return new Token("op_menor", lexeme, sourceManager.getLineNumber());
            }
        }

        private Token e3_exclamationMark() throws IOException{
            if(currentCharacter == '='){
                updateLexeme();
                updateCurrentCharacter();
                return new Token("op_distinto", lexeme, sourceManager.getLineNumber());
            } else {
                return new Token("op_negacion", lexeme, sourceManager.getLineNumber());
            }
        }

        private Token e4_equals() throws IOException{
            if(currentCharacter == '='){
                updateLexeme();
                updateCurrentCharacter();
                return new Token("op_igual", lexeme, sourceManager.getLineNumber());
            } else {
                return new Token("op_asignacion", lexeme, sourceManager.getLineNumber());
            }
        }

        private Token e5_ampersand() throws IOException, LexicalException {
            if(currentCharacter == '&'){
                updateLexeme();
                updateCurrentCharacter();
                return new Token("op_and", lexeme, sourceManager.getLineNumber());
            } else {
                throw new LexicalException(sourceManager.getColumnNumber(),sourceManager.getLineNumber(), lexeme, sourceManager);
            }
        }

        private Token e6_pipe() throws IOException{
            if(currentCharacter == '|'){
                updateLexeme();
                updateCurrentCharacter();
                return new Token("op_or", lexeme, sourceManager.getLineNumber());
            } else {
                return new Token("op_asignacion", lexeme, sourceManager.getLineNumber()); //MAL, duda para consultar
            }
        }

        private Token e7_percentSign() {
            return new Token("op_modulo", lexeme, sourceManager.getLineNumber());
        }

        private Token e8_plusSign() throws IOException{
            if(currentCharacter == '+'){
                updateLexeme();
                updateCurrentCharacter();
                return new Token("op_incremento", lexeme, sourceManager.getLineNumber());
            } else {
                return new Token("op_suma", lexeme, sourceManager.getLineNumber());
            }
        }

        private Token e9_minusSign() throws IOException{
            if(currentCharacter == '-'){
                updateLexeme();
                updateCurrentCharacter();
                return new Token("op_decremento", lexeme, sourceManager.getLineNumber());
            } else {
                return new Token("op_resta", lexeme, sourceManager.getLineNumber());
            }
        }

        private Token e10_asterisk(){
            return new Token("op_multiplicacion", lexeme, sourceManager.getLineNumber());
        }
        
        private Token e11_forwardSlash() throws IOException, LexicalException {
            updateCurrentCharacter();
            if (currentCharacter == '/') { //Comentario
                return e11_comment();
            } else {
                if (currentCharacter == '*') { //comentarioMultilinea
                    return e11_multilineComment();
                } else {
                    return new Token("op_division", lexeme, sourceManager.getLineNumber());
                }
            }
        }

        private Token e11_multilineComment() throws IOException, LexicalException {
            updateCurrentCharacter();
            if(currentCharacter != '*'){
                return e11_multilineComment();
            } else{
                return e11_multilineCommentEnd();
            }
        }

        private Token e11_multilineCommentEnd() throws IOException, LexicalException {
            updateCurrentCharacter();
            if(currentCharacter != '/'){
                return e11_multilineComment();
            } else{
                updateCurrentCharacter();
                return e0();
            }
        }

        private Token e11_comment() throws IOException, LexicalException {
            if(currentCharacter != '\n'){
                updateCurrentCharacter();
                return e11_comment();
            } else{
                return e0();
            }
        }

        //Puntacion
        private Token e12_leftParenthesis(){
            return new Token("pnt_parentesisIzquierdo", lexeme, sourceManager.getLineNumber());
        }

        private Token e13_rightParenthesis(){
            return new Token("pnt_parentesisDerecho", lexeme, sourceManager.getLineNumber());
        }

        private Token e14_leftBrace(){
            return new Token("pnt_llaveIzquierda", lexeme, sourceManager.getLineNumber());
        }

        private Token e15_rightBrace(){
            return new Token("pnt_llaveDerecha", lexeme, sourceManager.getLineNumber());
        }

        private Token e16_semiColon(){
            return new Token("pnt_puntoYComa", lexeme, sourceManager.getLineNumber());
        }

        private Token e17_comma(){
            return new Token("pnt_coma", lexeme, sourceManager.getLineNumber());
        }

        private Token e18_period(){
            return new Token("pnt_punto", lexeme, sourceManager.getLineNumber());
        }

        private Token e19_colon(){
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

        private Token e23_EOF(){
            return new Token("EOF", lexeme, sourceManager.getLineNumber());
        }
    }


