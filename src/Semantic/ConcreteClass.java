package Semantic;

import Exceptions.SemanticException;
import Lexical.Token;
import Main.MainSemantic;

import java.util.HashMap;
import java.util.Objects;

public class ConcreteClass {
        private Token token;
        private Constructor constructor;
        private Token inheritance;
        private Token modifier;
        private HashMap<String, Method> methods;
        private HashMap<String, Attribute> attributes;
        private boolean consolidated = false;
        private boolean checkingCircular = false;

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
                throw new SemanticException("El atributo "+attribute.getToken()+" ya existe", attribute.getToken(), attribute.getToken().getLineNumber());
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
                    ConcreteClass fatherClass = MainSemantic.ST.itIsAnExistingClass(inheritance);
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
                                throw new SemanticException("Una clase abstracta, no puede extender a una clase final/estatica", token, token.getLineNumber());
                            }
                        }
                    } else {
                        if (this.getModifier() != null) {
                            String classType = this.getModifier().getTokenType();
                            if (classType.equals("pr_abstract")) {
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
            if(!consolidated) {
                if (inheritance == null) {
                    this.inheritance = MainSemantic.ST.getClasses().get("Object").getToken();
                } else {
                    ConcreteClass father = MainSemantic.ST.itIsAnExistingClass(inheritance);
                    father.consolidate();
                    consolidateAttributes(father);
                    consolidateMethod(father);
                }
                if (constructor == null) {
                    this.setConstructor(new Constructor(token));
                }
                consolidated = true;
            }
    }

    private HashMap<String, Method> getMethods() {
            return methods;
    }

    private HashMap<String,Attribute> getAttributes() {
            return attributes;
    }

    private void checkCircularInheritance() throws SemanticException {
        if (checkingCircular) {
            throw new SemanticException("Herencia circular detectada en clase " + this.getName(), inheritance, inheritance.getLineNumber());
        }
        checkingCircular = true;
        if (inheritance != null) {
            ConcreteClass father = MainSemantic.ST.itIsAnExistingClass(inheritance);
            if (father != null) {
                father.checkCircularInheritance();
            }
        }

        checkingCircular = false;
    }

    public int getAttributeLine(String attributeName) throws SemanticException {
            Attribute a = attributes.get(attributeName);
            if (a == null) {
                throw new SemanticException("El atributo '" + attributeName + "' no existe en la clase " + getName(), token, token.getLineNumber()); }
            return a.getToken().getLineNumber();
        }

    private void consolidateMethod(ConcreteClass father) throws SemanticException {
        for (Method fatherMethod : father.getMethods().values()) {
            Method thisMethod = methods.get(fatherMethod.getName());

            if (thisMethod != null) {
                Token fatherModifier = null;
                String fatherModifierType = "";

                if (fatherMethod.getModifier() != null) {
                    fatherModifier = fatherMethod.getModifier();
                    fatherModifierType = fatherModifier.getTokenType();
                }
                if (fatherModifierType.equals("pr_final")) {
                    throw new SemanticException("El método final '" + fatherMethod.getName() + "' no puede ser redefinido en " + this.getName(), thisMethod.getToken(), thisMethod.getToken().getLineNumber());
                }
                if (fatherModifierType.equals("pr_static")) {
                    throw new SemanticException("El método static '" + fatherMethod.getName() + "' no puede ser redefinido en " + this.getName(), thisMethod.getToken(), thisMethod.getToken().getLineNumber());
                }
                boolean fatherIsAbstract = fatherModifierType.equals("pr_abstract");
                boolean thisClassIsConcrete = (modifier == null) || !modifier.getTokenType().equals("pr_abstract");

                if (fatherIsAbstract && thisClassIsConcrete && thisMethod.sameParameters(fatherMethod) && !thisMethod.getHasBlock()) {
                    throw new SemanticException("El método abstract '" + fatherMethod.getName() + "' fue redefinido y no tiene cuerpo", thisMethod.getToken(), thisMethod.getToken().getLineNumber());
                }
                if (fatherMethod.getReturnType() != null && thisMethod.getReturnType() != null) {
                    boolean differentReturnType = !thisMethod.getReturnType().getToken().getLexeme().equals(fatherMethod.getReturnType().getToken().getLexeme());
                    boolean differentParams = !thisMethod.sameParameters(fatherMethod);

                    if (differentReturnType) {
                        throw new SemanticException("El método '" + thisMethod.getName() + "' redefine con tipo de retorno distinto al heredado", thisMethod.getToken(), thisMethod.getToken().getLineNumber());
                    }
                    if (differentParams) {
                        throw new SemanticException("El método '" + thisMethod.getName() + "' redefine con distinta lista de parámetros", thisMethod.getToken(), thisMethod.getToken().getLineNumber());
                    }
                }
            }
            else {
                boolean fatherIsAbstract = fatherMethod.getModifier() != null && fatherMethod.getModifier().getTokenType().equals("pr_abstract");
                boolean thisClassIsConcrete = (modifier == null) || !modifier.getTokenType().equals("pr_abstract");
                if (fatherIsAbstract && thisClassIsConcrete) {
                    throw new SemanticException("No definiste el método abstracto '" + fatherMethod.getName() + "' en la clase " + this.getName(), fatherMethod.getToken(), fatherMethod.getToken().getLineNumber());
                }
                methods.put(fatherMethod.getName(), fatherMethod);
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

        public void setConstructor(Constructor token){
            constructor = token;
        }

    public void sentenceCheck() throws SemanticException {
        for(Method m : methods.values()){
            m.sentenceCheck();
        }
    }
}


