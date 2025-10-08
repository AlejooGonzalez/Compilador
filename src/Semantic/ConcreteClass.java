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
                throw new SemanticException("El metodo " + m.getToken() + " ya existe", m.getToken(), m.getToken().getLineNumber());
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
                        String fatherType = fatherClass.getModifier().getTokenType();
                        if (fatherType.equals("pr_static")) {
                            throw new SemanticException("No se puede heredar de una clase estática", token, token.getLineNumber());
                        }
                        if (fatherType.equals("pr_final")) {
                            throw new SemanticException("No se puede heredar de una clase final", token, token.getLineNumber());
                        }
                        if(this.getModifier() != null) {
                            String classType = this.getModifier().getTokenType();
                            if ((classType.equals("pr_final") || classType.equals("pr_static")) && fatherType.equals("pr_abstract")) {
                                throw new SemanticException("Una clase abstracta, no puede extender a una clase concreta", token, token.getLineNumber());
                            }
                        }
                    }
                    checkCircularInheritance();
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

    public void consolidate() throws SemanticException {
        if (inheritance != null) {
            ConcreteClass father = MainSyntactic.ST.existsClass(inheritance);
            consolidateAttributes(father);
            consolidateMethod(father);
        } else {
            if (!Objects.equals(this.getName(), "Object")) {
                this.inheritance = MainSyntactic.ST.getClasses().get("Object").getToken();
            }
        }
    }

    private HashMap<String, Method> getMethods() {
            return methods;
    }

    private HashMap<String,Attribute> getAttributes() {
            return attributes;
    }

    private void checkCircularInheritance() throws SemanticException {
            ConcreteClass ancestor = this;
            while (ancestor.getInheritance() != null) {
                ConcreteClass father = MainSyntactic.ST.existsClass(ancestor.getInheritance());
                if (father == null)
                    break;
                if (father == this) {
                    throw new SemanticException("Herencia circular detectada en clase " + this.getName(), inheritance, inheritance.getLineNumber());
                }
                ancestor = father;
            }
        }

    public int getAttributeLine(String attributeName) throws SemanticException {
            Attribute a = attributes.get(attributeName);
            if (a == null) {
                throw new SemanticException("El atributo '" + attributeName + "' no existe en la clase " + getName(), token, token.getLineNumber()); }
            return a.getToken().getLineNumber();
        }

        private void consolidateMethod(ConcreteClass father) throws SemanticException {
            for (Method mFather : father.getMethods().values()) {
                Method mThis = methods.get(mFather.getName());
                if (mThis != null) {
                    if (mFather.getModifier() != null) {
                        if (mFather.getModifier().getTokenType().equals("pr_final")) {
                            throw new SemanticException("El método final '" + mFather.getName() + "' no puede ser redefinido en " + this.getName(), mThis.getToken(), mThis.getToken().getLineNumber());
                        }
                        if (mFather.getModifier().getTokenType().equals("pr_static")) {
                            throw new SemanticException("El método static '" + mFather.getName() + "' no puede ser redefinido en " + this.getName(), mThis.getToken(), mThis.getToken().getLineNumber());
                        }
                        if (mFather.getModifier().getTokenType().equals("pr_abstract") && mThis.getName().equals(mFather.getName()) && mThis.sameParameters(mFather) && !mThis.getHasBlock()) {
                            throw new SemanticException("El método abstract '" + mFather.getName() + "' fue redefinidio y no tiene cuerpo ", mThis.getToken(), mThis.getToken().getLineNumber());
                        }
                    }
                    if (mFather.getReturnType() != null && mThis.getReturnType() != null) {
                        if (!mThis.getReturnType().getToken().getLexeme().equals(mFather.getReturnType().getToken().getLexeme())) {
                            throw new SemanticException("El método '" + mThis.getName() + "' sobreecarga con tipo de retorno distinto al heredado", mThis.getToken(), mThis.getToken().getLineNumber());
                        } else {
                            if (!mThis.sameParameters(mFather)) {
                                throw new SemanticException("El método '" + mThis.getName() + "' es redefinido con distinto tipo de parametros", mThis.getToken(), mThis.getToken().getLineNumber());
                            }
                        }
                    }
                } else {
                    if (mFather.getModifier() != null) {
                        if (mFather.getModifier().getTokenType().equals("pr_abstract")) {
                            throw new SemanticException("No definiste el metodo " + mFather.getName(), mFather.getToken(), mFather.getToken().getLineNumber());
                        } else {
                            methods.put(mFather.getName(), mFather);
                        }
                    }
                }
            }
        }

        private void consolidateAttributes(ConcreteClass father) throws SemanticException {
            for (Attribute a : father.getAttributes().values()) {
                if (attributes.containsKey(a.getName())) {
                    throw new SemanticException("El atributo '" + a.getName() + "' tiene el mismo nombre que un atributo de una superclase " + this.getName(), a.getToken(), this.getAttributeLine(a.getName()));
                }
                attributes.put(a.getName(), a);
            }
        }


    }


