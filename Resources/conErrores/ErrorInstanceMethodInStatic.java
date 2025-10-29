//[Error:instanceMethod|8]
class ErrorInstanceMethodInStatic {
    void instanceMethod() {
        var x = 1;
    }
    
    static void staticMethod() {
        instanceMethod();
    }
}
