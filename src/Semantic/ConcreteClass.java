package Semantic;

import Exceptions.SemanticException;
import Lexical.Token;
import Main.MainSemantic;

import java.util.*;

public class ConcreteClass {
        private Token token;
        private Constructor constructor;
        private Token inheritance;
        private Token modifier;
        private LinkedHashMap<String, Method> methods;
        private LinkedHashMap<String, Attribute> attributes;
        private boolean consolidated = false;
        private boolean checkingCircular = false;
        private int lastMethodOffset;
        private int lastAttributeOffset;
        private boolean ifOffseted = false;
        private boolean methodsOffseted = false;
        private boolean attributesOffseted = false;


    public ConcreteClass(Token token, Token modifier) {
            this.token = token;
            this.modifier = modifier;
            this.methods = new LinkedHashMap<>();
            this.attributes = new LinkedHashMap<>();
    }

        public Token getToken() {
            return token;
        }

        public Token getModifier() {
            return modifier;
        }

        public void addAttributes(Attribute attribute) throws SemanticException {
            if (!attributes.containsKey(attribute.getLexeme())) {
                attributes.put(attribute.getLexeme(), attribute);
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
            if (!methods.containsKey(m.getLexeme())) {
                methods.put(m.getLexeme(), m);
            } else {
                throw new SemanticException("El metodo con ese nombre ya existe", m.getToken(), m.getToken().getLineNumber());
            }
        }

        public Constructor getConstructor() {
            return constructor;
        }
        public void setInheritance(Token inheritance) {
            this.inheritance = inheritance;
        }

        public Token getInheritance() {
            return inheritance;
        }

        public String getLexeme() {
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
                constructor.setClassName(token);
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

    public HashMap<String, Method> getMethods() {
            return methods;
    }

    public HashMap<String,Attribute> getAttributes() {
            return attributes;
    }

    private void checkCircularInheritance() throws SemanticException {
        if (checkingCircular) {
            throw new SemanticException("Herencia circular detectada en clase " + this.getLexeme(), inheritance, inheritance.getLineNumber());
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
                throw new SemanticException("El atributo '" + attributeName + "' no existe en la clase " + getLexeme(), token, token.getLineNumber()); }
            return a.getToken().getLineNumber();
        }

    public void setLastMethodOffset(int lastMethodOffset) {
        this.lastMethodOffset = lastMethodOffset;
    }

    private void consolidateMethod(ConcreteClass father) throws SemanticException {
        for (Method fatherMethod : father.getMethods().values()) {
            Method thisMethod = methods.get(fatherMethod.getLexeme());

            if (thisMethod != null) {
                Token fatherModifier;
                String fatherModifierType = "";

                if (fatherMethod.getModifier() != null) {
                    fatherModifier = fatherMethod.getModifier();
                    fatherModifierType = fatherModifier.getTokenType();
                }
                if (fatherModifierType.equals("pr_final")) {
                    throw new SemanticException("El método final '" + fatherMethod.getLexeme() + "' no puede ser redefinido en " + this.getLexeme(), thisMethod.getToken(), thisMethod.getToken().getLineNumber());
                }
                if (fatherModifierType.equals("pr_static")) {
                    throw new SemanticException("El método static '" + fatherMethod.getLexeme() + "' no puede ser redefinido en " + this.getLexeme(), thisMethod.getToken(), thisMethod.getToken().getLineNumber());
                }
                boolean fatherIsAbstract = fatherModifierType.equals("pr_abstract");
                boolean thisClassIsConcrete = (modifier == null) || !modifier.getTokenType().equals("pr_abstract");

                if (fatherIsAbstract && thisClassIsConcrete && thisMethod.sameParameters(fatherMethod) && !thisMethod.getHasBlock()) {
                    throw new SemanticException("El método abstract '" + fatherMethod.getLexeme() + "' fue redefinido y no tiene cuerpo", thisMethod.getToken(), thisMethod.getToken().getLineNumber());
                }
                if (fatherMethod.getReturnType() != null && thisMethod.getReturnType() != null) {
                    boolean differentReturnType = !thisMethod.getReturnType().getToken().getLexeme().equals(fatherMethod.getReturnType().getToken().getLexeme());
                    boolean differentParams = !thisMethod.sameParameters(fatherMethod);

                    if (differentReturnType) {
                        throw new SemanticException("El método '" + thisMethod.getLexeme() + "' redefine con tipo de retorno distinto al heredado", thisMethod.getToken(), thisMethod.getToken().getLineNumber());
                    }
                    if (differentParams) {
                        throw new SemanticException("El método '" + thisMethod.getLexeme() + "' redefine con distinta lista de parámetros", thisMethod.getToken(), thisMethod.getToken().getLineNumber());
                    }
                }
            }
            else {
                boolean fatherIsAbstract = fatherMethod.getModifier() != null && fatherMethod.getModifier().getTokenType().equals("pr_abstract");
                boolean thisClassIsConcrete = (modifier == null) || !modifier.getTokenType().equals("pr_abstract");
                if (fatherIsAbstract && thisClassIsConcrete) {
                    throw new SemanticException("No definiste el método abstracto '" + fatherMethod.getLexeme() + "' en la clase " + this.getLexeme(), fatherMethod.getToken(), fatherMethod.getToken().getLineNumber());
                }
                methods.put(fatherMethod.getLexeme(), fatherMethod);
            }
        }
    }

        private void consolidateAttributes(ConcreteClass father) throws SemanticException {
            for (Attribute a : father.getAttributes().values()) {
                if (attributes.containsKey(a.getLexeme())) {
                    throw new SemanticException("El atributo '" + a.getLexeme() + "' tiene el mismo nombre que un atributo de una superclase " + this.getLexeme(), a.getToken(), this.getAttributeLine(a.getLexeme()));
                }
                attributes.put(a.getLexeme(), a);
            }
        }

        public void setConstructor(Constructor token){
            constructor = token;
        }

    public void sentenceCheck() throws SemanticException {
        MainSemantic.ST.setCurrentClass(this);
        for(Method m : methods.values()){
            if(m.getLexeme().equals("main")){
                MainSemantic.ST.setClassMainMethod(this.getLexeme());
            }
            m.sentenceCheck();
        }
        if(constructor!=null){
            constructor.check();
        }
    }

    public Method itsAnExisistingMethod(Token method) {
        return methods.get(method.getLexeme());
    }

    public Attribute getAttribute(String lexeme) {
        return attributes.get(lexeme);
    }

    public Attribute itsAnExisistingAttribute(Token method) {
        return attributes.get(method.getLexeme());
    }

    public int getLastMethodOffset() {
        return lastMethodOffset;
    }

    public void generate() throws SemanticException {
        MainSemantic.ST.setCurrentClass(this);
        generateVT();
        generateConstructorAndMethods();
    }

    public void generateConstructorAndMethods(){
        MainSemantic.ST.getInstructionsList().add(".CODE");
            for(Method methods: methods.values()){
                if(methods.getClassWhoCreateMethod() != null && methods.getClassWhoCreateMethod().getLexeme().equals(token.getLexeme()))
                    methods.generate();
            }
        constructor.generate();
    }

    private void generateVT() {
        HashMap<Integer, String> methodsLabelByOffset = new HashMap<>();
        for (Method m : methods.values()) {
            if (m.getModifier()==null || m.getModifier() != null && !m.getModifier().getLexeme().equals("static"))
                methodsLabelByOffset.put(m.getOffset(), m.getClassWhoCreateMethod().getLexeme()+"_"+m.getLexeme());
        }

        if (!methodsLabelByOffset.isEmpty()){
            MainSemantic.ST.getInstructionsList().add(".DATA");
            StringBuilder methodsLabels = new StringBuilder();
            for (int i = 0; i < getLastMethodOffset(); i++) {
                if (methodsLabelByOffset.get(i) != null)
                    methodsLabels.append(methodsLabelByOffset.get(i));
                else methodsLabels.append("0");
                if (i != getLastMethodOffset()-1)
                    methodsLabels.append(",");
            }

            MainSemantic.ST.getInstructionsList().add("VT_"+token.getLexeme()+": DW "+methodsLabels+" ; Etiquetas de metodo de " + token.getLexeme());
        } else {
            MainSemantic.ST.getInstructionsList().add(".DATA");
            MainSemantic.ST.getInstructionsList().add("VT_"+token.getLexeme()+": NOP ");
        }
        MainSemantic.ST.getInstructionsList().add("");
    }

    public void setMethodsOffsets() {
        int offset = 0;
        if (methodsOffseted)
            return;
        if (inheritance != null && !inheritance.getLexeme().equals("Object")) {
            ConcreteClass father = MainSemantic.ST.itIsAnExistingClass(inheritance);
            if (father != null) {
                father.setMethodsOffsets();
                offset = father.getLastMethodOffset();
            }
        }
        for (Method m : methods.values()) {
            int offsetAux = methodIsInherited(m);
                if (offsetAux != -1) {
                    m.setOffset(offsetAux);
                } else {
                    m.setOffset(offset);
                    offset++;
                }
        }
        this.lastMethodOffset = offset;
        methodsOffseted = true;
    }

    private int methodIsInherited(Method m) {
        int toReturn = -1;
        if(inheritance != null) {
            if (!inheritance.getLexeme().equals("Object")) {
                ConcreteClass aux = MainSemantic.ST.itIsAnExistingClass(inheritance);
                if(aux != null) {
                    for (Method m2 : aux.getMethods().values()) {
                        if (m.getLexeme().equals(m2.getLexeme())) {
                            toReturn = m2.getOffset();
                            break;
                        }
                    }
                }
            }
        }
        return toReturn;
    }

    public void setAttributesOffsets() {
        int nextOffset = 1;
        if (attributesOffseted)
            return;
        if (inheritance != null && !inheritance.getLexeme().equals("Object")) {
            ConcreteClass father = MainSemantic.ST.itIsAnExistingClass(inheritance);
            if (father != null) {
                father.setAttributesOffsets();
                nextOffset = father.getLastAttributeOffset();
            }
        }
        for (Attribute a : attributes.values()) {
            int offsetAux = attributeIsInherited(a);
            if (offsetAux != -1) {
                a.setOffset(offsetAux);
            } else {
                a.setOffset(nextOffset);
                nextOffset++;
            }
        }
        this.lastAttributeOffset = nextOffset;
        attributesOffseted = true;
    }

    public int getLastAttributeOffset() {
        return  lastAttributeOffset;
    }

    private int attributeIsInherited(Attribute m) {
        int toReturn = -1;
        if(inheritance != null) {
            if (!inheritance.getLexeme().equals("Object")) {
                ConcreteClass aux = MainSemantic.ST.itIsAnExistingClass(inheritance);
                if(aux != null) {
                    for (Attribute m2 : aux.getAttributes().values()) {
                        if (m.getLexeme().equals(m2.getLexeme())) {
                            toReturn = m2.getOffset();
                            break;
                        }
                    }
                }
            }
        }
        return toReturn;
    }

    public String getVTable(){
        return "VT_"+token.getLexeme();
    }
}


