package Semantic;

import Exceptions.SemanticException;
import Lexical.Token;

import java.util.HashMap;

    public class ConcreteClass {
        private Token token;
        private Constructor constructor;
        private Token inheritance;
        private HashMap<String, Method> methods;
        private HashMap<String, Attribute> attributes;

        public ConcreteClass(Token token) {
            this.token = token;
            this.methods = new HashMap<>();
            this.attributes = new HashMap<>();
        }

        public Token getToken() {
            return token;
        }

        public void addAttributes(Attribute attribute) throws SemanticException {
            if (!attributes.containsKey(attribute.getName())) {
                attributes.put(attribute.getName(), attribute);
            } else {
                throw new SemanticException("Method already exists", attribute.getToken());
            }
        }

        public void addMethod(Method m) throws SemanticException {
            if (!methods.containsKey(m.getName())) {
                methods.put(m.getName(), m);
            } else {
                throw new SemanticException("Method already exists", m.getToken());
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

        public void addConstructor(Constructor cons) throws SemanticException {
            if (this.constructor == null) {
                this.constructor = cons;
            } else {
                throw new SemanticException("Error", constructor.getToken());
            }
        }
    }
