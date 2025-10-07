package Semantic;

import Exceptions.SemanticException;
import Lexical.Token;
import Main.MainSyntactic;

import java.util.HashMap;
import java.util.Objects;

public class ConcreteClass {
        private Token token;
        private Constructor constructor;
        private Token inheritance;
        private Token modifier;
        private HashMap<String, Method> methods;
        private HashMap<String, Attribute> attributes;

        public ConcreteClass(Token token, Token modifier) {
            this.token = token;
            this.modifier = modifier;
            this.methods = new HashMap<>();
            this.attributes = new HashMap<>();
        }

        public Token getToken() {
            return token;
        }

        public Token getModifier() {
            return modifier;
        }

        public void addAttributes(Attribute attribute) throws SemanticException {
            if (!attributes.containsKey(attribute.getName())) {
                attributes.put(attribute.getName(), attribute);
            } else {
                throw new SemanticException("El atributo"+attribute.getToken()+"ya existe", attribute.getToken(), attribute.getToken().getLineNumber());
            }
        }

        public void addConstructor(Constructor cons) throws SemanticException {
            if (this.constructor == null) {
                this.constructor = cons;
            } else {
                throw new SemanticException("La clase tiene mas de un constructor", cons.getToken(), cons.getToken().getLineNumber());
            }
        }

        public void addMethod(Method m) throws SemanticException {
            if (!methods.containsKey(m.getName())) {
                methods.put(m.getName(), m);
            } else {
                throw new SemanticException("El metodo"+m.getToken()+"ya existe", m.getToken(), m.getToken().getLineNumber());
            }
        }

        public void setInheritance(Token inheritance) {
            this.inheritance = inheritance;
        }

        public Token getInheritance() {
            return inheritance;
        }

        public String getName() {
            return token.getLexeme();
        }

        public void itIsWellStated() throws SemanticException {
            if(inheritance!=null) {
                if (!Objects.equals(inheritance.getLexeme(), "Object")) {
                    ConcreteClass fatherClass = MainSyntactic.ST.existsClass(inheritance);
                    if (fatherClass == null) {
                        throw new SemanticException("La clase " + inheritance.getLexeme() + " no está declarada", inheritance, inheritance.getLineNumber());
                    } else {
                        if (fatherClass == this) {
                            throw new SemanticException("Una clase no puede extenderse a sí misma", inheritance, inheritance.getLineNumber());
                        }
                    }
                    if (fatherClass.getModifier() != null) {
                        String modType = fatherClass.getModifier().getTokenType();
                        if (modType.equals("pr_static")) {
                            throw new SemanticException("No se puede heredar de una clase estática", inheritance, inheritance.getLineNumber());
                        }
                        if (modType.equals("pr_final")) {
                            throw new SemanticException("No se puede heredar de una clase final", inheritance, inheritance.getLineNumber());
                        }
                        checkCircularInheritance();
                    }
                }
            }

            for(Attribute a : attributes.values()){
                a.itIsWellStated();
            }
            for(Method m : methods.values()) {
                m.itIsWellStated();
            }

            if(modifier != null) {
                if(modifier.getTokenType().equals("pr_abstract") && constructor != null){
                  throw new SemanticException("Una clase abstracta no puede tener constructor", constructor.getToken(), constructor.getToken().getLineNumber());
                }
            }

            if(constructor!=null){
                constructor.itIsWellStated();
            }
        }

        private void checkCircularInheritance() throws SemanticException {
            ConcreteClass ancestor = this;
            while (ancestor.getInheritance() != null) {
                ConcreteClass father = MainSyntactic.ST.existsClass(ancestor.getInheritance());
                if (father == null)
                    break;
                if (father == this) {
                    throw new SemanticException("Herencia circular detectada en clase " + this.getName(), this.getToken(), this.getToken().getLineNumber());
                }
                ancestor = father;
            }
        }
    }
